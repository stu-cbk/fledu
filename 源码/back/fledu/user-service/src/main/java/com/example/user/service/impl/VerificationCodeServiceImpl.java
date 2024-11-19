package com.example.user.service.impl;

import com.example.user.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void storeVerificationCode(String phoneNumber, String code, long expiration) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String key = "verification_code:" + phoneNumber;
        ops.set(key, code, expiration, TimeUnit.SECONDS);
    }

    @Override
    public String getVerificationCode(String phoneNumber) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String key = "verification_code:" + phoneNumber;
        return (String) ops.get(key);
    }
}

