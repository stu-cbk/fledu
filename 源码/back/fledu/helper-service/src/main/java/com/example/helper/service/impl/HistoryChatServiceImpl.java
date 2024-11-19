package com.example.helper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.helper.entity.dto.HistoryChatInfo;
import com.example.helper.mapper.HistoryChatMapper;
import com.example.helper.service.HistoryChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryChatServiceImpl implements HistoryChatService {
    @Autowired
    private HistoryChatMapper historyChatMapper;

    public List<HistoryChatInfo> selectHistoryData(String userId, String type){
        LambdaQueryWrapper<HistoryChatInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HistoryChatInfo::getUserId, userId);
        queryWrapper.eq(HistoryChatInfo::getCid, type);
        return historyChatMapper.selectList(queryWrapper);
    }

    public int deleteHistoryData(String userId, String type){
        LambdaQueryWrapper<HistoryChatInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HistoryChatInfo::getUserId, userId);
        queryWrapper.eq(HistoryChatInfo::getCid, type);
        return historyChatMapper.delete(queryWrapper);
    }

    public int insert(HistoryChatInfo historyChatInfo){
        return historyChatMapper.insert(historyChatInfo);
    }
}
