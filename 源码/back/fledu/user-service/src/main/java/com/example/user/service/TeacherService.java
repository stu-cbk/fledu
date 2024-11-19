package com.example.user.service;

import com.example.user.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher selectByName(String userName);
    Teacher selectByPhone(String phone);
    Teacher selectById(Long id);
    int insert(Teacher teacher);
    int delete(Long id);
    int update(Teacher teacher);
    List<Teacher> selectAll();
}
