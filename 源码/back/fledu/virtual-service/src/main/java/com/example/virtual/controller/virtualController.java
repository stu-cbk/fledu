package com.example.virtual.controller;

import com.example.virtual.entity.request.IatReq;
import com.example.virtual.entity.request.TtsReq;
import com.example.virtual.utils.Result;
import com.example.virtual.utils.WssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.SignatureException;

@Api("在线英文训练服务")
@RestController
public class virtualController {

    @ApiOperation(value="获得语音听写wssURL 21ms")
    @GetMapping("/getListenWssUrl")
    public Result getListenWssUrl()
    {
        try {
            WssUtil wssUtil = new WssUtil("iat-api.xfyun.cn","/v2/iat");
            return Result.suc(wssUtil.getURL());
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return Result.fail("调用接口失败");
    }

    @ApiOperation(value="获得语音听写Request 24ms")
    @GetMapping("/getListenRequest")
    @ResponseBody
    public IatReq getListenRequest()
    {
        IatReq iatReq = new IatReq("8075351b", "en_us", "iat", "mandarin");
        return iatReq;
    }

    @ApiOperation(value="获得语音合成WssURL 26ms")
    @GetMapping("/getSpeakWssUrl")
    public Result getSpeakWssUrl()
    {
        try {
            WssUtil wssUtil = new WssUtil("tts-api.xfyun.cn","/v2/tts");
            return Result.suc(wssUtil.getURL());
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return Result.fail("调用接口失败");
    }

    @ApiOperation(value="获得语音合成Request 13ms")
    @GetMapping("/getSpeakRequest")
    @ResponseBody
    public TtsReq getSpeakRequest()
    {
        TtsReq ttsReq = new TtsReq("8075351b","x4_enus_luna_assist");
        return ttsReq;
    }

}
