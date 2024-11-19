package com.example.helper.service.impl;

import com.example.helper.service.AsyncTaskService;
import com.example.helper.service.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {

    @Autowired
    private HelperService helperService;

    @Async
    @Override
    public void deleteCharacter(String appId, String cid, String name)
    {
        try {
            helperService.deleteHelper(appId, cid, name);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
