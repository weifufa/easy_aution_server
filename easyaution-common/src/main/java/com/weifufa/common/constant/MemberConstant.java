package com.weifufa.common.constant;

public class MemberConstant {
    public static final String SMS_CODE_CACHE_PREFIX = "sms:code:"; //临时存储短信验证码常量
    public static final String TOKEN_PHONE_PREFIX = "token:phone:"; //临时存储短信验证码常量
    public static final int TOKEN_EXPIRE=3600*3; //tokne过期时间
    public static final int CAPTCHA_EXPIRE=300; //验证码过期时间5分钟
}