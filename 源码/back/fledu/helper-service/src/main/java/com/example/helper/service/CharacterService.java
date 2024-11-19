package com.example.helper.service;

import com.example.helper.entity.dto.CharacterInfo;

import java.util.List;

public interface CharacterService {
    int insert(CharacterInfo characterInfo);
    int delete(Long id);
    int update(CharacterInfo characterInfo);
    CharacterInfo selectOne(Long id);
    List<CharacterInfo> selectAll(String userId);
}
