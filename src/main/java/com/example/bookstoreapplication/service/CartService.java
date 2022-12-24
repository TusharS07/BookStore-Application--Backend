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
import java.util.Optional;


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


    //--------------------------------- Add New Cart Data -----------------------------------------------------------------
    @Override
    public CartModel addToCart(String token, CartDTO cartDTO) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
            BookModel book = bookRepository.findById(cartDTO.getBookId()).get();
            if (book != null) {
                if (cartDTO.getQuantity() <= book.getBookQuantity()) {
                    CartModel cart = cartRepository.findByUserIdBookId(user.getId(), cartDTO.getBookId());
                    if (cart == null) {
                        double totalPrice = calculateTotalPrice(cartDTO.getQuantity(), book.getPrice());
                        cart = new CartModel(user, book, cartDTO.getQuantity(), totalPrice);
                        return cartRepository.save(cart);
                    }
                    throw new BookStoreException("This Book Already Exist into Your Cart"
                            +"\nif you want to add more Quantity for this book"
                            +"\nthen please go to cart and update Book Quantity");
                }
                throw new BookStoreException(cartDTO.getQuantity()+ " Books Not available in Stock"+
                        "\nOnly "+ book.getBookQuantity()+" Books available in stock");
            }
            throw new BookStoreException("Book Not Found");
        }
        throw new BookStoreException("Please first Login Application");
    }

    private double calculateTotalPrice(int quantity, int bookPrice){
        return quantity * bookPrice;
    }

    //--------------------------------- Update Cart Data ------------------------------------------------------------------
    @Override
    public CartModel updateBookCart(String token, CartDTO cartDTO) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
            BookModel book = bookRepository.findById(cartDTO.getBookId()).get();
            CartModel cart = cartRepository.findByUserIdBookId(user.getId(), cartDTO.getBookId());
            if (cart != null) {
                if (cartDTO.getQuantity() <= book.getBookQuantity()) {
                    double totalPrice = calculateTotalPrice(cartDTO.getQuantity(), book.getPrice());
                    cart.setQuantity(cartDTO.getQuantity());
                    cart.setTotalPrice(totalPrice);
                    return cartRepository.save(cart);
                }
                throw new BookStoreException(cartDTO.getQuantity()+ " Books Not available in Stock"+
                        "\nOnly "+ book.getBookQuantity()+" Books available in stock");
            }
            throw new BookStoreException("Book Not Found in Cart");
        }
        throw new BookStoreException("Please first Login Application");
    }

    //--------------------------------- Delete Cart Data -----------------------------------------------------------------
    @Override
    public String removeBookFromCart(String token, int cartId) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
            if (cartRepository.findById(cartId).isPresent()) {
                cartRepository.deleteById(cartId);
                return "Delete Successful";
            }
            throw new BookStoreException("Book Not Found");
        }
        throw new BookStoreException("Please first Login Application");
    }

    //--------------------------------- Get Cart Data by Cart Id (Only Admin) -------------------------------------------

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

    //--------------------------------- Show Cart Data(Books) ---------------------------------
    @Override
    public List<CartModel> getUserCartRecordByUser(String token) {
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