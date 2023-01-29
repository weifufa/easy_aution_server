package com.weifufa.easyaution.member.vo;

import lombok.Data;

@Data
public class MemberSmsLoginVo {
    private String phone;//手机号
    private String code;//验证码
}