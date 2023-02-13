package com.weifufa.easyaution.member.vo;

import lombok.Data;

@Data
public class MemberLoginVo {
    private String username; //用户名
    private String password; //密码
    private String captcha; //验证码
    private String uuid; //uuid
}