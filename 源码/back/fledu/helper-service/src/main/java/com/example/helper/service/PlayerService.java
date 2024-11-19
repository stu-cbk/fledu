package com.example.helper.service;

import com.example.helper.entity.dto.PlayerDto;

public interface PlayerService {
    Boolean ifRegister(String appId,String playerName);
    String register(PlayerDto player);
    Boolean delete(PlayerDto player);
}
