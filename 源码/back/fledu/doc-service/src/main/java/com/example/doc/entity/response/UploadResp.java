package com.example.doc.entity.response;

import lombok.Data;

@Data
public class UploadResp{
    private boolean flag;
    private int code;
    private String desc;
    private String sid;

    private Datas data;

    @Data
    public static class Datas{
        private String parseType;
        private int quantity;
        private String fileId;
    }
}
