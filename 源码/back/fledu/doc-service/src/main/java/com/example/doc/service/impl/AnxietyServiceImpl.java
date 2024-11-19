package com.example.doc.service.impl;

import com.example.doc.entity.doc.Anxiety;
import com.example.doc.mapper.AnxietyMapper;
import com.example.doc.service.AnxietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnxietyServiceImpl implements AnxietyService {
    @Autowired
    private AnxietyMapper anxietyMapper;

    public List<Anxiety> selectAll() {return anxietyMapper.selectList(null);}
}
