package com.otpexample01.otpexample.service;

import com.otpexample01.otpexample.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailVerificationService {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    static final Map<String,String> emailOtpMapping= new HashMap<>();//if any update done for static variable will reflect here

         //verify email by taking email n otp no.
    public Map<String,String> verifyOtp(String email,String otp){
        String storedOtp = emailOtpMapping.get(email);//based on email gets otp fro hashmap

        Map<String,String> response=new HashMap<>();
        if (storedOtp !=null && storedOtp.equals(otp)){//store otp is not null then & stored otp is equal to otp
                   //Fetch user by email and mark email as verified
            //logger.info("OTP is valid. Proceeding with verification")

            User user=userService.getUserByEmail(email);
            //user !=null-true
            if(user !=null) {
                emailOtpMapping.remove(email);//once the otp is verify before we update the user otp is removed//after removing otp from hashmap then it going to verify the email
                userService.verifyEmail(user);//if user is there set email id true
                response.put("status","success");
                response.put("message","email verified successfully");
            }else {
                //logger.error("invalid otp received for email: {}",email);
                response.put("status","error");//if not report status error
                response.put("message","User not found");
            }
        }else {
            response.put("status","error");
            response.put("message","invalid Otp");
        }
        return response;
    }

    //based on email verifying wheather user exist in ur db//
    public Map<String, String> sendOtpForLogin(String email) {
        if (userService.isEmailVerified(email)){
            String otp = emailService.generateOtp();//after verifying then only call generateOtp() n get otp
            emailOtpMapping.put(email,otp);//that otp storing in hashmap

            //send otp to the users's email
            emailService.sendOtpEmail(email);//same email service sent otp


            //and message back
            Map<String,String> response=new HashMap<>();
            response.put("status","success");
            response.put("message","otp sent successfully");
            return response;
        }else {
            Map<String,String> response=new HashMap<>();
            response.put("status","error");
            response.put("message","email not verified");
            return response;
        }

    }

    public Map<String, String> verifyOtpForLogin(String email, String otp) {
        String storedOtp = emailOtpMapping.get(email);//getting email n otp from hashmap

        Map<String,String> response=new HashMap<>();
        //first stored otp is not null and stored otp = otp is true then process//if false give error msg
        if (storedOtp !=null && storedOtp.equals(otp)){
            emailOtpMapping.remove(email);
            //Otp is valid
            response.put("status","success");
            response.put("message","otp verified successfully");
        }else {
            //invalid otp
            response.put("status","error");
            response.put("message","invalid Otp");
        }
        return response;
    }
}
