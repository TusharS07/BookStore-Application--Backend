package com.example.bookstoreapplication.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private String name;
    private int phoneNo;
    private String address;
    private String city;
    private String landMark;
    private int pinCode;
    private float price;
    private int userId;

    @OneToMany()
    public List<CartModel> cart;

    @ManyToMany
    public List<BookModel> book;



}
