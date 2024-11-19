package com.example.helper.entity.request;

import lombok.Data;

import java.util.List;

@Data
public class InteractiveRequest {
    Header header;
    Parameter parameter;
    Payload payload;

    @Data
    public static class Header {
        /**
         * 应用appid，从开放平台控制台创建的应用中获取
         */
        String app_id;
        /**
         * AI人格ID
         */
        String agent_id;
        /**
         * 最大长度32 每个用户的id，用于区分不同用户
         */
        String uid;
    }

    @Data
    public static class Parameter {
        Chat chat;
    }

    @Data
    public static class Chat {
        /**
         * 需要保障用户下的唯一性 用于关联用户会话
         */
        String chat_id;

        /**
         * 上一轮会话ID
         */
        String pre_chat_id;
    }

    @Data
    public static class Payload {
        Message message;
    }

    @Data
    public static class Message {
        List<Text> text;
    }

    @Data
    public static class Text {
        String role;
        String content;
    }
}
