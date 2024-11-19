package com.example.video.service.impl;

import com.example.video.service.HistoryDataService;
import com.example.video.service.AsyncTaskService;
import com.example.video.utils.CreateVideo;
import com.example.video.utils.PPTToImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {

    @Autowired
    private HistoryDataService historyDataService;

    @Async
    @Override
    public void createVideoAsync(String fileId, int totalTask) {
        try {
            CreateVideo createVideo = new CreateVideo(fileId, totalTask);
            createVideo.create();
            createVideo.concat();
            historyDataService.updateStatus(Long.valueOf(fileId), "4");
        } catch (Exception e) {
            // 处理异常情况
            // 你可能需要记录日志或者更新数据库中的状态
            e.printStackTrace();
            historyDataService.updateStatus(Long.valueOf(fileId), "2"); // 更新状态为"处理失败"
        }
    }

    @Async
    @Override
    public void processPPT(String fileId, MultipartFile file) {
        try {
            PPTToImage pptToImage = new PPTToImage(fileId);
            pptToImage.transImage(file);
            pptToImage.savePPT(file);
            historyDataService.updateStatus(Long.valueOf(fileId), "0"); // 更新状态为"图片已生成"
        } catch (IOException e) {
            e.printStackTrace();
            // 处理异常
            historyDataService.updateStatus(Long.valueOf(fileId), "-1"); // 更新状态为"处理失败"
        }
    }
}

