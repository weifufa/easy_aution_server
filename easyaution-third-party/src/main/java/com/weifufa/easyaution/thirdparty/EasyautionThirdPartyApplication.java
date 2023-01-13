package com.weifufa.easyaution.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EasyautionThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyautionThirdPartyApplication.class, args);
    }

}
