package com.example.ppt.service;

import com.example.ppt.entity.request.OutlineReq;
import com.example.ppt.entity.request.PPTBySidReq;

public interface AsyncTaskService {
    void createOutlineTask(Long id, OutlineReq outlineReq);
    void createPPTTask(Long id, PPTBySidReq pptBySidReq);
}
