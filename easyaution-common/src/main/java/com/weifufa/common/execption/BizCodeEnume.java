package com.weifufa.common.execption;

/***
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为 5 为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知
 异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 * 10: 通用
 *     001：参数格式校验
 *     002：短信验证码评率太高
 * 11: 商品
 * 12: 订单
 * 13: 购物车
 * 14: 物流
 * 15: 用户
 * 21: 库存
 *
 *
 */
public enum BizCodeEnume {

    USER_EXIST_EXCEPTION(15001, "用户存在"),
    LOGINACCT_PASSWORD_INVAILD_EXCEPTION(15002, "账号密码错误"),
    PHONE_EXIST_EXCEPTION(15003, "手机号存在"),
    PHONE_NO_EXIST_EXCEPTION(15004, "手机号不存在"),




    UNKNOW_EXCEPTION(10000, "系统位置异常"),

    VAILD_EXCEPTION(10001, "参数格式检验失败"),

    SMS_CODE_EXCEPTION(10001, "验证码获取评率太高，稍后再试"),
    SMS_CODE_EXPIRE(10002, "短信验证码错误或已过期，请重新发送"),
    TO_MANY_REQUEST(10003, "请求流量过大"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架异常"),


    NO_STOCK_EXCEPTION(21000,"商品库存不足");
    private int code;
    private String msg;

    BizCodeEnume(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}