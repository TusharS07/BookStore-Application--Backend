package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.BookDTO;
import com.example.bookstoreapplication.model.BookModel;

import java.util.List;

public interface IbookService {
    BookModel addBooks(String token, BookDTO bookDTO);
    BookModel updateBooksData(String token, int id, BookDTO bookDTO);
    String deleteBookById(String token, int id);
    List<BookModel> showAllBooks();
    BookModel getBookByID(int id);
    List<BookModel> searchBookByName(String bookName);
    List<BookModel> searchBookByAuthorName(String authorName);
    List<BookModel> sortBookByPriceHighToLow();
    List<BookModel> sortBookByPriceLowToHigh();
    List<BookModel> sortBooksByNewestArrivals();
}
