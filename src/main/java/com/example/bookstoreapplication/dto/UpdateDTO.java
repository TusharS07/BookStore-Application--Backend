package com.example.bookstoreapplication.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UpdateDTO {
    @NotNull(message = "First Name cannot be null")
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\\\s]{1,}$", message = "First Name Is Invalid"+"\nFirst Name contain at least 2 characters")
    private String firstName;
    @NotNull(message = "Last Name cannot be null")
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\\\s]{2,}$", message = "Last Name Is Invalid"+"\nLast Name contain at least 3 characters")
    private String lastName;
}
