package com.example.bookstoreapplication.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("AddressBook").apiInfo(apiInfo()).select().build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("BookStore Application", "BookStore Application Using SpringBoot.", "1.0",
                "Terms of Service", new Contact("Tushar Salunkhe" ,
                "https://github.com/TusharS07/BookStore-Application--Backend.git", "tushars1289@gmail.com"),
                "Apache License Version 2.0", "https://www.apache.org/licenses/", Collections.emptyList());
    }

}
