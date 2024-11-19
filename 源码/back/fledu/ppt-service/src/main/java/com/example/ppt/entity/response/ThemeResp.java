package com.example.ppt.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class ThemeResp {
    private boolean flag;
    private int code;
    private String desc;
    private Integer count;
    private List<Datas> data;

    @Data
    public static class Datas {
        private String key;
        private String name;
        private String thumbnail;
    }
}
