package com.example.helper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.helper.entity.dto.ChatInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper extends BaseMapper<ChatInfo> {

}
