package com.example.ppt.entity.dto;

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
    public HistoryData(String userId, String status)
    {
        this.userId = userId;
        this.status = status;
        this.lastChangeTime = new Timestamp(System.currentTimeMillis());
    }
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    Long id;
    @TableField("userId")
    String userId;
    @TableField("status")
    String status;
    @TableField("outlineSid")
    String outlineSid;
    @TableField("pptSid")
    String pptSid;
    @TableField("name")
    String name;
    @TableField("outline")
    String outline;
    @TableField("pptUrl")
    String pptUrl;
    @TableField("coverImgSrc")
    String coverImgSrc;
    @TableField("lastChangeTime")
    Timestamp lastChangeTime;
}
