package com.example.doc.service;

import com.example.doc.entity.doc.HistoryData;

import java.util.List;

public interface HistoryDataService {
    HistoryData selectHistoryData(String fileId);
    List<HistoryData> selectAllHistoryData(String userId);
    int insert(HistoryData historyData);
    int delete(String fileId);
}
