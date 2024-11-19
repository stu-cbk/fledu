package com.example.doc.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Result<T>{
    /**
     * 1.status状态值：代表本次请求response的状态结果。
     */
    private Integer status;
    /**
     * 2.response描述：对本次状态码的描述。
     */
    private String msg;
    /**
     * 3.data数据：本次返回的数据。
     */
    private T data;

    private Result(){}

    /**
     * 成功，创建ResResult：没data数据
     */
    public static Result suc() {
        Result result = new Result();
        result.setStatus(ResultCode.SUCCESS);
        result.setMsg("操作成功");
        return result;
    }

    /**
     * 成功，创建ResResult：有data数据
     */
    public static Result suc(Object data) {
        Result result = new Result();
        result.setStatus(ResultCode.SUCCESS);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    /**
     * 失败，指定desc
     */
    public static Result fail(String desc) {
        Result result = new Result();
        result.setStatus(ResultCode.ERROR);
        result.setMsg(desc);
        return result;
    }

    public static Result isExist(Integer code){
        Result result = new Result();
        result.setStatus(code);
        result.setMsg("返回用户名是否存在的信息");
        return result;
    }



}
