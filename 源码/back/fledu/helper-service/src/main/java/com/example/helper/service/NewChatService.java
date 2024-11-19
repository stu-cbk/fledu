package com.example.helper.service;

import com.example.helper.entity.dto.NewChatInfo;

import java.util.List;

public interface NewChatService {
    int insert(NewChatInfo newChatInfo);
    int delete(Long id);
    List<NewChatInfo> selectByUserId(String userId);
}
