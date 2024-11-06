package edu.icet.service;

import edu.icet.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;

    private ConcurrentHashMap<String, Integer> otpCache = new ConcurrentHashMap<>();

    public int generateOtp(String email) {
        int otp = new Random().nextInt(900000) + 100000;  // Generate 6-digit OTP
        otpCache.put(email, otp);
        return otp;
    }

    public void sendOtpEmail(String email, int otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code for $Evenly");
        message.setText("Your OTP code is: " + otp);
        mailSender.send(message);
    }

    public boolean verifyOtp(String email, int otp) {
        Integer cachedOtp = otpCache.get(email);
        if (cachedOtp != null && cachedOtp == otp) {
            otpCache.remove(email);
            return true;
        }
        return false;
    }
}
