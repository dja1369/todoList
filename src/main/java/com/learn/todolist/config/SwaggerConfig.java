package com.learn.todolist.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SecuritySchemes({
        @SecurityScheme(
                name = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT"
        )
})
@OpenAPIDefinition(
        info = @Info(
                title = "Todo List API",
                version = "1.0",
                description = "Todo List API v1.0"
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@Configuration
public class SwaggerConfig {
        private final String paths = "/api/**";

//        @Bean
//        public GroupedOpenApi openApi() {
//                GroupedOpenApi.builder()
//                        .group("todoList")
//                        .pathsToMatch(paths)
//                        .addOpenApiCustomizer(openApi
//        }

}
