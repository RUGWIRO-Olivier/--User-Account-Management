package com.userProfileManagementApi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.Collections;
import java.util.List;
@Configuration
public class SwaggerConfig {
    private final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info().title("Z Company").version("v1")
                        .description("The Z Company User Profile Management API documentation")
                        .license(new License().name("All rights reserved").url("#"))
        );
    }

    @Bean
    GroupedOpenApi zCompanyOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("Z Company")
                .pathsToMatch("/**")
                .addOperationCustomizer(new GlobalHeaderAdder())
                .build();
    }

    @Component
    class GlobalHeaderAdder implements OperationCustomizer {
        @Override
        public Operation customize(Operation operation, HandlerMethod handlerMethod) {
            operation.addSecurityItem(new SecurityRequirement().addList("Bearer"));
            List<Parameter> parameterList = operation.getParameters();
            if (parameterList != null && !parameterList.isEmpty()) {
                Collections.rotate(parameterList, 1);
            }
            return operation;
        }
    }
}
