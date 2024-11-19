package com.example.user.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    public UserDto(){}
    public UserDto(Student user)
    {
        this.userName = user.getUserName();
        this.type = "0";
        this.userEmail = user.getUserEmail();
        this.userPhone = user.getUserPhone();
    }
    public UserDto(Teacher user)
    {
        this.userName = user.getUserName();
        this.type = "1";
        this.userEmail = user.getUserEmail();
        this.userPhone = user.getUserPhone();
    }
    public UserDto(Admin user)
    {
        this.userName = user.getUserName();
        this.type = "2";
        this.userEmail = user.getUserEmail();
        this.userPhone = user.getUserPhone();
    }
    private Long id;
    private String userName;
    private String userPassword;
    private String userPhone;
    private String userEmail;
    private String type;
    private String code;
}
