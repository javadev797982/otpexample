package com.otpexample01.otpexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
//static import done here
import static com.otpexample01.otpexample.service.EmailVerificationService.emailOtpMapping;
@Service
public class EmailService {

    private JavaMailSender javaMailSender;
    private final UserService userService;

    public EmailService(JavaMailSender javaMailSender, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
    }

    public String generateOtp(){
        return String.format("%06d",new java.util.Random().nextInt(10000000));//String has format() which generate 6 digit otp random number
    }

    public void sendOtpEmail(String email) {
        String otp = generateOtp();//generateOtp()-generates otp n store in otp variable

        //save the otp for later verification in map
        emailOtpMapping.put(email,otp);

        sendEmail(email,"OTP for email verification","your otp is: "+otp);

    }
    private void sendEmail(String to,String subject,String text){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("umarbhagalpur.mu@gmail.com");//give valid email
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);


    }
}
