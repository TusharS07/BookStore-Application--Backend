package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.CartDTO;
import com.example.bookstoreapplication.model.CartModel;

import java.util.List;

public interface IcartService {
    CartModel addToCart(String token, CartDTO cartDTO);
    CartModel getCartRecordById(String token, int cartId);
    List<CartModel> getUserCartRecordByUserId(String token);

}
