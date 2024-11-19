package com.example.user.service;

public interface JWTService {
    void storeJWT(String userId, String token, long expiration);
    String getJWT(String userId);
}
