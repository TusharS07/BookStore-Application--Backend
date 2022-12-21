package com.example.bookstoreapplication.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class BookModel {
    @Column(name = "bookId")
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int bookId;
    private String bookName;
    private String authorName;
    private int bookQuantity;
    private int price;
    private String profilePic;
    private String bookDescription;
}
