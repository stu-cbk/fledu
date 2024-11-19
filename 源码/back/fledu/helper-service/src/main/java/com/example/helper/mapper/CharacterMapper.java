package com.example.helper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.helper.entity.dto.CharacterInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CharacterMapper extends BaseMapper<CharacterInfo> {

}
