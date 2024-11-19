package com.example.video.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("history_data")
public class HistoryData {
    public HistoryData(){}
    public HistoryData(String status){
        this.status = status;
    }
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    Long id;
    @TableField("status")
    String status;
    @TableField("taskIdList")
    String taskIdList;
    @TableField("taskStatus")
    String taskStatus;
    @TableField("totalTask")
    Integer totalTask;
}
