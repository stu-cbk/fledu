package com.example.helper.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class InteractiveResponse {
    Header header;
    Payload payload;

    @Data
    public static class Header {
        /**
         * 会话是否成功的描述信息
         */
        String message;
        /**
         * 错误码，0表示正常，非0表示出错；详细释义可在接口说明文档最后的错误码说明了解
         */
        Integer code;
        /**
         * 会话状态，取值为[0,1,2]；0代表首次结果；1代表中间结果；2代表最后一个结果
         */
        Integer status;
        /**
         * 会话的唯一id，用于讯飞技术人员查询服务端会话日志使用,出现调用错误时建议留存该字段
         */
        String sid;
    }

    @Data
    public static class Payload {
        Choices choices;
        Usage usage;
    }

    @Data
    public static class Choices {
        /**
         * 文本响应状态，取值为[0,1,2]; 0代表首个文本结果；1代表中间文本结果；2代表最后一个文本结果
         */
        Integer status;
        /**
         * 返回的数据序号，取值为[0,9999999]
         */
        Integer seq;
        /**
         * AI的回答内容
         */
        String content;
        List<Text> text;
        /**
         * 角色标识，固定为assistant，标识角色为AI
         */
        String role;
        /**
         * 结果序号，取值为[0,10]; 当前为保留字段，开发者可忽略
         */
        Integer index;
    }

    @Data
    public static class Usage {
        int player_current_chars = 0;

        /**
         * 本轮人格发言字数
         */
        int agent_current_chars = 0;

        /**
         * 角色设定字数
         */
        int system_current_chars = 0;

        /**
         * 本轮总token数
         */
        int total_current_tokens = 0;

    }

    @Data
    public static class Text {
        String role;
        String content;
    }
}
