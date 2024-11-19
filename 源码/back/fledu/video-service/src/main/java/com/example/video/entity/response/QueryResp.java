package com.example.video.entity.response;

import lombok.Data;

@Data
public class QueryResp {
    private Data1 header;
    private Data2 payload;
    @Data
    public static class Data1{
        int code;
        String message;
        String sid;
        String task_id;
        String task_status;
    }

    @Data
    public static class Data2{
        Data2_1 audio;
        Data2_2 pybuf;
    }

    @Data
    public static class Data2_1{
        String encoding;
        int sample_rate;
        int channels;
        int bit_depth;
        String audio;
    }

    @Data
    public static class Data2_2 {
        String encoding;
        String text;
    }
}
