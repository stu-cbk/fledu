package com.example.doc.utils;

import com.alibaba.fastjson.JSON;
import com.example.doc.entity.chat.ChatMessage;
import com.example.doc.entity.chat.ChatRequest;

import java.util.Collections;

public class WssUtils {
    private static String url = "wss://chatdoc.xfyun.cn/openapi/chat";
    private static String appId = "xxx";
    private static String secret = "xxx";

    public static String createURL()
    {
        // 构造url鉴权
        long ts = System.currentTimeMillis() / 1000;
        String signature = ApiAuthAlgorithm.getSignature(appId, secret, ts);
        String requestUrl = url + "?" + "appId=" + appId + "&timestamp=" + ts + "&signature=" + signature;
        return requestUrl;
    }

    public static String createSendText(String question, String fileId)
    {
        ChatMessage message = new ChatMessage();
        message.setRole("user");
        message.setContent(question);
        // 请求内容
        ChatRequest request = ChatRequest.builder()
                .fileIds(Collections.singletonList(fileId))
                .topN(3)
                .messages(Collections.singletonList(message))
                .build();
        String sendText = JSON.toJSONString(request);
        return sendText;
    }
}
