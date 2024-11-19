package com.example.helper.service;

import com.example.helper.entity.dto.PlayerInfo;

public interface PlayerInfoService {
    int insert(PlayerInfo playerInfo);
    PlayerInfo select(Long id);
}
