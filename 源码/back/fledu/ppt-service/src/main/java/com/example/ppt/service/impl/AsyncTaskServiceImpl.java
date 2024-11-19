package com.example.ppt.service.impl;

import com.example.ppt.clients.PPTClient;
import com.example.ppt.entity.request.OutlineReq;
import com.example.ppt.entity.request.PPTBySidReq;
import com.example.ppt.entity.response.OutlineResp;
import com.example.ppt.entity.response.PPTResp;
import com.example.ppt.service.AsyncTaskService;
import com.example.ppt.service.HistoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;

@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {

    @Autowired
    private PPTClient pptClient;

    @Autowired
    private HistoryDataService historyDataService;

    @Async
    @Override
    public void createOutlineTask(Long id, OutlineReq outlineReq)
    {
        try {
            OutlineResp outlineResp = pptClient.createOutline(outlineReq);
            // 更新大纲和sid，同时更新状态
            historyDataService.updateOutlineAndSid(id, JSON.toJSONString(outlineResp.getData().getOutline()),
                    outlineResp.getData().getSid(), "1");
        }catch (Exception e){
            e.printStackTrace();
            // 删除这一条记录
            historyDataService.delete(id);
        }
    }

    @Async
    @Override
    public void createPPTTask(Long id, PPTBySidReq pptBySidReq)
    {
        try {
            PPTResp pptResp = pptClient.createPPTBySid(pptBySidReq);
            // 更新pptSid、ppt标题以及封面 同时更新状态
            historyDataService.updatePPTSid(id, pptResp.getData().getSid(),
                    pptResp.getData().getTitle(),pptResp.getData().getCoverImgSrc(), "3");
        }catch (Exception e){
            e.printStackTrace();
            historyDataService.updateStatus(id, "1");
        }
    }
}
