package com.green.greengram2.common;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()) //스웨거에서 로그인처리할때 사용?
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Greengram Ver.2")
                .description("인스타그램 클론 코딩")
                .version("2.0.0");
    }
}
