package com.example.helper.entity.dto;

import lombok.Data;

@Data
public class InteractiveDto {
    String appId;
    String agentId;
    String playerId;
    String chatId;
    String interactionType;  //  obs or cvn
    String description;
}
