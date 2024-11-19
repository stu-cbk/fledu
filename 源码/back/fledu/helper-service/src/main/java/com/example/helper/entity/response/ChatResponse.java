package com.example.helper.entity.response;

import com.example.helper.entity.dto.TextDto;
import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {
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
        List<TextDto> text;
    }

}
