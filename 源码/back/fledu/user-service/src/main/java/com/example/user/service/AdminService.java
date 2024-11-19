package com.example.user.service;

import com.example.user.entity.Admin;

import java.util.List;

public interface AdminService {
    Admin selectByName(String userName);
    Admin selectByPhone(String phone);
    Admin selectById(Long id);
    int insert(Admin admin);
    int delete(Long id);
    int update(Admin admin);
    List<Admin> selectAll();
}
