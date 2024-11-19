package com.example.doc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.doc.entity.doc.HistoryData;
import com.example.doc.mapper.HistoryDataMapper;
import com.example.doc.service.HistoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class HistoryDataServiceImpl implements HistoryDataService {
    @Autowired
    private HistoryDataMapper historyDataMapper;

    public HistoryData selectHistoryData(String fileId)
    {
        LambdaQueryWrapper<HistoryData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HistoryData::getFileId, fileId);
        return historyDataMapper.selectOne(queryWrapper);
    }

    public List<HistoryData> selectAllHistoryData(String userId)
    {
        LambdaQueryWrapper<HistoryData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HistoryData::getUserId, userId);
        List<HistoryData> historyDataList = historyDataMapper.selectList(queryWrapper);
        for (HistoryData historyData : historyDataList){
            historyData.setUserId(null);
            historyData.setAnswer(null);
        }
        return historyDataList;
    }

    public int insert(HistoryData historyData)
    {
        return historyDataMapper.insert(historyData);
    }

    public int delete(String fileId)
    {
        LambdaQueryWrapper<HistoryData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HistoryData::getFileId, fileId);
        return historyDataMapper.delete(queryWrapper);
    }

}
