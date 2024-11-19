package com.example.doc.entity.doc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@TableName("history_chat")
public class HistoryChat {
    public HistoryChat(){}
    public HistoryChat(String fileId, String question, String answer)
    {
        this.fileId = fileId;
        this.question = question;
        this.answer = answer;
    }
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    Long id;
    @TableField("fileId")
    String fileId;
    @TableField("question")
    String question;
    @TableField("answer")
    String answer;
}
