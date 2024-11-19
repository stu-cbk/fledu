package com.example.helper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.helper.entity.dto.ChatInfo;
import com.example.helper.mapper.ChatMapper;
import com.example.helper.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatMapper chatMapper;

    public ChatInfo select(ChatInfo chat)
    {
        LambdaQueryWrapper<ChatInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatInfo::getUserId, chat.getUserId());
        queryWrapper.eq(ChatInfo::getCid, chat.getCid());
        return chatMapper.selectOne(queryWrapper);
    }

    public int insert(ChatInfo chat)
    {
        return chatMapper.insert(chat);
    }

    public int update(ChatInfo chat)
    {
        LambdaQueryWrapper<ChatInfo> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(ChatInfo::getUserId, chat.getUserId());
        updateWrapper.eq(ChatInfo::getCid, chat.getCid());
        return chatMapper.update(chat, updateWrapper);
    }

    public int delete(ChatInfo chat)
    {
        LambdaQueryWrapper<ChatInfo> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(ChatInfo::getUserId, chat.getUserId());
        deleteWrapper.eq(ChatInfo::getCid, chat.getCid());
        return chatMapper.update(chat, deleteWrapper);
    }
}
