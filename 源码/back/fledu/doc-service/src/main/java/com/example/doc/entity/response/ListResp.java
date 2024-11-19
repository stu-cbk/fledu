package com.example.doc.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class ListResp{
    private boolean flag;
    private int code;
    private String desc;
    private String sid;

    private Datas data;

    @Data
    public static class Datas{
        private Integer total;
        private List<String> rows;
    }

}
