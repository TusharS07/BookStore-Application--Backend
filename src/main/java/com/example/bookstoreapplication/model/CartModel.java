package com.example.bookstoreapplication.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int cartId;
    private int quantity;
    @OneToOne()
    @JoinColumn(name = "userId")
    private UserModel userData;

    @ManyToOne()
    @JoinColumn(name = "bookId")
    private BookModel bookData;

    private double totalPrice;

    public CartModel(UserModel userData, BookModel bookData, int quantity, double totalPrice) {
        this.userData = userData;
        this.bookData = bookData;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}