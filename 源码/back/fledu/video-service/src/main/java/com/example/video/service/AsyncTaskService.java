package com.example.video.service;

import org.springframework.web.multipart.MultipartFile;

public interface AsyncTaskService {
    void processPPT(String fileId, MultipartFile file);
    void createVideoAsync(String fileId, int totalTask);
}
