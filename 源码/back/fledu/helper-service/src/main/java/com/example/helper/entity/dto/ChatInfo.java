package com.example.helper.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@TableName("latest_chat")
public class ChatInfo {
    public ChatInfo(){}
    public ChatInfo(String userId,String cid,String latestChatId)
    {
        this.userId = userId;
        this.cid = cid;
        this.latestChatId = latestChatId;
    }
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    Long id;
    @TableField("userId")
    String userId;
    @TableField("cid")
    String cid;
    @TableField("latestChatId")
    String latestChatId;
}
