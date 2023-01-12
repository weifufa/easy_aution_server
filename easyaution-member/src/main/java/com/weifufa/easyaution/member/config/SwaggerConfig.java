package com.weifufa.easyaution.member.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig 接口文档配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 配置接口文档生成规则
     */
    @Bean
    public Docket getDocket() {
        // 设置文档生成规则
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()) // 设置文档信息
                .select()
                // 设置哪个包下的类需要生成文档
                .apis(RequestHandlerSelectors.basePackage("com.weifufa.easyaution.member.controller"))
                .paths(PathSelectors.any()) // 定义哪些路径的接口需要生成文档
                .build();

    }

    /**
     * 设置文档信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("易拍卖护院服务接口文档")
                .description("易拍卖项目SwaggerAPI文档")
                .version("1.0")
                .contact(new Contact("weifufa",
                        "http://localhost:8000/springboot/swagger-ui.html",
                        "xxx@qq.com"))
                .build();
    }
}