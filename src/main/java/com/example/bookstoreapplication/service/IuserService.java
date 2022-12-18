package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.LoginDTO;
import com.example.bookstoreapplication.dto.RegisterDTO;
import com.example.bookstoreapplication.dto.UpdateDTO;
import com.example.bookstoreapplication.model.UserModel;

import java.util.List;

public interface IuserService {
    String registerNewUser(RegisterDTO registerDTO);
    String login(LoginDTO loginDTO);
    String logout(String token);
    String forgotPassword(String token, String password);
    String delete(String token);

    UserModel update(UpdateDTO updateDTO, String token);

    UserModel getUserData(String token);
    List<UserModel> showAllUsers(String token);

}
