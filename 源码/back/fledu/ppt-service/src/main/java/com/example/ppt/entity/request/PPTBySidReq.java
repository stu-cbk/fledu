package com.example.ppt.entity.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PPTBySidReq {
    public PPTBySidReq(){}
    public PPTBySidReq(String sid, String outline){
        this.sid = sid;
        this.outline = outline;
    }
    private String sid;
    private String outline;
}
