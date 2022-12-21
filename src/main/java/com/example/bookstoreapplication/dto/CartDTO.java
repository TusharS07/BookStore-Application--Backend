package com.example.bookstoreapplication.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartDTO {
    private int bookId;
    @NotNull(message = "Quantity Cannot be Null!")
    private int quantity;
}
