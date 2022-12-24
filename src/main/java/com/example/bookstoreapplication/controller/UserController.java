package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.Response;
import com.example.bookstoreapplication.dto.LoginDTO;
import com.example.bookstoreapplication.dto.RegisterDTO;
import com.example.bookstoreapplication.dto.UpdateDTO;
import com.example.bookstoreapplication.model.UserModel;
import com.example.bookstoreapplication.service.IuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/UserPage")
public class UserController {
    @Autowired
    IuserService iuserService;

    //--------------------------------- Add New User Data ---------------------------------

    @PostMapping("/Register_New_User")
    public ResponseEntity<Response> registerNewUser(@Valid @RequestBody  RegisterDTO registerDTO) {
        iuserService.registerNewUser(registerDTO);
        Response response = new Response(registerDTO, "User Registered Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- User Login (Both User or Admin)---------------------------------

    @PostMapping("/Login")
    public ResponseEntity<Response> loginPage(@RequestBody LoginDTO loginDTO) {
        String token = iuserService.login(loginDTO);
        Response response = new Response(token, "Login Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- User Logout (Both User or Admin)---------------------------------
    @PutMapping("/Logout")
    public ResponseEntity<Response> logOutUser(@RequestHeader String token) {
        iuserService.logout(token);
        Response response = new Response("User Logout", "SuccessFully Logout");
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Forgot Password (Both User or Admin)---------------------------------
    @PutMapping("/Forgot_Password")
    public ResponseEntity<Response> forgotPassword(@RequestHeader String token, @RequestParam String password) {
        iuserService.forgotPassword(token, password);
        Response response = new Response("Please Login again with new password", "Password changed Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Delete User Data (Only User)---------------------------------
    @DeleteMapping("/DeleteUser/user")
    public ResponseEntity<Response> delete(@RequestHeader String token) {
        iuserService.delete(token);
        Response response = new Response("Deleted for User: ", "Deleted Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Delete User (Only Admin) ---------------------------------
    @DeleteMapping("/DeleteUser/Admin")
    public ResponseEntity<Response> deleteUserAsAdmin(@RequestHeader String token, @RequestParam int id) {
        iuserService.deleteUserAsAdmin(token, id);
        Response response = new Response("Deleted for User: " +id, "Deleted Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Get User Data (Only User) ---------------------------------
    @GetMapping("/Get_Data/user")
    public ResponseEntity<Response> getUserData(@RequestHeader String token) {
        UserModel getUserData = iuserService.getUserData(token);
        Response response = new Response(getUserData, "User Data");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //----------------------------- Update UserData (Only User)--------------------------------

    @PutMapping("/UpdateData/user")
    public ResponseEntity<Response> updateData(@Valid @RequestBody UpdateDTO updateDTO, @RequestHeader String token)  {
        UserModel update = iuserService.update(updateDTO, token);
        Response response = new Response(update, "User Updated Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //----------------------------- Show_All_UserData (Only Admin)--------------------------------


    @GetMapping("/Show_All_User/Admin")
    public ResponseEntity<Response> getAllUser(@RequestHeader String token){
        List<UserModel> userModelList = iuserService.showAllUsers(token);
        Response response = new Response(userModelList, "All users Data" );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
