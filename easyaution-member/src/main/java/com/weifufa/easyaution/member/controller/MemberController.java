package com.weifufa.easyaution.member.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weifufa.common.utils.R;
import com.weifufa.easyaution.member.entity.MemberEntity;
import com.weifufa.easyaution.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("member/member")
@Api(tags = "会员服务", value = "提供会员信息等功能") //类说明
public class MemberController {
    @Autowired
    MemberService memberService;

    @ApiOperation(value="查询所有会员信息")
    @RequestMapping(method = RequestMethod.GET)
    public R selectAll() {
//        MemberEntity asd=new MemberEntity();
//        asd.setUsername("测试");
//        String username = asd.getUsername();
//        System.out.println(username);
        //  R r = R.ok().setData(list);

        List<MemberEntity> list = memberService.selectAll();
      R r=  R.ok().put("data", list);
        return r;
    }

}