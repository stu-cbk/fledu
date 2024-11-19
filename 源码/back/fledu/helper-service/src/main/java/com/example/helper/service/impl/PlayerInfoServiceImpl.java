package com.example.helper.service.impl;

import com.example.helper.entity.dto.PlayerInfo;
import com.example.helper.mapper.PlayerInfoMapper;
import com.example.helper.service.PlayerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerInfoServiceImpl implements PlayerInfoService {
    @Autowired
    private PlayerInfoMapper playerInfoMapper;

    public int insert(PlayerInfo playerInfo){return playerInfoMapper.insert(playerInfo);}
    public PlayerInfo select(Long id) {return playerInfoMapper.selectById(id);}
}
