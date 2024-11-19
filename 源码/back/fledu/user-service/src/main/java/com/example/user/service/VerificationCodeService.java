package com.example.user.service;

public interface VerificationCodeService {
    void storeVerificationCode(String phoneNumber, String code, long expiration);
    String getVerificationCode(String phoneNumber);
}
