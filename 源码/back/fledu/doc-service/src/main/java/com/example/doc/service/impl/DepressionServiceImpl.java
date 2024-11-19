package com.example.doc.service.impl;

import com.example.doc.entity.doc.Depression;
import com.example.doc.mapper.DepressionMapper;
import com.example.doc.service.DepressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepressionServiceImpl implements DepressionService {
    @Autowired
    DepressionMapper depressionMapper;
    public List<Depression> selectAll(){
        return depressionMapper.selectList(null);
    }
}
