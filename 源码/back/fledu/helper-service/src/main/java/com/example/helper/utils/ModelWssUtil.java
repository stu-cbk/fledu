package com.example.helper.utils;

import com.alibaba.fastjson.JSON;
import com.example.helper.entity.dto.TextDto;
import com.example.helper.entity.request.ChatRequest;
import lombok.Data;

import java.security.SignatureException;
import java.util.List;

public class ModelWssUtil {

    private String url = "wss://spark-api.xf-yun.com/v1.1/chat";
    private String requestLine = "/v1.1/chat";
    private String appId = "xxx";
    private String playerId = "xxx";
    private String secret = "xxx";

    public String getSendText(List<TextDto> messages)
    {

        ChatRequest chatRequest = new ChatRequest();
        ChatRequest.Header header = new ChatRequest.Header();
        header.setApp_id(appId);
        chatRequest.setHeader(header);

        ChatRequest.Parameter parameter = new ChatRequest.Parameter();
        ChatRequest.Chat chat = new ChatRequest.Chat();
        chat.setDomain("general");
        chat.setMax_tokens(512);
        parameter.setChat(chat);
        chatRequest.setParameter(parameter);

        ChatRequest.Payload payload = new ChatRequest.Payload();
        ChatRequest.Message message = new ChatRequest.Message();
        message.setText(messages);
        payload.setMessage(message);
        chatRequest.setPayload(payload);

        return JSON.toJSONString(chatRequest);
    }

    public String getWssURL()
    {
        AuthUtil authUtil = null;
        try {
            authUtil = new AuthUtil(requestLine);
        } catch (SignatureException e) {
            e.printStackTrace();
            return "mistake";
        }
        return url + "?authorization=" + authUtil.getAuthorization()
                + "&date=" + authUtil.getTs() + "&host=" + authUtil.getHost();
    }
}
