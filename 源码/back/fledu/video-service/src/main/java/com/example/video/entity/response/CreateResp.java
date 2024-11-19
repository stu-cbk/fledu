package com.example.video.entity.response;

import lombok.Data;

@Data
public class CreateResp {
    private Data1 header;

    @Data
    public static class Data1{
        int code;
        String message;
        String sid;
        String task_id;
    }
}
