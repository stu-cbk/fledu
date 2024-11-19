package com.example.ppt.controller;


import com.alibaba.fastjson.JSON;
import com.example.ppt.clients.PPTClient;
import com.example.ppt.entity.dto.HistoryData;
import com.example.ppt.entity.dto.HistoryDataDto;
import com.example.ppt.entity.request.OutlineReq;
import com.example.ppt.entity.request.PPTBySidReq;
import com.example.ppt.entity.response.ProgressResp;
import com.example.ppt.service.AsyncTaskService;
import com.example.ppt.service.HistoryDataService;
import com.example.ppt.utils.Result;
import com.example.ppt.utils.ThemeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Api("ppt生成服务")
@RestController
public class PPTController {

    @Autowired
    private PPTClient pptClient;

    @Autowired
    private HistoryDataService historyDataService;

    @Autowired
    private AsyncTaskService asyncTaskService;

    // 使用status维护状态 0 创建大纲生成任务 1 完成大纲生成任务 2 创建ppt生成任务 3 发送ppt生成消息 4 完成ppt生成任务
    @ApiOperation(value="得到字段主键Id 需传入query和theme 55ms")
    @PostMapping("/createOutline")
    public Result createOutline(@RequestHeader("userId") String userId, @RequestBody OutlineReq outlineReq)
    {
        if (userId == null) return Result.fail("系统内部错误");
        if (outlineReq == null) return Result.fail("传入数据不能为空");
        try{
            HistoryData historyData = new HistoryData(userId, "0");
            int s = historyDataService.insert(historyData);

            // 异步创建大纲生成任务
            asyncTaskService.createOutlineTask(historyData.getId(), outlineReq);

            if (s == 1) return Result.suc(historyData.getId().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("创建大纲生成任务失败");
    }

    @ApiOperation(value="查看大纲生成任务进度 0 表示未生成 1 表示已生成 24ms")
    @GetMapping("/queryOutline")
    public Result queryOutline(@RequestParam("id") Long id)
    {
        if (id == null) return Result.fail("传入数据不能为null");
        try{
            HistoryData historyData = historyDataService.selectById(id);
            if (historyData == null) return Result.fail("传入数据错误");
            String status = historyData.getStatus();
            if (status.equals("0")) return Result.suc(0);
            if (status.equals("1")) return Result.suc(1);
            return Result.fail("调用逻辑错误");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("查询大纲生成进度失败");
    }

    @ApiOperation(value="更新ppt大纲 需传入字段主键id和ppt大纲json字符串 27ms")
    @PostMapping("/updateOutline")
    public Result updateOutline(@RequestBody HistoryDataDto historyDataDto)
    {
        if (historyDataDto == null) return Result.fail("传入数据不能为null");
        Long id = historyDataDto.getId();
        String outline = historyDataDto.getOutline();
        if (id == null || outline == null) return Result.fail("传入数据不能为null");
        try{
            historyDataService.updateOutline(id, outline);
            return Result.suc("更新成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("更新大纲失败");
    }

    @ApiOperation(value="ppt生成 需传入字段主键id 在完成大纲生成后才能调用 41ms")
    @GetMapping("/createPPT")
    public Result createPPT(@RequestParam("id") Long id)
    {
        if (id == null) return Result.fail("传入数据不能为null");
        try{
            HistoryData historyData = historyDataService.selectById(id);
            if (historyData == null) return Result.fail("传入数据错误");
            String status = historyData.getStatus();
            if (status.equals("0")) return Result.fail("接口调用逻辑不当");
            String outlineSid = historyData.getOutlineSid();
            String outline = historyData.getOutline();
            if (outlineSid == null || outline == null) return Result.fail("系统内部错误");
            int s = historyDataService.updateStatus(id, "2");

            PPTBySidReq pptBySidReq = new PPTBySidReq(outlineSid, outline);

            // 异步创建ppt生成任务
            asyncTaskService.createPPTTask(id, pptBySidReq);

            if (s == 1) return Result.suc("正在生成ppt");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("创建ppt失败");
    }

    @ApiOperation(value="查询ppt生成进度 需传入字段主键id 在完成ppt生成后才能调用 " +
            "0表示未生成 1表示已生成 该api限流 3s访问一次 第一次254ms 第二次14ms")
    @GetMapping("/queryPPT")
    public Result queryPPT(@RequestParam("id") Long id)
    {
        if (id == null) return Result.fail("传入数据不能为null");
        try {
            HistoryData historyData = historyDataService.selectById(id);
            if (historyData == null) return Result.fail("传入数据错误");
            String status = historyData.getStatus();
            if (status.equals("4")) return Result.suc(1);
            if (!status.equals("3")) return Result.fail("接口调用逻辑错误");
            String pptSid = historyData.getPptSid();
            if (pptSid == null) return Result.fail("系统内部错误");
            ProgressResp progressResp = pptClient.getProgress(pptSid);
            if (progressResp.getCode() != 0) return Result.fail("ppt生成失败");
            int process = progressResp.getData().getProcess();
            if (process == 100)
            {
                historyDataService.updatePPT(id, progressResp.getData().getPptUrl(), "4");
                return Result.suc(1);
            }
            return Result.suc(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("查询ppt进度失败");
    }

    @ApiOperation(value="文档重命名 需传入字段主键id和重命名文档名 30ms")
    @PostMapping("/updateName")
    public Result updateName(@RequestBody HistoryDataDto historyDataDto)
    {
        if (historyDataDto == null) return Result.fail("传入数据不能为null");
        try{
            Long id = historyDataDto.getId();
            String name = historyDataDto.getName();
            if (id == null || name == null) return Result.fail("传入数据不能为null");
            historyDataService.updateName(id, name);
            return Result.suc("更新成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("重命名失败");
    }

    @ApiOperation(value="theme主题列表查询 传入createOutline的是key 也可以选择auto 随机主题 16ms")
    @GetMapping("/getThemeList")
    public Result getThemeList()
    {
        return Result.suc(ThemeUtil.getThemeList());
    }

    @ApiOperation(value="历史ppt文档生成数据 14ms")
    @GetMapping("/getHistoryData")
    public Result getHistoryData(@RequestHeader("userId") String userId)
    {
        if (userId == null) return Result.fail("系统内部错误");
        try {
            List<HistoryData> historyDataList = historyDataService.selectAllData(userId);
            for (HistoryData historyData : historyDataList)
            {
                historyData.setUserId(historyData.getId().toString());
                historyData.setOutline(null);
                historyData.setPptSid(null);
                historyData.setOutlineSid(null);
            }
            return Result.suc(historyDataList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("获取失败");
    }

    @ApiOperation(value="大纲查询 需传入字段主键id 在完成大纲生成后才能调用 15ms")
    @GetMapping("/getOutline")
    public Result getOutline(@RequestParam("id") Long id)
    {
        if (id == null) return Result.fail("传入数据不能为null");
        HistoryData historyData = historyDataService.selectById(id);
        if (historyData == null) return Result.fail("传入数据错误");
        return Result.suc(historyData.getOutline());
    }

    @ApiOperation(value="封面查询 需传入字段主键id 在完成ppt生成后才能调用 14ms")
    @GetMapping("/getCoverImgSrc")
    public Result getCoverImgSrc(@RequestParam("id") Long id)
    {
        if (id == null) return Result.fail("传入数据不能为null");
        HistoryData historyData = historyDataService.selectById(id);
        if (historyData == null) return Result.fail("传入数据错误");
        return Result.suc(historyData.getCoverImgSrc());
    }

    @ApiOperation(value="ppt查询 需传入字段主键id 在完成ppt生成后才能调用 11ms")
    @GetMapping("/getPPT")
    public Result getPPT(@RequestParam("id") Long id)
    {
        if (id == null) return Result.fail("传入数据不能为null");
        HistoryData historyData = historyDataService.selectById(id);
        if (historyData == null) return Result.fail("传入数据错误");
        return Result.suc(historyData.getPptUrl());
    }

    @ApiOperation(value="文件名查询 需传入字段主键id 在完成ppt生成后才能调用 13ms")
    @GetMapping("/getName")
    public Result getName(@RequestParam("id") Long id)
    {
        if (id == null) return Result.fail("传入数据不能为null");
        HistoryData historyData = historyDataService.selectById(id);
        if (historyData == null) return Result.fail("传入数据错误");
        return Result.suc(historyData.getName());
    }

    @ApiOperation(value="更新时间查询 需传入字段主键id 在完成大纲生成后才能调用 16ms")
    @GetMapping("/getTimeLastChange")
    public Result getTimeLastChange(@RequestParam("id") Long id)
    {
        if (id == null) return Result.fail("传入数据不能为null");
        HistoryData historyData = historyDataService.selectById(id);
        if (historyData == null) return Result.fail("传入数据错误");
        return Result.suc(historyData.getLastChangeTime());
    }

    @ApiOperation(value="字段详细信息查询 需传入字段主键id 在完成大纲生成后才能调用 14ms")
    @GetMapping("/getInfo")
    public Result getInfo(@RequestParam("id") Long id)
    {
        if (id == null) return Result.fail("传入数据不能为null");
        HistoryData historyData = historyDataService.selectById(id);
        if (historyData == null) return Result.fail("传入数据错误");
        historyData.setUserId(null);
        return Result.suc(historyData);
    }
}
