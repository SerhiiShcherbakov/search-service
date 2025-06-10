package com.serhiishcherbakov.searchservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("search-service").version("v1"))
                .components(new Components()
                        .addSecuritySchemes(
                                "api-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .name("X-Api-Key")
                                        .in(SecurityScheme.In.HEADER))
                        .addSecuritySchemes(
                                "user-token",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .name("Authorization")
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Example: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwibmFtZSI6IlRlc3QgVXNlciIsImVtYWlsIjoidGVzdC51c2VyQGVtYWlsLmNvbSJ9.2eVA2QN3O1w3lFSGtr5vIUkZAUobmR9ZSER4cmInsbU")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("api-key"))
                .addSecurityItem(new SecurityRequirement().addList("user-token"));
    }
}
