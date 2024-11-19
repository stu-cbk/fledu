package com.example.virtual.entity.request;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class TtsReq {
    Data_1 common;
    Data_2 business;
    Data_3 data;

    public TtsReq(){}
    public TtsReq(String appId, String speaker)
    {
        this.common = new Data_1(appId);
        this.business = new Data_2(speaker);
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
        Data_2(String vcn)
        {
            this.aue = "lame";
            this.sfl = 1;
            this.vcn = vcn;
            this.speed = 75;
            this.tte = "UTF8";
        }
        String aue;
        Integer sfl;
        String auf;
        String vcn;
        Integer speed;
        Integer volume;
        Integer pitch;
        Integer bgs;
        String tte;
        String reg;
        String rdn;
    }

    @Data
    public static class Data_3 {
        int status;
        String text;
    }
}
