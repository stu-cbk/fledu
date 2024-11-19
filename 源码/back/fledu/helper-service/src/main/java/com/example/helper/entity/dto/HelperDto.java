package com.example.helper.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class HelperDto {
    String appId;
    String playerId;
    String agentId;
    String agentName;
    String agentType;
    String description;
    String identity;
    String personalityDescription;
    List<LanguageStyle> languageStyle;
    String hobby;
    String speaker;
    String keyPersonality;
    String mission;

    @Data
    public static class LanguageStyle{
        String field;
        String example;
        String scene;
    }
}
