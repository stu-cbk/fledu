package com.example.doc.entity.doc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("social_avoidance")
public class SocialAvoidance {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    @TableField("question")
    String question;
    @TableField("a")
    String a;
    @TableField("b")
    String b;
}
