package com.example.helper.service;

import com.example.helper.entity.dto.ChatInfo;

public interface ChatService {
    ChatInfo select(ChatInfo chat);
    int insert(ChatInfo chat);
    int update(ChatInfo chat);
    int delete(ChatInfo chat);
}
