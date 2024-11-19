package com.example.doc.service;

import java.util.Map;

public interface ChatService {
    Map<String,String> chat(String question, String fileId);
}
