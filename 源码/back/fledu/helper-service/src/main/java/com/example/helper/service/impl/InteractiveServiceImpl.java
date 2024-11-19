package com.example.helper.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.helper.entity.request.InteractiveRequest;
import com.example.helper.entity.response.InteractiveResponse;
import com.example.helper.service.InteractiveService;
import com.example.helper.utils.ApiAuthAlgorithm;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Service
public class InteractiveServiceImpl implements InteractiveService {
    private String url = "wss://ai-character.xfyun.cn/api/open/interactivews/";
    private String appId = "07bd5f36";
    private String secret = "NTI3MDU5MjM2MGZhNDdiNmU2MTJhOTVm";

    public Map<String,String> chat(String playerId, String agentId,String chatId, String preChatId, List<InteractiveRequest.Text> text)
    {
        //构造请求
        try {
            InteractiveRequest interactiveRequest = new InteractiveRequest();

            //设置请求头
            final InteractiveRequest.Header header = new InteractiveRequest.Header();
            header.setApp_id(appId);
            header.setUid(playerId);
            header.setAgent_id(agentId);
            interactiveRequest.setHeader(header);

            //设置parameter
            InteractiveRequest.Parameter parameter = new InteractiveRequest.Parameter();
            InteractiveRequest.Chat chat = new InteractiveRequest.Chat();
            chat.setChat_id(chatId);
            //如重新开启会话比希望连续上次会话，需要带上pre_chat_id
            chat.setPre_chat_id(preChatId);
            parameter.setChat(chat);
            interactiveRequest.setParameter(parameter);

            //设置payload
            InteractiveRequest.Payload payload = new InteractiveRequest.Payload();
            InteractiveRequest.Message message = new InteractiveRequest.Message();
            message.setText(text);
            payload.setMessage(message);
            interactiveRequest.setPayload(payload);
            String sendText = JSON.toJSONString(interactiveRequest);

            Long ts = System.currentTimeMillis();
            String signature = ApiAuthAlgorithm.getSignature(appId, secret, ts);
            String requestUrl = url + chatId + "?" + "appId=" + appId + "&timestamp=" + ts + "&signature=" + signature;

            Map<String,String> map = new HashMap<>();
            map.put("sendText", sendText);
            map.put("requestUrl", requestUrl);
            // map.put("chatId", chatId);
            return map;
        }catch (Exception e){
            System.out.println(e);
            return new HashMap<>();
        }
    }
}
