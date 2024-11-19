package com.example.doc.entity.doc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@TableName("history_data")
public class HistoryData {
    public HistoryData(){}
    public HistoryData(String fileId, String userId, String type, String answer, Integer score)
    {
        this.fileId = fileId;
        this.userId = userId;
        this.type = type;
        this.answer = answer;
        this.score = score;
        this.lastChangeTime = new Timestamp(System.currentTimeMillis());
    }
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    Long id;
    @TableField("fileId")
    String fileId;
    @TableField("userId")
    String userId;
    @TableField("type")
    String type;
    @TableField("answer")
    String answer;
    @TableField("score")
    Integer score;
    @TableField("lastChangeTime")
    Timestamp lastChangeTime;
}
