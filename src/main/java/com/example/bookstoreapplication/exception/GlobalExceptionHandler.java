package com.example.bookstoreapplication.exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BookStoreException.class)
    public String userAlreadyExist(BookStoreException bookStoreException) {
        return bookStoreException.getMessage();

    }
}
