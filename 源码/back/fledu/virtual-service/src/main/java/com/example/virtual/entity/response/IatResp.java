package com.example.virtual.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class IatResp {
    String sid;
    String message;
    int code;
    Data_1 data;

    @Data
    public static class Data_1{
        int status;
        Data_1_1 result;
    }

    @Data
    public static class Data_1_1{
        int sn;
        boolean ls;
        int bg;
        int ed;
        List<Data_1_1_1> ws;
        Data_1_1_2 vad;
    }

    @Data
    public static class Data_1_1_2{
        List<Data_1_1_2_1> ws;
    }

    @Data
    public static class Data_1_1_2_1{
        int bg;
        int ed;
    }

    @Data
    public static class Data_1_1_1{
        int bg;
        List<Data_1_1_1_1> cw;
    }

    @Data
    public static class Data_1_1_1_1{
        String w;
    }
}
