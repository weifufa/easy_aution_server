package com.weifufa.easyaution.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weifufa.common.utils.R;
import com.weifufa.easyaution.member.entity.MemberEntity;
import com.weifufa.easyaution.member.vo.MemberLoginVo;
import com.weifufa.easyaution.member.vo.MemberResVo;
import com.weifufa.easyaution.member.vo.MemberSmsLoginVo;
import com.weifufa.common.utils.PageUtils;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author weifufa
 * @email wff66@foxmail.com
 * @date 2023-01-31 20:10:22
 */
public interface MemberService  extends IService<MemberEntity> {

  /**
   * 登录
   * @param vo
   * @return
   */
  MemberEntity login(MemberLoginVo vo);

  /**
   * 查询手机号是否存在
   * @param phone
   * @return
   */
  MemberEntity isExitPhone(String phone);

  /**
   * 短信登录
   * @param vo
   * @return
   */
    R smsLogin(MemberSmsLoginVo vo);

  PageUtils queryPage(Map<String, Object> params);

  /**
   * 用户注册
   * @param vo
   * @return
   */
  R register(MemberResVo vo);

}

