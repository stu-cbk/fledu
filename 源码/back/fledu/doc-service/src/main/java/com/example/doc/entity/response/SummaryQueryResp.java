package com.example.doc.entity.response;

import lombok.Data;

@Data
public class SummaryQueryResp {
    private int code;
    private String desc;
    private String sid;
    private Datas data;

    @Data
    public static class Datas{
        String summaryStatus;
        String summary;
    }
}
