package com.weifufa.easyaution.member;

import com.weifufa.easyaution.member.dao.MemberDao;
import com.weifufa.easyaution.member.entity.MemberEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EasyautionMemberApplicationTests {

    @Autowired
    MemberDao memberDao;
    @Test
    void contextLoads() {
        List<MemberEntity> memberEntities = memberDao.selectList();
        System.out.println(memberEntities);
    }

}
