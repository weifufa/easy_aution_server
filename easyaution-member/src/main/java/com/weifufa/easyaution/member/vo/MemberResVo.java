package com.weifufa.easyaution.member.vo;

import com.weifufa.common.valid.AddGroup;
import com.weifufa.common.valid.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class MemberResVo {
    /*暂时用不到可以删除 2023/2/5*/
    //    username: '', //确认
//    password: '',//密码
//    repassword: '',
//    email: '', //邮箱
//    code: '', //验证码
//    phone: '', //手机号
    @NotEmpty(groups = {AddGroup.class})
    //@Pattern(regexp="\\d{3}",message = "用户名长度需要大于3位",groups = {AddGroup.class})
    private String username;//用户名
    @NotEmpty(groups = {AddGroup.class})
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",message = "至少8个字符，，至少有1个小写字母和1个数字,不能包含特殊字符（非数字字母）",groups = {AddGroup.class})
    private String password;//密码
    @NotEmpty(groups = {AddGroup.class})
    @Pattern(regexp="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",message = "邮箱格式不正确请重新输入!",groups = {AddGroup.class})
    private String email;//邮箱
    @NotEmpty(groups = {AddGroup.class})
    @Pattern(regexp="^[0-9]{6}$",message = "验证码格式错误!",groups = {AddGroup.class})
    private String code;//验证码
    @NotEmpty(groups = {AddGroup.class})
    @Pattern(regexp="0?(13|14|15|18)[0-9]{9}",message = "手机号格式错误!",groups = {AddGroup.class})
    private String phone;//手机号

}