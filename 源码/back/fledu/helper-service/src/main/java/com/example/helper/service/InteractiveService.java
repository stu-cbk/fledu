package com.example.helper.service;

import com.example.helper.entity.request.InteractiveRequest;

import java.util.List;
import java.util.Map;

public interface InteractiveService {
    Map<String,String> chat(String playerId, String agentId, String chatId, String preChatId, List<InteractiveRequest.Text> text);
}
