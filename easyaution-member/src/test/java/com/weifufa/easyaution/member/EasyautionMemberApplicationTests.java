package com.weifufa.easyaution.member;

import com.weifufa.easyaution.member.dao.MemberDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class EasyautionMemberApplicationTests {

    @Autowired
    MemberDao memberDao;

    @Test
    void contextLoads() {
//        //密码要进行加密存储。加盐：$1$+8位字符
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encode = passwordEncoder.encode("123456");
//        System.out.println(encode);
    }

    private long time = 1000 * 60 * 60 * 24;//过期时间一天
    private String signature = "admin";

    @Test
    public void jwt() {

//        /*构建jwt对象*/
//        JwtBuilder jwtBuilder = Jwts.builder();
//        String jwtToken = jwtBuilder
//                //header
//                .setHeaderParam("type", "JWT")
//                .setHeaderParam("alg", "HS256") //设置参数
//                //payload
//                .claim("username", "tom")
//                .claim("role", "admin")
//                .setSubject("admin-test") //设置主题
//                .setExpiration(new Date(System.currentTimeMillis() + time))//设置有效期
//                .setId(UUID.randomUUID().toString())
//                //signature
//                .signWith(SignatureAlgorithm.HS256, signature)//生成256前面
//                .compact();
//
//        System.out.println(jwtToken);
    }

    @Test
    public void parse() {
//        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VybmFtZSI6InRvbSIsInJvbGUiOiJhZG1pbiIsInN1YiI6ImFkbWluLXRlc3QiLCJleHAiOjE2NzU4NTMxOTcsImp0aSI6IjU0MTJmOTA5LTYxYTYtNDEyNy1iN2RmLTZjYmNkMGUyYWRlMCJ9.vVI7I7gAD_zZOg_mJ69M9sunReQlF-uA5qZnljLkubc";
//        JwtParser jwtParser = Jwts.parser();
//        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
//        Claims claims = claimsJws.getBody();
//        System.out.println(claims.get("username"));
//        System.out.println(claims.get("role")); //获取id
//        System.out.println(claims.getId());
//        System.out.println(claims.getSubject());
//        System.out.println(claims.getExpiration()); //获取有效期
    }
}
