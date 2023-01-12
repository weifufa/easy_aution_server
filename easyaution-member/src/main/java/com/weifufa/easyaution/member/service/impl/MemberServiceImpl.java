package com.weifufa.easyaution.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weifufa.easyaution.member.dao.MemberDao;
import com.weifufa.easyaution.member.entity.MemberEntity;
import com.weifufa.easyaution.member.service.MemberService;
import com.weifufa.easyaution.member.vo.MemberLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {
//    @Autowired
//    MemberDao memberDao;
    @Override
    public List<MemberEntity> selectAll() {
        List<MemberEntity> list= new ArrayList<MemberEntity>();//        return memberDao.selectList();
        return list;
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        String username=vo.getUsername();
        String password=vo.getPassword();
        MemberEntity entity=this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("username",username));
        if(entity==null)
        {
            //登录失败
            return null;
        }
        else
        {
            //获取到数据库的password
            String passwordDb=entity.getPassword();
            BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
            //2.密码匹配
            boolean matches=passwordEncoder.matches(password,passwordDb);
            if(matches)
            {
                return entity;
            }else {
                return null;
            }
        }
    }

    @Override
    public MemberEntity IsExitPhone(String phone) {
        if(StringUtils.isEmpty(phone))return null;
        return  this.getOne(new QueryWrapper<MemberEntity>().eq("phone", phone));
    }
}