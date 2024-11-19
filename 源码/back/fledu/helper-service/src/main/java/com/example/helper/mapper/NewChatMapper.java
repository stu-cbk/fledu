package com.example.helper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.helper.entity.dto.NewChatInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewChatMapper extends BaseMapper<NewChatInfo> {

}
