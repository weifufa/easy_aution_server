package com.weifufa.easyaution.member.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weifufa.common.constant.MemberConstant;
import com.weifufa.common.execption.BizCodeEnume;
import com.weifufa.common.utils.PageUtils;
import com.weifufa.common.utils.R;
import com.weifufa.common.valid.AddGroup;
import com.weifufa.easyaution.member.entity.MemberEntity;
import com.weifufa.easyaution.member.exception.PhoneExistException;
import com.weifufa.easyaution.member.exception.UsernameExistException;
import com.weifufa.easyaution.member.feign.ThirdPartFeignService;
import com.weifufa.easyaution.member.service.MemberService;
import com.weifufa.easyaution.member.vo.MemberLoginVo;
import com.weifufa.easyaution.member.vo.MemberResVo;
import com.weifufa.easyaution.member.vo.MemberSmsLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    /**
     * 列表
     */
    @ApiOperation(value = "获取会员列表")
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "根据Id获取会员信息")
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        MemberEntity Member = memberService.getById(id);

        return R.ok().put("Member", Member);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存会员信息")
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity Member){
        memberService.save(Member);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改会员信息")
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity Member){
        memberService.updateById(Member);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除会员信息")
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        memberService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
    @ApiOperation(value = "用户密码登录")
    @PostMapping("/login")
    public R login(@RequestBody MemberLoginVo vo) {
        MemberEntity entity = memberService.login(vo);
        if (entity != null) {
            return R.ok();
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
       String redisCode=redisTemplate.opsForValue().get(MemberConstant.SMS_CODE_CACHE_PREFIX+phone); //先去redis中获取验证码
        if(!StringUtils.isEmpty(redisCode))//判断验证码是否存在，存在的话防止重复发送验证码
        {
            long l=Long.parseLong(redisCode.split("_")[1]);
            if(System.currentTimeMillis()-l<60000)
            {
                //60秒不能再发
                return R.error(BizCodeEnume.SMS_CODE_EXCEPTION.getCode(), BizCodeEnume.SMS_CODE_EXCEPTION.getMsg());
            }
        }
        //准备发送验证码
        String code=(int)((Math.random()*9+1)*100000)+"";//发送验证码
        String redCode=code+"_"+System.currentTimeMillis();//存一个redis需要记录一个时间，后面防止多次发送验证码消息
        //redis缓存验证码，防止同一个phone在60秒内再次发送验证码
        redisTemplate.opsForValue().set(MemberConstant.SMS_CODE_CACHE_PREFIX+phone,redCode,10, TimeUnit.MINUTES); //这里先设置10分钟

       // //TODO 调用第三方服务
       // thirdPartFeignService.sendCode(phone,code);
        return R.ok();
    }

    @ApiOperation(value = "短信登录")
    @PostMapping("/smslogin")
    public R smsLogin(@RequestBody MemberSmsLoginVo vo)
    {
       R r=memberService.smsLogin(vo);
       return r;
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public R register(@Validated({AddGroup.class}) @RequestBody MemberResVo vo)
    {
        R r=null;
        try {
            r = memberService.register(vo);
        } catch (PhoneExistException e) {
            return R.error(BizCodeEnume.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnume.PHONE_EXIST_EXCEPTION.getMsg());
        } catch (UsernameExistException e) {
            return R.error(BizCodeEnume.USER_EXIST_EXCEPTION.getCode(), BizCodeEnume.USER_EXIST_EXCEPTION.getMsg());
        }
        return r;
    }
}