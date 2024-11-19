package com.example.helper.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("character_info")
public class CharacterInfo {
    public CharacterInfo(){}
    public CharacterInfo(String userId, String name, String mission, String cid)
    {
        this.userId = userId;
        this.name = name;
        this.mission = mission;
        this.cid = cid;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    Long id;
    @TableField("userId")
    String userId;
    @TableField("name")
    String name;
    @TableField("mission")
    String mission;
    @TableField("cid")
    String cid;
    @TableField("time")
    Timestamp time;
}
