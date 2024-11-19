package com.example.doc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.doc.entity.chat.ChatMessage;
import com.example.doc.entity.chat.ChatRequest;
import com.example.doc.entity.response.ChatResp;
import com.example.doc.service.ChatService;
import com.example.doc.utils.ApiAuthAlgorithm;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@Service
public class ChatServiceImpl implements ChatService {
    private String url = "wss://chatdoc.xfyun.cn/openapi/chat";
    private String appId = "8075351b";
    private String secret = "ZmMzMmY1Y2Y1ZWVkMGRjMGYzNjdjNDYw";

    public Map<String,String> chat(String question, String fileId){
        try {
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

            // 构造url鉴权
            long ts = System.currentTimeMillis() / 1000;
            String signature = ApiAuthAlgorithm.getSignature(appId, secret, ts);
            String requestUrl = url + "?" + "appId=" + appId + "&timestamp=" + ts + "&signature=" + signature;

            Map<String,String> map = new HashMap<>();
            map.put("requestUrl", requestUrl);
            map.put("sendText", sendText);
            map.put("fileId", fileId);
            return map;
            /*
            // wss
            Request wsRequest = (new Request.Builder()).url(requestUrl).build();
            final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
            final StringBuffer buffer = new StringBuffer();
            CountDownLatch latch = new CountDownLatch(1);

            WebSocket webSocket = okHttpClient.newWebSocket(wsRequest, new WebSocketListener() {
                @Override
                public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                    System.out.println("WebSocket Opened");
                }
                @Override
                public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                    System.out.println("WebSocket Closed: " + reason);
                    okHttpClient.connectionPool().evictAll();
                    latch.countDown();
                }

                @Override
                public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                    System.out.println("WebSocket Failure: " + t.getMessage());
                    okHttpClient.connectionPool().evictAll();
                    latch.countDown();
                }

                @Override
                public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                    System.out.println(text);
                    ChatResp response = JSONObject.parseObject(text, ChatResp.class);
                    if (response.getContent() != null) {
                        String content = response.getContent();
                        buffer.append(content);
                    }
                    if (response.getStatus() == 2){
                        webSocket.close(1000, "websocket finish");
                        okHttpClient.connectionPool().evictAll();
                    }
                }
            });

            System.out.println(sendText);
            webSocket.send(sendText);

            // 等待 WebSocket 处理完成
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "调用失败";
            }
            */

        }catch (Exception e){
            System.out.println(e);
            return new HashMap<>();
        }
    }
}
