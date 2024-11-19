package com.example.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.video.entity.dto.HistoryData;
import com.example.video.mapper.HistoryDataMapper;
import com.example.video.service.HistoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoryDataServiceImpl implements HistoryDataService {
    @Autowired
    private HistoryDataMapper historyDataMapper;

    public int insert(HistoryData historyData)
    {
        return historyDataMapper.insert(historyData);
    }

    public int update(HistoryData historyData)
    {
        return historyDataMapper.updateById(historyData);
    }

    public int updateStatus(Long id, String status)
    {
        LambdaUpdateWrapper<HistoryData> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(HistoryData::getId, id)
                .set(HistoryData::getStatus, status);
        return historyDataMapper.update(null, updateWrapper);
    }
    public int delete(Long id)
    {
        return historyDataMapper.deleteById(id);
    }

    public HistoryData selectById(Long id)
    {
        return historyDataMapper.selectById(id);
    }

    public List<HistoryData> selectAll(){return historyDataMapper.selectList(null);}
}
