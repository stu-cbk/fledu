package com.example.ppt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.ppt.entity.dto.HistoryData;
import com.example.ppt.mapper.HistoryDataMapper;
import com.example.ppt.service.HistoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class HistoryDataServiceImpl implements HistoryDataService {
    @Autowired
    private HistoryDataMapper historyDataMapper;

    public List<HistoryData> selectAllData(String userId)
    {
        LambdaQueryWrapper<HistoryData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HistoryData::getUserId, userId);
        return historyDataMapper.selectList(queryWrapper);
    }

    public HistoryData selectById(Long id)
    {
        return historyDataMapper.selectById(id);
    }

    public int insert(HistoryData historyData)
    {
        return historyDataMapper.insert(historyData);
    }

    public int updateOutlineAndSid(Long id, String outline, String outlineSid, String status)
    {
        LambdaUpdateWrapper<HistoryData> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(HistoryData::getId, id)
                .set(HistoryData::getOutline, outline)
                .set(HistoryData::getOutlineSid, outlineSid)
                .set(HistoryData::getStatus, status);
        return historyDataMapper.update(null, updateWrapper);
    }

    public int updateStatus(Long id, String status)
    {
        LambdaUpdateWrapper<HistoryData> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(HistoryData::getId, id)
                .set(HistoryData::getStatus, status);
        return historyDataMapper.update(null, updateWrapper);
    }

    public int updateOutline(Long id, String outline)
    {
        LambdaUpdateWrapper<HistoryData> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(HistoryData::getId, id)
                .set(HistoryData::getOutline, outline);
        return historyDataMapper.update(null, updateWrapper);
    }

    public int updateName(Long id, String name)
    {
        LambdaUpdateWrapper<HistoryData> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(HistoryData::getId, id)
                .set(HistoryData::getName, name)
                .set(HistoryData::getLastChangeTime, new Timestamp(System.currentTimeMillis()));
        return historyDataMapper.update(null, updateWrapper);
    }

    public int updatePPT(Long id, String pptUrl, String status)
    {
        LambdaUpdateWrapper<HistoryData> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(HistoryData::getId, id)
                .set(HistoryData::getPptUrl, pptUrl)
                .set(HistoryData::getStatus, status)
                .set(HistoryData::getLastChangeTime, new Timestamp(System.currentTimeMillis()));
        return historyDataMapper.update(null, updateWrapper);
    }

    public int updatePPTSid(Long id, String pptSid, String name, String coverImgSrc, String status)
    {
        LambdaUpdateWrapper<HistoryData> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(HistoryData::getId, id)
                .set(HistoryData::getPptSid, pptSid)
                .set(HistoryData::getName, name)
                .set(HistoryData::getCoverImgSrc, coverImgSrc)
                .set(HistoryData::getStatus, status);
        return historyDataMapper.update(null, updateWrapper);
    }

    public int delete(Long id) {
        return historyDataMapper.deleteById(id);
    }
}
