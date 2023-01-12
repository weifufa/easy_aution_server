package com.weifufa.easyaution.member.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weifufa.common.execption.BizCodeEnume;
import com.weifufa.common.utils.R;
import com.weifufa.easyaution.member.entity.MemberEntity;
import com.weifufa.easyaution.member.service.MemberService;
import com.weifufa.easyaution.member.vo.MemberLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("member")
@Api(tags = "会员服务", value = "提供会员信息等功能") //类说明
public class MemberController {
    @Autowired
    MemberService memberService;

    @ApiOperation(value = "查询所有会员信息")
    @RequestMapping(method = RequestMethod.GET)
    public R selectAll() {
//        MemberEntity asd=new MemberEntity();
//        asd.setUsername("测试");
//        String username = asd.getUsername();
//        System.out.println(username);
        //  R r = R.ok().setData(list);

        List<MemberEntity> list = memberService.selectAll();
        R r = R.ok().put("data", list);
        return r;
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
        MemberEntity entity = memberService.IsExitPhone(phone);
        if (entity != null) {
            return R.ok(BizCodeEnume.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnume.PHONE_EXIST_EXCEPTION.getMsg());
        } else {
            return R.error(BizCodeEnume.PHONE_NO_EXIST_EXCEPTION.getCode(), BizCodeEnume.PHONE_NO_EXIST_EXCEPTION.getMsg());
        }

    }

    @ApiOperation(value = "发送短信验证码")
    public R sendCode(@PathParam("phone") String phone) {
        return R.ok();
    }
}