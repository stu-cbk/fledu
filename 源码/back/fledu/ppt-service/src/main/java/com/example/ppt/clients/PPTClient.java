package com.example.ppt.clients;

import com.example.ppt.entity.request.OutlineReq;
import com.example.ppt.entity.request.PPTBySidReq;
import com.example.ppt.entity.request.PPTReq;
import com.example.ppt.entity.response.OutlineResp;
import com.example.ppt.entity.response.PPTResp;
import com.example.ppt.entity.response.ProgressResp;
import com.example.ppt.entity.response.ThemeResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ppt-service", url = "https://zwapi.xfyun.cn/api/aippt")
public interface PPTClient {
    @PostMapping("/createOutline")
    OutlineResp createOutline(@RequestBody OutlineReq outlineReq);
    @PostMapping("/create")
    PPTResp createPPT(@RequestBody PPTReq pptReq);
    @PostMapping("/createBySid")
    PPTResp createPPTBySid(@RequestBody PPTBySidReq pptReq);
    @GetMapping("/progress")
    ProgressResp getProgress(@RequestParam("sid") String sid);
    @GetMapping("/themeList")
    ThemeResp getTheme();
}
