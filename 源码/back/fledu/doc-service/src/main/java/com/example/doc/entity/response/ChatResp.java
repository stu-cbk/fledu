package com.example.doc.entity.response;

import lombok.Data;

@Data
public class ChatResp {
    private int code;
    private String sid;
    private int status;
    private String message;
    private String content;
}
