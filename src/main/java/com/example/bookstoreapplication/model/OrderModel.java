package com.example.bookstoreapplication.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private String name;
    private String phoneNo;
    private String pinCode;
    private String locality;
    private String address;
    private String city;
    private String landMark;
    private String AddressType;

    private LocalDate orderDate;
    private int orderQuantity;
    private double price;
    private int userId;

    private Boolean isCancel = false;


    @ManyToMany
    private List<BookModel> book;


}
