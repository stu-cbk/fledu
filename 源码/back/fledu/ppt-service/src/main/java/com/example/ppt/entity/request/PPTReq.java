package com.example.ppt.entity.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PPTReq {
    public PPTReq(){}
    public PPTReq(String q, String t)
    {
        this.query = q;
        this.theme = t;
        this.author = "茯苓教育";
    }

    private String query;
    private String theme;
    private String author;
}
