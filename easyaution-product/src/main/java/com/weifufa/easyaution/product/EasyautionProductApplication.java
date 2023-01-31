package com.weifufa.easyaution.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class EasyautionProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyautionProductApplication.class, args);
    }

}
