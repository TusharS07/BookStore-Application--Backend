package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.LoginDTO;
import com.example.bookstoreapplication.dto.RegisterDTO;
import com.example.bookstoreapplication.dto.UpdateDTO;
import com.example.bookstoreapplication.exception.BookStoreException;
import com.example.bookstoreapplication.model.UserModel;
import com.example.bookstoreapplication.repository.UserRepository;
import com.example.bookstoreapplication.utility.EmailService;
import com.example.bookstoreapplication.utility.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements IuserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EmailService emailService;

    @Autowired
    JwtUtils jwtUtils;

    //--------------------------------- Register New User ---------------------------------
    @Override
    public String registerNewUser(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()) == null) {
            UserModel registerNewUser = modelMapper.map(registerDTO,UserModel.class);
            registerNewUser.setRole("user");
            userRepository.save(registerNewUser);
            emailService.sendMail(registerDTO.getEmail(), "Registration Successful for BookStore Application");
            return "User Register Successful";
        }
        throw  new BookStoreException("User Already Exist"+"\n Please Try with Another Email id");
    }

    //--------------------------------- User Login ---------------------------------

    @Override
    public String login(LoginDTO loginDTO) {
        if (userRepository.findByEmail(loginDTO.getEmail()) != null) {
            if (userRepository.findByEmail(loginDTO.getEmail()).getPassword().equals(loginDTO.getPassword())) {
                String token = jwtUtils.generateToken(loginDTO);
                UserModel userModel = userRepository.findByEmail(loginDTO.getEmail());
                userModel.setLogin(true);
                userModel.setId(userModel.getId());
                userRepository.save(userModel);
                emailService.sendMail(loginDTO.getEmail(), "Login Successful");
                return token;
            }
            throw new BookStoreException("please check Your Password");
        }
        throw new BookStoreException("Check Your Email-ID");
    }

    //--------------------------------- User Logout ---------------------------------
    @Override
    public String logout(String token) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel userModel = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(userModel.getEmail()).isLogin()) {
            userModel.setLogin(false);
            userRepository.save(userModel);
            return "User Logout Successfully";
        }
        throw new BookStoreException("User Not Found");
    }
    //--------------------------------- Forgot Password ---------------------------------
    @Override
    public String forgotPassword(String token, String password) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel resetPassword = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(resetPassword.getEmail()).isLogin()) {
            resetPassword.setPassword(password);
            resetPassword.setLogin(false);
            userRepository.save(resetPassword);
            return "Password changed Successful";
        }
        throw new BookStoreException("please Login with Proper email and password");
    }

    //--------------------------------- Delete User Data ---------------------------------

    @Override
    public String delete(String token) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel delete = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(delete.getEmail()).isLogin()) {
            userRepository.deleteById(delete.getId());
            return "user deleted";
        }
        throw new BookStoreException("User Not Found");
    }

    //--------------------------------- Update User Data ---------------------------------

    @Override
    public UserModel update(UpdateDTO updateDTO, String token) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (user != null) {
            UserModel update = modelMapper.map(updateDTO, UserModel.class);
            update.setId(user.getId());
            update.setLogin(true);
            update.setRole(user.getRole());
            if (update.getFirstName() == null) {
                update.setFirstName(user.getFirstName());
            }
            if (update.getLastName() == null) {
                update.setLastName(user.getLastName());
            }
            if (update.getEmail() == null) {
                update.setEmail(user.getEmail());
            }
            if (update.getPassword() == null) {
                update.setPassword(user.getPassword());
            }
            return userRepository.save(update);
        }
        throw new BookStoreException("Email And Password is not Matched");
    }

    //--------------------------------- Get User By Token Id ---------------------------------
    @Override
    public UserModel getUserData(String token) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
            return userRepository.findById(user.getId()).get();
        }
        throw new BookStoreException("Invalid User");
    }

    //----------------------------- Show_All_UserData --------------------------------

    @Override
    public List<UserModel> showAllUsers(String token) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
            if (userRepository.findById(user.getId()).get().getRole().equals("Admin")) {
                return userRepository.findAll();
            }
            throw new BookStoreException("Not Accessable to You");
        }
        throw new BookStoreException("Admin is not Login");
    }
}
