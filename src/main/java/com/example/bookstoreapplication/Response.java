package com.example.bookstoreapplication;

public class Response {
    String message;
    Object obj;


    public Response(Object obj, String message) {
        this.obj = obj;
        this.message = message;
    }

    public Response() {

    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
