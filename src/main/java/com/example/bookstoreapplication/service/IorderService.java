package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.OrderDTO;
import com.example.bookstoreapplication.model.OrderModel;

import java.util.List;

public interface IorderService {

    OrderModel placeOrder(String token, OrderDTO orderDTO);
    List<OrderModel> showUserOrders(String token);
    List<OrderModel> getAllOrderDetails(String token);

    OrderModel getOrderDatabyOrderId(String token, int orderId);

    String removeOrderDetailsByOrderId(String token, int orderId);
    String cancelOrder(String token, int orderId);

}
