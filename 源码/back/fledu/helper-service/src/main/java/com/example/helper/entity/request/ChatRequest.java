package com.example.helper.entity.request;

import com.example.helper.entity.dto.TextDto;
import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {
    Header header;
    Parameter parameter;
    Payload payload;

    @Data
    public static class Header {
        /**
         * 应用appid，从开放平台控制台创建的应用中获取
         */
        String app_id;
    }

    @Data
    public static class Parameter {
        Chat chat;
    }

    @Data
    public static class Chat {
        /**
         * 模型类型
         */
        String domain;
        int max_tokens;
    }

    @Data
    public static class Payload {
       Message message;
    }

    @Data
    public static class Message {
        List<TextDto> text;
    }
}
