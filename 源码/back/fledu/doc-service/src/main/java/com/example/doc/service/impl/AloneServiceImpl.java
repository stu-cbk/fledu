package com.example.doc.service.impl;

import com.example.doc.entity.doc.Alone;
import com.example.doc.mapper.AloneMapper;
import com.example.doc.service.AloneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AloneServiceImpl implements AloneService {
    @Autowired
    private AloneMapper aloneMapper;

    public List<Alone> selectAll(){return aloneMapper.selectList(null);}
}
