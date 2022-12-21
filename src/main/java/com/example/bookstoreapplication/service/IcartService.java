package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.CartDTO;
import com.example.bookstoreapplication.model.CartModel;

import java.util.List;

public interface IcartService {
    CartModel addToCart(String token, CartDTO cartDTO);

    CartModel updateBookCart(String token, CartDTO cartDTO);

    String removeBookFromCart(String token, int cartId);

    CartModel getCartRecordById(String token, int cartId);

    List<CartModel> getUserCartRecordByUser(String token);




}
