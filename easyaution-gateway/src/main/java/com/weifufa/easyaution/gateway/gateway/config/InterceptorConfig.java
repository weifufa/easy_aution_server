package com.weifufa.easyaution.gateway.gateway.config;

//import com.weifufa.easyaution.gateway.gateway.interceptos.JWTInterceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new JWTInterceptor())
//                .addPathPatterns("/api/**") //其他接口token验证
//                .excludePathPatterns("/api/login");//所有用户都放行
//    }
//}