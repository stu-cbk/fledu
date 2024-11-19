package com.example.doc.service.impl;

import com.example.doc.entity.doc.SocialAvoidance;
import com.example.doc.mapper.SocialAvoidanceMapper;
import com.example.doc.service.SocialAvoidanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialAvoidanceServiceImpl implements SocialAvoidanceService {
    @Autowired
    private SocialAvoidanceMapper socialAvoidanceMapper;

    public List<SocialAvoidance> selectAll() {return socialAvoidanceMapper.selectList(null);}
}
