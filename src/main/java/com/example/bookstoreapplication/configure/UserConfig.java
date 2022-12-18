package com.example.bookstoreapplication.configure;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserConfig {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
