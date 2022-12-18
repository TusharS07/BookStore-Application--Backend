package com.example.bookstoreapplication.exception;

public class BookStoreException extends RuntimeException{
    String message;

    public BookStoreException(String message) {
        this.message = message;
    }

    public BookStoreException(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
