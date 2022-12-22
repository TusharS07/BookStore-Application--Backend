package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Integer> {

    @Query(value = "SELECT * FROM bookstore_application.order_model where user_id=:userId" , nativeQuery = true)
    List<OrderModel> findByUser(int userId);

    List<OrderModel> findAllByUserId(int userId);
}
