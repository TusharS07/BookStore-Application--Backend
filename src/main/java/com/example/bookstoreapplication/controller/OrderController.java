package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.Response;
import com.example.bookstoreapplication.dto.OrderDTO;
import com.example.bookstoreapplication.model.OrderModel;
import com.example.bookstoreapplication.service.IorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/OrderPage")
public class OrderController {

    @Autowired
    IorderService iorderService;

    //--------------------------------- show All Books Info And Total qty And Total Price (user) before Order -------------------------------
    @GetMapping("/Show_Books_AvailableInCart_and_TotalPriceQty/User")
    public String showAllBooksAndTotalqtyAndPrice(@RequestHeader String token) {
        return iorderService.showAllBooksInfoAndTotalqtyAndPrice(token);

    }

    //--------------------------------- place Order (Only User)-----------------------------------------------------------------------------------------
    @PostMapping("/placeOrder")
    public ResponseEntity<Response> placeOrder(@RequestHeader String token, @Valid @RequestBody OrderDTO orderDTO) {
        OrderModel orderModel = iorderService.placeOrder(token, orderDTO);
        Response response = new Response(orderModel, "Order Placed Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Get Order Data (Only User) ---------------------------------------------------------------------------------
    @GetMapping("/Show_All_Orders/User")
    public ResponseEntity<Response> showUserOrders(@RequestHeader String token) {
        List<OrderModel> orders = iorderService.showUserOrders(token);
        Response response = new Response(orders,"Record found successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Get All Orders Data (Only Admin) ---------------------------------------------------------------------------
    @GetMapping("/Show_All_Orders/Admin")
    public ResponseEntity<Response> GetAllOrdersDataAsAdmin(@RequestHeader String token) {
        List<OrderModel> allOrders = iorderService.getAllOrderDetails(token);
        Response response = new Response(allOrders,"Record found successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Get Order Data by Order ID (only Admin) --------------------------------------------
    @GetMapping("/get_Order_Details_by_OrderId/Admin")
    public ResponseEntity<Response> getOrderDetailsByOrderIdForAdmin(@RequestHeader String token, @RequestParam int orderId) {
        OrderModel orderDetails = iorderService.getOrderdetailsbyOrderIdforAdmin(token,orderId);
        Response response = new Response(orderDetails, "Order Details Found for Order ID:"+orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Get Order Data by Order ID (Only User) --------------------------------------------
    @GetMapping("/get_Order_Details_by_OrderId/User")
    public ResponseEntity<Response> getOrderDetailsByOrderIdForUser(@RequestHeader String token, @RequestParam int orderId) {
        OrderModel orderDetails = iorderService.getOrderdetailsbyOrderIdforUser(token,orderId);
        Response response = new Response(orderDetails, "Order Details Found for Order ID:"+orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Remove OrderDetails By OrderId (Only Admin) ----------------------------------------------------------------
    @DeleteMapping("/Remove_Order_Details/Admin")
    public ResponseEntity<Response> removeOrderDataByOrderId(@RequestHeader String token, @RequestParam int orderId) {
        iorderService.removeOrderDetailsByOrderId(token, orderId);
        Response response = new Response("Removed order for Order id: " +orderId, "Removed Order Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Cancel Order (Only user) -----------------------------------------------------------------------------------
    @PutMapping("/Cancel_Order")
    public ResponseEntity<Response> cancelOrder(@RequestHeader String token, @RequestParam int orderId) {
        iorderService.cancelOrder(token, orderId);
        Response response = new Response("Cancel order for Order id: " +orderId, "You've successfully cancelled your order");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Change Order Mobile No (Only user) --------------------------------------------------------------------------
    @PutMapping("/Change_Order_PhoneNo")
    public ResponseEntity<Response> changeOrderAddressOrPhoneNo(@RequestHeader String token, @RequestParam int orderId, @RequestParam String phoneNo) {
        OrderModel updateOrder = iorderService.changeMobileNo(token, orderId, phoneNo);
        Response response = new Response(updateOrder, "Order Phone Number Change Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
