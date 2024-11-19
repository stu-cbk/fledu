package com.example.virtual.entity.request;

import lombok.Data;

@Data
public class IatReq {
    Data_1 common;
    Data_2 business;
    Data_3 data;

    public IatReq(){}
    public IatReq(String appId, String language, String domain, String accent)
    {
        this.common = new Data_1(appId);
        this.business = new Data_2(language, domain, accent);
        this.data = new Data_3();
    }
    @Data
    public static class Data_1{
        Data_1(){}
        Data_1(String appId)
        {
            this.appId = appId;
        }
        String appId;
    }

    @Data
    public static class Data_2{
        Data_2(){}
        Data_2(String language, String domain, String accent)
        {
            this.language = language;
            this.domain = domain;
            this.accent = accent;
            this.vad_eos = 2000;
        }
        String language;
        String domain;
        String accent;
        Integer vad_eos;
    }

    @Data
    public static class Data_3{
        Data_3()
        {
            this.format = "audio/L16;rate=16000";
            this.encoding = "lame";
        }
        int status;
        String format;
        String encoding;
        String audio;
    }
}
