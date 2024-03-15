package com.otpexample01.otpexample.service;

import com.otpexample01.otpexample.entity.User;
import com.otpexample01.otpexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user){
        //save the user to Db
        return userRepository.save(user);
    }


    //go db n verify user exist or not
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    public void verifyEmail(User user) {
        user.setEmailVerified(true);//if verification is true
        userRepository.save(user);//after true updating n save in db
    }

    public boolean isEmailVerified(String email) {
        User user = userRepository.findByEmail(email);
        return user !=null && user.isEmailVerified();// && this work as when the line is statement is true then only true

    }
}
