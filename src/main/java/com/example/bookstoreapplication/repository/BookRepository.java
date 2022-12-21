package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookModel, Integer> {
    List<BookModel> findByBookName(String bookName);
    List<BookModel> findAllByAuthorName(String authorName);
}
