package com.example.bookstoreapplication.dto;

import com.example.bookstoreapplication.model.BookModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {


    private String firstName;
    private String lastName;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Mobile Number is Invalid"+"\nMobile Format - E.g. +91-8087339090 - Country code follow by space or hyphen and 10digit number")
    private String phoneNo;

    @Pattern(regexp = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$", message = "PinCode Is Invalid")
    private String pinCode;
    private String locality;

    private String address;

    private String city;

    private String landMark;

    private String AddressType;

    private LocalDate orderDate = LocalDate.now();


}
