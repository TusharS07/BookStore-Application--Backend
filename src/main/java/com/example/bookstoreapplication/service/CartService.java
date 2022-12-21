package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.CartDTO;
import com.example.bookstoreapplication.dto.LoginDTO;
import com.example.bookstoreapplication.exception.BookStoreException;
import com.example.bookstoreapplication.model.BookModel;
import com.example.bookstoreapplication.model.CartModel;
import com.example.bookstoreapplication.model.UserModel;
import com.example.bookstoreapplication.repository.BookRepository;
import com.example.bookstoreapplication.repository.CartRepository;
import com.example.bookstoreapplication.repository.UserRepository;
import com.example.bookstoreapplication.utility.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartService implements IcartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    JwtUtils jwtUtils;


    @Override
    public CartModel addToCart(String token, CartDTO cartDTO) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
            BookModel book = bookRepository.findById(cartDTO.getBookId()).get();
            if (book != null) {
                if (cartDTO.getQuantity() <= book.getBookQuantity()) {
                    double totalPrice = calculateTotalPrice(cartDTO.getQuantity(), book.getPrice());
                    CartModel cartModel = modelMapper.map(cartDTO, CartModel.class);
                    cartModel.setBookData(book);
                    cartModel.setUserData(user);
                    cartModel.setTotalPrice(totalPrice);
                    return cartRepository.save(cartModel);
                }
                throw new BookStoreException(cartDTO.getQuantity()+ "Books Not available in Stock"+
                        "\nOnly "+ book.getBookQuantity()+" Books available in stock");

            }
            throw new BookStoreException("Book Not Found");
        }
        throw new BookStoreException("Please first Login Application");
    }

    private double calculateTotalPrice(int quantity, int bookPrice){
        return quantity * bookPrice;
    }

    @Override
    public CartModel getCartRecordById(String token, int cartId) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).getRole().equals("Admin") && userRepository.findByEmail(user.getEmail()).isLogin()) {
            if (cartRepository.findById(cartId).isPresent()) {
                return cartRepository.findById(cartId).get();
            }
            throw new BookStoreException("Cart Record doesn't exists");
        }
        throw new BookStoreException("Only Admin can see cart records by Cart-id"+"\nplease login Application As admin");
    }

    @Override
    public List<CartModel> getUserCartRecordByUserId(String token) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
            List<CartModel> userCart = cartRepository.findByUser(user.getId());
            if (userCart.isEmpty()) {
                throw new BookStoreException("Empty Cart");
            }
            return userCart;
        }
        throw new BookStoreException("Please first Login Application");
    }

}
