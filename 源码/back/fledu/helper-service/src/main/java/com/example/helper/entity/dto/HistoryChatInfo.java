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
@TableName("history_chat")
public class HistoryChatInfo {
    public HistoryChatInfo(){}
    public HistoryChatInfo(String userId,ChatDto chatDto)
    {
        this.userId = userId;
        this.cid = chatDto.getCid();
        this.question = chatDto.getQuestion();
        this.answer = chatDto.getAnswer();
    }
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    Long id;
    @TableField("userId")
    String userId;
    @TableField("cid")
    String cid;
    @TableField("question")
    String question;
    @TableField("answer")
    String answer;
}
