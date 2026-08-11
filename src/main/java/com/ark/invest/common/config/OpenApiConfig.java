package com.ark.invest.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI arkInvestmentOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ark Investment Management API")
                        .description("""
                                REST API for managing investment funds,
                                investors, transactions, and financial reporting.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("API Development Team")));
    }
}