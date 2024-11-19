package com.example.helper.entity.dto;

import lombok.Data;

@Data
public class PlayerDto {
    String appId;
    String playerId;
    String playerName;
    String playerType;
    String description;
    String senderIdentity;
}
