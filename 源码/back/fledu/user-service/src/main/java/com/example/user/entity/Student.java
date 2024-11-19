package com.example.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@TableName("student")
public class Student {
    // 无参数构造函数
    public Student() {}

    public Student(UserDto user)
    {
        this.userName = user.getUserName();
        this.userPassword = user.getUserPassword();
        this.userEmail = user.getUserEmail();
        this.userPhone = user.getUserPhone();
    }
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("userName")
    private String userName;
    @TableField("userPassword")
    private String userPassword;
    @TableField("userPhone")
    private String userPhone;
    @TableField("userEmail")
    private String userEmail;
}
