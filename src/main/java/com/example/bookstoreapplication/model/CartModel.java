package com.example.bookstoreapplication.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int cartId;
    private int quantity;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private UserModel userData;

    @ManyToOne(cascade = CascadeType.ALL)
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
