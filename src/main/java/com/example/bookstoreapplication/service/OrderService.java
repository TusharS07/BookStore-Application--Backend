package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.LoginDTO;
import com.example.bookstoreapplication.dto.OrderDTO;
import com.example.bookstoreapplication.exception.BookStoreException;
import com.example.bookstoreapplication.model.BookModel;
import com.example.bookstoreapplication.model.CartModel;
import com.example.bookstoreapplication.model.OrderModel;
import com.example.bookstoreapplication.model.UserModel;
import com.example.bookstoreapplication.repository.BookRepository;
import com.example.bookstoreapplication.repository.CartRepository;
import com.example.bookstoreapplication.repository.OrderRepository;
import com.example.bookstoreapplication.repository.UserRepository;
import com.example.bookstoreapplication.utility.EmailService;
import com.example.bookstoreapplication.utility.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IorderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EmailService emailService;

    @Autowired
    JwtUtils jwtUtils;



    //--------------------------------- place Order(User) ---------------------------------------
    @Override
    public OrderModel placeOrder(String token, OrderDTO orderDTO) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
            List<CartModel> cart = cartRepository.findByUser(user.getId());
            List<BookModel> orderedBooks = new ArrayList<>();

            double totalOrderPrice = 0;
            int totalOrderQty = 0;

            for (int i = 0; i < cart.size(); i++) {
                totalOrderPrice = totalOrderPrice + cart.get(i).getTotalPrice();
                totalOrderQty = totalOrderQty + cart.get(i).getQuantity();
                orderedBooks.add(cart.get(i).getBookData());
            }

            OrderModel orderModel = modelMapper.map(orderDTO, OrderModel.class);
            orderModel.setUserId(user.getId());
            orderModel.setBook(orderedBooks);
            orderModel.setOrderQuantity(totalOrderQty);
            orderModel.setPrice(totalOrderPrice);
            orderRepository.save(orderModel);

            emailService.sendMail(user.getEmail(), "Hi " + user.getFirstName() + " " + user.getLastName() + "," +
                    "\n\nYour Order Placed Successful" +
                    "\nOrder Id: " + orderModel.getOrderId() +
                    "\nThe Order will delivered to you with in two days." +
                    "\n\nWe have attached your invoice should you need it in the future." +
                    "\n\n\nThank you for shopping!" +
                    "\nBookStore");

            //update qty int books database
            for (int i = 0; i < cart.size(); i++) {
                BookModel book = cart.get(i).getBookData();
                int updatedQty = updateBookQty(book.getBookQuantity(), cart.get(i).getQuantity());
                book.setBookQuantity(updatedQty);
                bookRepository.save(book);
                cartRepository.deleteById(cart.get(i).getCartId());
            }
            return orderModel;
        }
        throw new BookStoreException("Please sign in your account");
    }

    private int updateBookQty(int bookQty, int bookQtyInCart) {
        int updatedQty = bookQty - bookQtyInCart;
        if (updatedQty <= 0) {
            return 0;
        } else {
            return updatedQty;
        }
    }

    //--------------------------------- Get Order Data(User) ----------------------------------
    @Override
    public List<OrderModel> showUserOrders(String token) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
            List<OrderModel> userOrders = orderRepository.findAllByUserId(user.getId());
            if (userOrders.isEmpty()) {
                throw new BookStoreException("Empty Order List");
            }
            return userOrders;
        }
        throw new BookStoreException("Please sign in your account");
    }

    //--------------------------------- Get All Orders Data(Admin) ----------------------------------
    @Override
    public List<OrderModel> getAllOrderDetails(String token) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).getRole().equals("Admin") && userRepository.findByEmail(user.getEmail()).isLogin()) {
            List<OrderModel> userOrders = orderRepository.findAll();
            if (userOrders.isEmpty()) {
                throw new BookStoreException("Empty Order List");
            }
            return userOrders;
        }
        throw new BookStoreException("Please sign in your account as Admin");
    }

    //--------------------------------- Get Order Data by Order Id (Admin or User) -----------------------------------------------------------
    @Override
    public OrderModel getOrderDatabyOrderId(String token, int orderId) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
            if (orderRepository.findById(orderId).isPresent()) {
                return orderRepository.findById(orderId).get();
            }
            throw new BookStoreException("Order Record Not Found" + "\nInvalid Order_Id");
        }
        throw new BookStoreException("Please sign in your account");
    }


    //--------------------------------- Remove OrderDetails By OrderId (Admin) --------------------------------------------------------------------------
    @Override
    public String removeOrderDetailsByOrderId(String token, int orderId) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).getRole().equals("Admin") && userRepository.findByEmail(user.getEmail()).isLogin()) {
            if (orderRepository.findById(orderId).isPresent()) {
                orderRepository.deleteById(orderId);
                return "Successfully Remove Order details For Id: " + orderId;
            }
            throw new BookStoreException("Order Record Not Found" + "\nInvalid Order_Id");
        }
        throw new BookStoreException("Please sign in your account as Admin");
    }

    //--------------------------------- Cancel Order (user) ---------------------------------------------
    @Override
    public String cancelOrder(String token, int orderId) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
            if (orderRepository.findById(orderId).isPresent()) {
                OrderModel order = orderRepository.findById(orderId).get();
                if (order.getIsCancel() == false) {
                    order.setIsCancel(true);
                    orderRepository.save(order);
                    emailService.sendMail(user.getEmail(),"Hi " + user.getFirstName() + " " + user.getLastName()+ ","+
                            "\n\nYou've successfully cancelled your order " +
                            "\n\nAt your request, we canceled your order.Here's" +
                            "\nyour Order info:" +
                            "\n\nOrder ID: "+orderId);
                    return "Order Cancel Successful!";
                }
                throw new BookStoreException("Order is already canceled!");
            }
            throw new BookStoreException("Order Record Not Found" + "\nInvalid Order_Id");
        }
        throw new BookStoreException("Please sign in your account");
    }
}
