package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Integer> {

    List<OrderModel> findAllByUserId(int userId);
}
