package com.example.helper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.helper.entity.dto.HistoryChatInfo;
import com.example.helper.entity.dto.NewChatInfo;
import com.example.helper.mapper.NewChatMapper;
import com.example.helper.service.NewChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewChatServiceImpl implements NewChatService {
    @Autowired
    private NewChatMapper newChatMapper;

    public int insert(NewChatInfo newChatInfo)
    {
        return newChatMapper.insert(newChatInfo);
    }

    public int delete(Long id)
    {
        return newChatMapper.deleteById(id);
    }

    public List<NewChatInfo> selectByUserId(String userId)
    {
        LambdaQueryWrapper<NewChatInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NewChatInfo::getUserId, userId);
        return newChatMapper.selectList(queryWrapper);
    }
}
