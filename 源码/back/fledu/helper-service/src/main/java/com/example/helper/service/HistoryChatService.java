package com.example.helper.service;

import com.example.helper.entity.dto.HistoryChatInfo;

import java.util.List;

public interface HistoryChatService {
    List<HistoryChatInfo> selectHistoryData(String userId, String type);
    int deleteHistoryData(String userId, String type);
    int insert(HistoryChatInfo historyChatInfo);

}
