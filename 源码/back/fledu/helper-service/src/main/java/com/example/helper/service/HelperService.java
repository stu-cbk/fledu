package com.example.helper.service;

import com.example.helper.entity.dto.HelperDto;

public interface HelperService {
    String createHelper(HelperDto helper);
    Boolean editHelper(HelperDto helper);
    Boolean getHelper(String appId, String agentId, String agentName);
    Boolean deleteHelper(String appId, String agentId, String agentName);
}
