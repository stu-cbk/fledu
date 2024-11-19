package com.example.doc.service;

import com.example.doc.entity.doc.HistoryChat;

import java.util.List;

public interface HistoryChatService {
    List<HistoryChat> selectHistoryChat(String fileId);
    int insert(HistoryChat historyChat);
    int delete(String fileId);
}
