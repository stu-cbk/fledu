package com.example.video.entity.request;

import lombok.Data;

@Data
public class QueryReq {
    public QueryReq(){}
    public QueryReq(String appId, String taskId)
    {
        this.header = new Data1(appId, taskId);
    }
    private Data1 header;

    @Data
    public static class Data1{
        public Data1(){}
        public Data1(String appId, String taskId){
            this.app_id = appId;
            this.task_id = taskId;
        }
        String app_id;
        String task_id;
    }
}
