package com.weifufa.easyaution.member.controller;

import com.alibaba.fastjson.TypeReference;
import com.weifufa.common.constant.MemberConstant;
import com.weifufa.common.execption.BizCodeEnume;
import com.weifufa.common.utils.JWTUtil;
import com.weifufa.common.utils.PageUtils;
import com.weifufa.common.utils.R;
import com.weifufa.common.valid.AddGroup;
import com.weifufa.easyaution.member.entity.MemberEntity;
import com.weifufa.easyaution.member.exception.PhoneExistException;
import com.weifufa.easyaution.member.exception.UsernameExistException;
import com.weifufa.easyaution.member.feign.ThirdPartFeignService;
import com.weifufa.easyaution.member.service.CaptchaService;
import com.weifufa.easyaution.member.service.MemberService;
import com.weifufa.easyaution.member.vo.MemberLoginVo;
import com.weifufa.easyaution.member.vo.MemberResVo;
import com.weifufa.easyaution.member.vo.MemberSmsLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("member")
@Api(tags = "会员服务", value = "提供会员信息等功能") //类说明
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ThirdPartFeignService thirdPartFeignService;
    @Autowired
    CaptchaService captchaService;

    /**
     * 列表
     */
    @ApiOperation(value = "获取会员列表")
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);
        return R.ok().put("page", page);

    }


    /**
     * 信息
     */
    @ApiOperation(value = "根据Id获取会员信息")
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberEntity Member = memberService.getById(id);

        return R.ok().put("Member", Member);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存会员信息")
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity Member) {
        memberService.save(Member);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改会员信息")
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity Member) {
        memberService.updateById(Member);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除会员信息")
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @ApiOperation(value = "用户密码登录")
    @PostMapping("/login")
    public R login(HttpServletResponse response, @RequestBody MemberLoginVo vo) {
        //验证图形码是否正确
        String captcha = redisTemplate.opsForValue().get(vo.getUuid());
        if(StringUtils.isEmpty(vo.getCaptcha())||(!vo.getCaptcha().equals(captcha)))
        {
            return R.error(BizCodeEnume.CAPTCHA_EXCEPTION.getCode(), BizCodeEnume.CAPTCHA_EXCEPTION.getMsg());
        }
        MemberEntity entity = memberService.login(vo);
        if (entity != null) {
            //生成JWT令牌
            Map<String, String> payload = new HashMap<>();
            payload.put("id", entity.getId().toString());
            payload.put("username", entity.getUsername());
            //生成Token
            String token = JWTUtil.getToken(payload);
            response.setHeader("Authorization", token);//存到响应体
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return R.ok().setData(entity);
        } else {
            return R.error(BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getCode(), BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getMsg());
        }
    }


    @ApiOperation(value = "查询手机号是否存在")
    @GetMapping("/isexitphone")
    public R isExitPhone(@PathParam("phone") String phone) {
        MemberEntity entity = memberService.isExitPhone(phone);
        if (entity != null) {
            return R.ok(BizCodeEnume.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnume.PHONE_EXIST_EXCEPTION.getMsg());
        } else {
            return R.error(BizCodeEnume.PHONE_NO_EXIST_EXCEPTION.getCode(), BizCodeEnume.PHONE_NO_EXIST_EXCEPTION.getMsg());
        }
    }

    @ApiOperation(value = "发送短信验证码")
    @GetMapping("/sms/sendcode")
    public R sendCode(@PathParam("phone") String phone) {
        String redisCode = redisTemplate.opsForValue().get(MemberConstant.SMS_CODE_CACHE_PREFIX + phone); //先去redis中获取验证码
        if (!StringUtils.isEmpty(redisCode))//判断验证码是否存在，存在的话防止重复发送验证码
        {
            long l = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis() - l < 60000) {
                //60秒不能再发
                return R.error(BizCodeEnume.SMS_CODE_EXCEPTION.getCode(), BizCodeEnume.SMS_CODE_EXCEPTION.getMsg());
            }
        }
        //准备发送验证码
        String code = (int) ((Math.random() * 9 + 1) * 100000) + "";//发送验证码
        String redCode = code + "_" + System.currentTimeMillis();//存一个redis需要记录一个时间，后面防止多次发送验证码消息
        //redis缓存验证码，防止同一个phone在60秒内再次发送验证码
        redisTemplate.opsForValue().set(MemberConstant.SMS_CODE_CACHE_PREFIX + phone, redCode, 10, TimeUnit.MINUTES); //这里先设置10分钟
        System.out.println("验证码：" + code);
        // //TODO 调用第三方服务 打开即可
        // thirdPartFeignService.sendCode(phone,code);
        return R.ok();
    }

    @ApiOperation(value = "短信登录")
    @PostMapping("/smslogin")
    public R smsLogin(HttpServletResponse response, @RequestBody MemberSmsLoginVo vo) {
        R r = memberService.smsLogin(vo);
        MemberEntity entity = r.getData(new TypeReference<MemberEntity>() {
        });
        String token = JWTUtil.createJwt(entity.getPhone());
        response.setHeader("Authorization", token);//存到响应体
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        redisTemplate.opsForValue().set(MemberConstant.TOKEN_PHONE_PREFIX + entity.getPhone(), token, MemberConstant.TOKEN_EXPIRE, TimeUnit.MINUTES);//3天后过期
        return r;
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public R register(@Validated({AddGroup.class}) @RequestBody MemberResVo vo, HttpServletResponse response) {
        R r = null;
        try {
            r = memberService.register(vo);
            MemberEntity entity = r.getData(new TypeReference<MemberEntity>() {
            });
            String token = JWTUtil.createJwt(entity.getPhone());
            response.setHeader("Authorization", token);//存到响应体
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            redisTemplate.opsForValue().set(MemberConstant.TOKEN_PHONE_PREFIX + entity.getPhone(), token, MemberConstant.TOKEN_EXPIRE, TimeUnit.MINUTES);//3天后过期
        } catch (PhoneExistException e) {
            return R.error(BizCodeEnume.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnume.PHONE_EXIST_EXCEPTION.getMsg());
        } catch (UsernameExistException e) {
            return R.error(BizCodeEnume.USER_EXIST_EXCEPTION.getCode(), BizCodeEnume.USER_EXIST_EXCEPTION.getMsg());
        }
        return r;
    }

    // 退出
    @ApiOperation(value = "退出接口", notes = "用户退出")
    @GetMapping("/logout")
    public R logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String userPhone = JWTUtil.getUserPhone(token);
        redisTemplate.delete(MemberConstant.TOKEN_PHONE_PREFIX + userPhone);
        return R.ok();
    }

    /**
     * 验证码
     */
    @ApiOperation(value = "获取图片验证码", notes = "获取图片验证码")
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取图片验证码
        BufferedImage image = captchaService.getCaptcha(uuid);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }
}