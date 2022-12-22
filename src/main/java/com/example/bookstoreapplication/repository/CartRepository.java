package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartModel, Integer> {

    @Query(value = "SELECT * FROM bookstore_application.cart_model where user_id=:id" , nativeQuery = true)
    List<CartModel> findByUser(int id);

    @Query(value = "SELECT * FROM bookstore_application.cart_model where user_id= :user_id and book_id= :book_id", nativeQuery = true)
    CartModel findByUserIdBookId(int user_id, int book_id);

}
