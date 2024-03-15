package com.otpexample01.otpexample.controller;

import com.otpexample01.otpexample.entity.User;
import com.otpexample01.otpexample.service.EmailService;
import com.otpexample01.otpexample.service.EmailVerificationService;
import com.otpexample01.otpexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailVerificationService emailVerificationService;

    @PostMapping("/register")
    public Map<String,String> registerUser(@RequestBody User user){
        //register user without email verification
        User registerUser = userService.registerUser(user);

        //send otp email for email verification
        //code work-fetch email id from user object
        emailService.sendOtpEmail(user.getEmail());//calling getEmail()

        Map<String,String> response= new HashMap<>();
        response.put("status","success");//message 1 putting//"status"-as key,"success"-as value //we can have any no of message
        response.put("message","User register successfully. Ckeck your email for verification");//message 2 putting
        return response;

    }
    @PostMapping("/verify-otp")
    public Map<String,String> verifyOtp(@RequestParam String email,@RequestParam String otp){
        return emailVerificationService.verifyOtp(email,otp);
    }

}
