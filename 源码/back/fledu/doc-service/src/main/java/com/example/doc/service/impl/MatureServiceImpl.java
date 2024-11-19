package com.example.doc.service.impl;

import com.example.doc.entity.doc.Mature;
import com.example.doc.mapper.MatureMapper;
import com.example.doc.service.MatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatureServiceImpl implements MatureService {
    @Autowired
    private MatureMapper matureMapper;

    public List<Mature> selectAll(){return matureMapper.selectList(null);}

}
