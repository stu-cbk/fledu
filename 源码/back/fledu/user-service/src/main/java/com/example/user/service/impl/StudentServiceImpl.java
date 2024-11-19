package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.entity.Student;
import com.example.user.mapper.StudentMapper;
import com.example.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentMapper studentMapper;

    public Student selectByName(String userName)
    {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getUserName, userName);
        return studentMapper.selectOne(queryWrapper);
    }

    public Student selectByPhone(String phone)
    {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getUserPhone, phone);
        return studentMapper.selectOne(queryWrapper);
    }

    public Student selectById(Long id) {return studentMapper.selectById(id);}

    public int insert(Student student)
    {
        return studentMapper.insert(student);
    }

    public int delete(Long id){ return studentMapper.deleteById(id);}

    public int update(Student student){ return studentMapper.updateById(student); }

    public List<Student> selectAll()
    {
        List<Student> students = studentMapper.selectList(null);
        for (Student student : students)
            student.setUserPassword(null);
        return students;
    }

}
