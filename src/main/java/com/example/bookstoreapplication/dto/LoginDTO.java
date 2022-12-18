package com.example.bookstoreapplication.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoginDTO {
    private String email;
    private String password;
}
