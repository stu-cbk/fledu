package com.example.helper.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("new_chat")
public class NewChatInfo {
    public NewChatInfo(){}
    public NewChatInfo(String userId)
    {
        this.userId = userId;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    Long id;
    @TableField("userId")
    String userId;
    @TableField("time")
    Timestamp time;
}
