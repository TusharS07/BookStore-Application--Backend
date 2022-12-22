package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Integer> {
    List<BookModel> findByBookName(String bookName);
    List<BookModel> findAllByAuthorName(String authorName);
}
