package com.example.bookstoreapplication.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegisterDTO {
    @NotNull(message = "First Name cannot be null")
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\\\s]{1,}$", message = "First Name Is Invalid"+"\nFirst Name contain at least 2 characters")
    private String firstName;
    @NotNull(message = "Last Name cannot be null")
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\\\s]{2,}$", message = "Last Name Is Invalid"+"\nLast Name contain at least 3 characters")
    private String lastName;
    @NotNull(message = "Email-Id cannot be null")
    @Pattern(regexp = "^[A-Za-z0-9]+(.[A-Za-z0-9]+)*@[^_\\\\W]+(.[^_\\\\W]+)?(?=(.[^_\\\\W]{3,}$|.[a-zA-Z]{2}$)).*$", message = "Email-Id is Invalid")
    private String email;
    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password is Invalid"+"\n Password contains atleast 1 upper alpha char, number ,special character,  and lenght should be more than 8")
    private String password;

    @NotNull(message = "Mobile Number cannot be null")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Mobile Number is Invalid"+"\nMobile Format - E.g. +91-8087339090 - Country code follow by space or hyphen and 10digit number")
    private String phoneNo;

    @NotNull(message = "PinCode cannot be null")
    @Pattern(regexp = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$", message = "PinCode Is Invalid")
    private String pinCode;
    private String locality;
    @NotNull(message = "Address cannot be null")
    private String address;
    @NotNull(message = "City cannot be null")
    private String city;
    @NotNull(message = "Landmark cannot be null")
    private String landMark;
    @NotNull(message = "Address Type cannot be null")
    private String AddressType;
}
