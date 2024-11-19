package com.example.video.service;

import com.example.video.entity.dto.HistoryData;

import java.util.List;

public interface HistoryDataService {
    int insert(HistoryData historyData);
    int delete(Long id);
    int update(HistoryData historyData);
    int updateStatus(Long id,String status);
    HistoryData selectById(Long id);
    List<HistoryData> selectAll();
}
