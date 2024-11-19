package com.example.doc.entity.doc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("mature")
public class Mature {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    @TableField("question")
    String question;
    @TableField("a")
    String a;
    @TableField("b")
    String b;
    @TableField("c")
    String c;
    @TableField("d")
    String d;
    @TableField("e")
    String e;
}
