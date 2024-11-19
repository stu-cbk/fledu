package com.example.user.service;

import com.example.user.entity.Student;

import java.util.List;

public interface StudentService{
    Student selectByName(String userName);
    Student selectByPhone(String phone);
    Student selectById(Long id);
    int insert(Student student);
    int update(Student student);
    int delete(Long id);
    List<Student> selectAll();
}
