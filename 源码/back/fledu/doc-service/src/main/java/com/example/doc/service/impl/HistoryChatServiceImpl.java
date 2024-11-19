package com.example.doc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.doc.entity.doc.HistoryChat;
import com.example.doc.mapper.HistoryChatMapper;
import com.example.doc.service.HistoryChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryChatServiceImpl implements HistoryChatService {

    @Autowired
    private HistoryChatMapper historyChatMapper;

    public List<HistoryChat> selectHistoryChat(String fileId) {
        LambdaQueryWrapper<HistoryChat> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HistoryChat::getFileId, fileId);
        return historyChatMapper.selectList(queryWrapper);
    }


    public int insert(HistoryChat historyConclusion) {
        return historyChatMapper.insert(historyConclusion);
    }

    public int delete(String fileId){
        LambdaQueryWrapper<HistoryChat> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HistoryChat::getFileId, fileId);
        return historyChatMapper.delete(queryWrapper);
    }

}
