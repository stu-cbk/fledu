package com.example.video.entity.request;

import lombok.Data;

@Data
public class CreateReq {
    public CreateReq(){}

    public CreateReq(String text, String appId, String vcn){
        this.header = new Data1(appId);
        this.parameter = new Data2(vcn);
        this.payload = new Data3(text);
    }

    private Data1 header;

    private Data2 parameter;

    private Data3 payload;

    @Data
    public static class Data1 {
        public Data1(){}
        public Data1(String appId){
            this.app_id = appId;
        }
        String app_id;
    }

    @Data
    public static class Data2 {
        public Data2(){}
        public Data2(String vcn){
            this.dts = new Data2_1(vcn);
        }
        Data2_1 dts;
    }

    @Data
    public static class Data2_1 {
        public Data2_1(){}
        public Data2_1(String vcn){
            this.vcn = vcn;
            this.audio = new Data2_1_1();
            this.speed = 30;
        }
        String vcn;
        Data2_1_1 audio;
        int speed;
    }

    @Data
    public static class Data2_1_1 {
        String encoding;
        public Data2_1_1(){
            this.encoding = "lame";
        }
    }

    @Data
    public static class Data3 {
        public Data3(){}
        public Data3(String text){
            this.text = new Data3_1(text);
        }
        Data3_1 text;
    }

    @Data
    public static class Data3_1{
        public Data3_1(){}
        public Data3_1(String text){
            this.text = text;
            this.format = "plain";
        }
        String text;
        String format;
    }

}
