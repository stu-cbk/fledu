package com.example.video.clients;

import com.example.video.entity.request.CreateReq;
import com.example.video.entity.request.QueryReq;
import com.example.video.entity.response.CreateResp;
import com.example.video.entity.response.QueryResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mp3-service", url = "https://api-dx.xf-yun.com/v1/private")
public interface Mp3Client {
    @PostMapping("/dts_create")
    CreateResp create(@RequestBody CreateReq createReq);
    @PostMapping("/dts_query")
    QueryResp query(@RequestBody QueryReq queryReq);
}
