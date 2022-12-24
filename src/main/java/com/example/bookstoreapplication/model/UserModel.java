package com.example.bookstoreapplication.model;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isLogin = false;
    private String role ;

    private String phoneNo;
    private String pinCode;
    private String locality;
    private String address;
    private String city;
    private String landMark;
    private String AddressType;

//    @OneToOne(mappedBy = "userData")
//    CartModel cartModel;


}
