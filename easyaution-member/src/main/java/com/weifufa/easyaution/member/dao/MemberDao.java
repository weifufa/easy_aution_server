package com.weifufa.easyaution.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weifufa.easyaution.member.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao  extends BaseMapper<MemberEntity>  {
    List<MemberEntity> selectList();
}