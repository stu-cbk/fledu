package com.example.ppt.service;

import com.example.ppt.entity.dto.HistoryData;

import java.util.List;

public interface HistoryDataService {
    List<HistoryData> selectAllData(String userId);
    int insert(HistoryData historyData);
    int updateOutlineAndSid(Long id, String outline, String sid, String status);
    int updateStatus(Long id, String status);
    int updateOutline(Long id, String outline);
    int updateName(Long id, String name);
    HistoryData selectById(Long id);
    int updatePPT(Long id, String pptUrl, String status);
    int updatePPTSid(Long id, String pptSid, String name, String coverImgSrc, String status);
    int delete(Long id);
}
