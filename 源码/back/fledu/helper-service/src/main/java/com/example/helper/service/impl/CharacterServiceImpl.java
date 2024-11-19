package com.example.helper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.helper.entity.dto.CharacterInfo;
import com.example.helper.mapper.CharacterMapper;
import com.example.helper.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {
    @Autowired
    private CharacterMapper characterMapper;

    public int insert(CharacterInfo characterInfo)
    {
        return characterMapper.insert(characterInfo);
    }

    public int delete(Long id)
    {
        return characterMapper.deleteById(id);
    }

    public int update(CharacterInfo characterInfo)
    {
        return characterMapper.updateById(characterInfo);
    }

    public CharacterInfo selectOne(Long id)
    {
        return characterMapper.selectById(id);
    }

    public List<CharacterInfo> selectAll(String userId)
    {
        LambdaQueryWrapper<CharacterInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CharacterInfo::getUserId, userId);
        return characterMapper.selectList(queryWrapper);
    }

}
