package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.user.entity.Admin;
import com.example.user.mapper.AdminMapper;
import com.example.user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public Admin selectByName(String userName)
    {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUserName, userName);
        return adminMapper.selectOne(queryWrapper);
    }

    public Admin selectByPhone(String phone)
    {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUserPhone, phone);
        return adminMapper.selectOne(queryWrapper);
    }

    public Admin selectById(Long id)
    {
        return adminMapper.selectById(id);
    }

    public int insert(Admin admin)
    {
        return adminMapper.insert(admin);
    }

    public int delete(Long id) {return adminMapper.deleteById(id);}

    public int update(Admin admin) {return adminMapper.updateById(admin);}

    public List<Admin> selectAll(){
        List<Admin> admins = adminMapper.selectList(null);
        for (Admin admin : admins)
            admin.setUserPassword(null);
        return admins;
    }
}
