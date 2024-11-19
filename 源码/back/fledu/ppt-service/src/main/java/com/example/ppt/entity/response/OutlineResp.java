package com.example.ppt.entity.response;

import lombok.Data;

@Data
public class OutlineResp {
    private boolean flag;
    private int code;
    private String desc;
    private Integer count;
    private Datas data;

    @Data
    public static class Datas {
        private String sid;
        private String coverImgSrc;
        private String title;
        private String subTitle;
        private OutlineVo outline;
    }
}
