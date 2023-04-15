package com.example.board.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final String SPRING_JWT_HEADER;

    public SwaggerConfig(@Value("${spring.jwt.header}") String SPRING_JWT_HEADER){
        log.info("SwaggerConfig 1번");
        this.SPRING_JWT_HEADER = SPRING_JWT_HEADER;
    }

    private ApiInfo apiInfo(){
        log.info("SwaggerConfig apiInfo 2번");
        return new ApiInfoBuilder()
                .title("BOARD REST API")
                .description("BOARD REST API Documentation")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket unAuthSwagger(){
        log.info("SwaggerConfig unAuthSwagger 3번");
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("permitALL API")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.board.controller")) // 해당 위치의 컨트롤러에 가서
                .paths(PathSelectors.ant("/permitAll/**"))  // /permitAll로 시작하는 모든 것들을 맵핑역할
                .build()
                .useDefaultResponseMessages(false);
    }
}
