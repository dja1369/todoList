package com.todolist.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info

import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    private final val SECURITY_SCHEME_NAME = "Authorization"

    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .components(
            Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme())
        )
        .info(apiInfo())

    private fun apiInfo(): Info = Info()
        .title("TODO REST APIDocs")
        .description("TODO API")
        .version("1.0.0")

    private fun securityScheme(): SecurityScheme {
        return SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
    }

}