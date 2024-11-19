package com.example.doc.entity.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ChatRequest {

    private List<String> fileIds;

    private List<ChatMessage> messages;

    private Integer topN;
}