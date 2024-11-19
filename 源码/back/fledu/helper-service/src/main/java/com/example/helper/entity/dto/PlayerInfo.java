package com.example.helper.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("info")
public class PlayerInfo {
    @TableId(value = "id",type = IdType.INPUT)
    Long id;
    @TableField("playerId")
    String playerId;
}
