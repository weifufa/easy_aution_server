package com.weifufa.easyaution.member;

import com.weifufa.easyaution.member.dao.MemberDao;
import com.weifufa.easyaution.member.entity.MemberEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
class EasyautionMemberApplicationTests {

    @Autowired
    MemberDao memberDao;
    @Test
    void contextLoads() {
        //密码要进行加密存储。加盐：$1$+8位字符
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

}
