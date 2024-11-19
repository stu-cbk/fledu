package com.example.virtual.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class TtsResp {
    String sid;
    String message;
    int code;
    Data_1 data;

    @Data
    public static class Data_1{
        int status;
        String audio;
        String ced;
    }
}
