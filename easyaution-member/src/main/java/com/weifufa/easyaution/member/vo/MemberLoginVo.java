package com.weifufa.easyaution.member.vo;

import lombok.Data;

@Data
public class MemberLoginVo {
    private String username;
    private String password;
    private String captcha;
}