package com.example.user.service.impl;

import com.example.user.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JWTServiceImpl implements JWTService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void storeJWT(String userId, String token, long expiration) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String key = "userId:" + userId;
        ops.set(key, token, expiration, TimeUnit.DAYS);
    }

    @Override
    public String getJWT(String userId)
    {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String key = "userId:" + userId;
        return (String) ops.get(key);
    }

}
