package com.example.ppt.entity.response;

import lombok.Data;

@Data
public class ProgressResp {
    private int code;
    private String desc;
    private Datas data;

    @Data
    public static class Datas{
        private int process;
        private String pptId;
        private String pptUrl;
    }
}
