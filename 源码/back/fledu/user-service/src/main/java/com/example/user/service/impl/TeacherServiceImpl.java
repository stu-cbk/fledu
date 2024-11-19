package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.user.entity.Teacher;
import com.example.user.mapper.TeacherMapper;
import com.example.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    public Teacher selectByName(String userName)
    {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getUserName, userName);
        return teacherMapper.selectOne(queryWrapper);
    }

    public Teacher selectByPhone(String phone)
    {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getUserPhone, phone);
        return teacherMapper.selectOne(queryWrapper);
    }

    public Teacher selectById(Long id)
    {
        return teacherMapper.selectById(id);
    }

    public int insert(Teacher teacher)
    {
        return teacherMapper.insert(teacher);
    }

    public int delete(Long id) {return teacherMapper.deleteById(id);}

    public int update(Teacher teacher) {return teacherMapper.updateById(teacher);}

    public List<Teacher> selectAll()
    {
        List<Teacher> teachers = teacherMapper.selectList(null);
        for (Teacher teacher:teachers)
            teacher.setUserPassword(null);
        return teachers;
    }
}
