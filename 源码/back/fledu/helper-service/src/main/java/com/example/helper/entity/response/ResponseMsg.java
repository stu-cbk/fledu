package com.example.helper.entity.response;

import lombok.Data;

@Data
public class ResponseMsg<T>{
    private boolean success;

    private Integer code;

    private String message;

    private T data;
}
