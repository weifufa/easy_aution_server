package com.weifufa.easyaution.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weifufa.easyaution.member.entity.MemberEntity;
import com.weifufa.easyaution.member.vo.MemberLoginVo;

import java.util.List;

public interface MemberService  extends IService<MemberEntity> {
  List<MemberEntity> selectAll();

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
  MemberEntity IsExitPhone(String phone);
}
