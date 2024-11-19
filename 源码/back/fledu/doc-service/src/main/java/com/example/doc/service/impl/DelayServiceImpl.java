package com.example.doc.service.impl;

import com.example.doc.entity.doc.Delay;
import com.example.doc.mapper.DelayMapper;
import com.example.doc.service.DelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelayServiceImpl implements DelayService {
    @Autowired
    private DelayMapper delayMapper;

    public List<Delay> selectAll(){return delayMapper.selectList(null);}
}
