package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.Response;
import com.example.bookstoreapplication.dto.CartDTO;
import com.example.bookstoreapplication.model.CartModel;
import com.example.bookstoreapplication.service.IcartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CartPage")
public class CartController {

    @Autowired
    IcartService icartService;


    //--------------------------------- Add New Cart Data ---------------------------------
    @PostMapping("/AddToCart")
    public ResponseEntity<Response> addToCart(@RequestHeader String token, @RequestBody CartDTO cartDTO) {
        CartModel cartModel = icartService.addToCart(token, cartDTO);
        Response response = new Response(cartModel, "Books Added Into Cart Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Update Cart Data ---------------------------------
    @PutMapping("UpdateBookCart")
    public ResponseEntity<Response> updateBookCart(@RequestHeader String token, @RequestBody CartDTO cartDTO) {
        CartModel cartModel = icartService.updateBookCart(token, cartDTO);
        Response response = new Response(cartModel, "Book Quantity Update Into Cart Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Delete Cart Data ---------------------------------
    @DeleteMapping("/Remove_Book_From_Cart")
    public ResponseEntity<Response> removeBookFromCart(@RequestHeader String token, @RequestParam int cartId) {
        icartService.removeBookFromCart(token,cartId);
        Response response = new Response("Removed Book for id: " + cartId, "Book Remove Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //--------------------------------- Show Cart Data(Books) ---------------------------------
    @GetMapping("/Show_UserCart_Record_byUser")
    public ResponseEntity<Response> showUserCartRecords(@RequestHeader String token) {
        List<CartModel> userCartDetails = icartService.getUserCartRecordByUser(token);
        Response response = new Response(userCartDetails, "Cart record retrieved successfully for User");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //--------------------------------- Get Cart Data by Cart Id (Only Admin) ---------------------------------
    @GetMapping("/get_Cart_Record_byCartId/Admin")
    public ResponseEntity<Response> getCartRecordbyCartId(@RequestHeader String token, @RequestParam int cartId) {
        CartModel cartModel = icartService.getCartRecordById(token, cartId);
        Response response = new Response(cartModel, "Cart record retrieved successfully for CartId:-" +cartId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}