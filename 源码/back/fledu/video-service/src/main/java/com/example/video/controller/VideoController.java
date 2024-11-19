package com.example.video.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.video.clients.Mp3Client;
import com.example.video.entity.dto.HistoryData;
import com.example.video.entity.dto.HistoryDto;
import com.example.video.entity.dto.TextDto;
import com.example.video.entity.request.CreateReq;
import com.example.video.entity.response.CreateResp;
import com.example.video.service.HistoryDataService;
import com.example.video.service.AsyncTaskService;
import com.example.video.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Api("音视频生成服务")
@RestController
public class VideoController {

    @Autowired
    private HistoryDataService historyDataService;
    @Autowired
    private Mp3Client mp3Client;
    @Autowired
    private AsyncTaskService asyncTaskService;

    private final String appId = "07bd5f36";
    private final Path fileBasePath = Paths.get("D:/flschool/data/").toAbsolutePath().normalize();
    //private final Path fileBasePath = Paths.get("/home/lzy/data/").toAbsolutePath().normalize();

    @ApiOperation(value="上传ppt 生成图片(异步) 返回fileId 332ms")
    @PostMapping(value="/createPngTask")
    public Result createPngTask(@RequestParam("file") MultipartFile file)  {
        if (file == null) return Result.fail("输入数据为null");
        try {
            HistoryData historyData = new HistoryData("-1");
            int s = historyDataService.insert(historyData);
            if (s == 1) {
                String fileId = historyData.getId().toString();
                // 异步调用
                asyncTaskService.processPPT(fileId, file);
                return Result.suc(fileId);
            } else return Result.fail("插入数据失败");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("插入数据失败");
    }

    @ApiOperation(value="上传fileId 返回图片上传任务进度 0表示未上传 1表示已上传 62ms")
    @GetMapping("/queryPngProcess")
    public Result queryPngProcess(@RequestParam("fileId") String fileId)
    {
        if (fileId == null) return Result.fail("输入数据为null");
        try {
            HistoryData historyData = historyDataService.selectById(Long.valueOf(fileId));
            if (historyData == null) return Result.fail("传参错误");
            if (historyData.getStatus().equals("-1")) return Result.suc(0);
            if (historyData.getStatus().equals("0")) return Result.suc(1);
            return Result.fail("接口调用逻辑不当");
        }catch (Exception e){
            return Result.fail("查询失败");
        }
    }

    @ApiOperation(value="上传fileId和文本列表 建立音频生成任务 返回fileId 1659ms")
    @PostMapping("/createMp3Task")
    public Result createMp3Task(@RequestBody TextDto textDto)
    {
        if (textDto == null) return Result.fail("输入数据为null");
        Long id = Long.valueOf(textDto.getId());
        HistoryData historyData0 = historyDataService.selectById(id);
        if (historyData0 == null) return Result.fail("传参错误");
        if (!historyData0.getStatus().equals("0")) return Result.fail("接口调用逻辑错误");

        List<String> taskIdList = new ArrayList<>();
        List<Integer> taskStatus = new ArrayList<>();
        for (String text : textDto.getTextList())
        {
            String line = CryptUtil.base64Encode(text);
            CreateReq createReq = new CreateReq(line, appId, "x4_yeting");
            CreateResp createResp = mp3Client.create(createReq);
            int code = createResp.getHeader().getCode();
            if (code == 0) {
                taskIdList.add(createResp.getHeader().getTask_id());
                taskStatus.add(0);
            } else return Result.fail(createResp.getHeader().getMessage());
        }
        String taskIdListJson = JSON.toJSONString(taskIdList);
        String taskStatusJson = JSON.toJSONString(taskStatus);
        HistoryData historyData = new HistoryData("1");
        historyData.setTaskIdList(taskIdListJson);
        historyData.setTaskStatus(taskStatusJson);
        historyData.setId(Long.valueOf(textDto.getId()));
        historyData.setTotalTask(taskIdList.size());
        int s = historyDataService.update(historyData);
        if (s == 1) return Result.suc(textDto.getId());
        else return Result.fail("上传文本失败");
    }

    @ApiOperation(value="上传fileId 返回音频生成任务进度 846ms")
    @GetMapping("/queryMp3Process")
    public Result queryMp3Process(@RequestParam("fileId") String fileId)
    {
        if (fileId == null) return Result.fail("输入数据为null");
        try {
            HistoryData historyData = historyDataService.selectById(Long.valueOf(fileId));
            if (historyData == null) return Result.fail("传参错误");
            if (!historyData.getStatus().equals("1") && !historyData.getStatus().equals("2")) return Result.fail("接口调用逻辑不当");
            if (historyData.getStatus().equals("2")) return Result.suc(100);
            JSONArray taskIdList = JSONArray.parseArray(historyData.getTaskIdList());
            JSONArray taskStatus = JSONArray.parseArray(historyData.getTaskStatus());

            TextToMp3 textToMp3 = new TextToMp3(fileId, taskIdList, taskStatus, mp3Client);
            int process = textToMp3.queryAudio(appId);
            System.out.println("进度：" + process);
            historyData.setTaskStatus(textToMp3.getStatus().toJSONString());
            if (process == 100) historyData.setStatus("2");
            else historyData.setStatus("1");
            historyDataService.update(historyData);
            return Result.suc(process);
        }catch (Exception e){
            return Result.fail("查询失败");
        }
    }

    @ApiOperation(value="上传fileId 生成视频 只有在完成音频生成后才能调用 27ms")
    @GetMapping("/createMp4Task")
    public Result createMp4Task(@RequestParam("fileId") String fileId)
    {
        if (fileId == null) return Result.fail("输入数据为null");
        try {
            Long id = Long.valueOf(fileId);
            HistoryData historyData = historyDataService.selectById(id);
            if (historyData == null) return Result.fail("传参错误");
            if (historyData.getStatus().equals("3") || historyData.getStatus().equals("4"))
                return Result.suc("已创建任务");
            if (!historyData.getStatus().equals("2")) return Result.fail("不能调用此接口");

            // 异步调用
            asyncTaskService.createVideoAsync(fileId, historyData.getTotalTask());

            historyDataService.updateStatus(id, "3"); // 更新状态为"任务已创建"
            return Result.suc("成功创建任务");
        }catch (Exception e){
            return Result.fail("生成失败");
        }
    }

    @ApiOperation(value="上传fileId 返回视频生成进度 0 表示尚未生成 1 表示成功生成 15ms")
    @GetMapping("/queryMp4Process")
    public Result queryMp4Process(@RequestParam("fileId") String fileId)
    {
        if (fileId == null) return Result.fail("输入数据为null");
        try {
            HistoryData historyData = historyDataService.selectById(Long.valueOf(fileId));
            if (historyData == null) return Result.fail("传参错误");
            if (historyData.getStatus().equals("3")) return Result.suc(0);
            if (historyData.getStatus().equals("4")) return Result.suc(1);
            return Result.fail("接口调用逻辑不当");
        }catch (Exception e){
            return Result.fail("查询失败");
        }
    }

    @ApiOperation(value="上传fileId 得到视频 32ms")
    @GetMapping("/mp4/{fileId}")
    public ResponseEntity<Resource> downloadVideoFile(@PathVariable String fileId)
    {
        try {
            // 校验文件名以防路径遍历攻击
            if (fileId.contains("..")) {
                throw new RuntimeException("不允许的文件名: " + fileId);
            }

            Long id = Long.valueOf(fileId);
            HistoryData historyData = historyDataService.selectById(id);
            if (historyData == null) throw new RuntimeException("传参错误");
            if (!historyData.getStatus().equals("4")) throw new RuntimeException("接口调用逻辑不对");

            Path filePath = fileBasePath.resolve("mp4").resolve(fileId).resolve("output.mp4").normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        }catch (Exception e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @ApiOperation(value="上传fileId 得到pptx 28ms")
    @GetMapping("/pptx/{fileId}")
    public ResponseEntity<Resource> downloadPptFile(@PathVariable String fileId) {
        try {
            // 校验文件名以防路径遍历攻击
            if (fileId.contains("..")) {
                throw new RuntimeException("不允许的文件名: " + fileId);
            }

            Long id = Long.valueOf(fileId);
            HistoryData historyData = historyDataService.selectById(id);
            if (historyData == null) throw new RuntimeException("传参错误");
            if (historyData.getStatus().equals("-1")) throw new RuntimeException("接口调用逻辑不对");

            Path filePath = fileBasePath.resolve("pptx").resolve(fileId + ".pptx").normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String encodedFilename = URLEncoder.encode(resource.getFilename(), "UTF-8").replaceAll("\\+", "%20");
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.presentation")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @ApiOperation(value="上传fileId 获取状态信息 21ms")
    @GetMapping("/getStatus")
    public Result getStatus(@RequestParam("fileId") String fileId)
    {
        HistoryData historyData = historyDataService.selectById(Long.valueOf(fileId));
        return Result.suc(historyData.getStatus());
    }

    @ApiOperation(value="获取历史数据 status0表示完成ppt以及图像上传 status1表示完成音频任务创建 " +
            "status2表示完成音频生成 status3表示完成视频任务创建 status4表示完成视频生成 19ms")
    @GetMapping("/getHistoryData")
    public Result getHistoryData()
    {
        List<HistoryData> historyDataList = historyDataService.selectAll();
        List<HistoryDto> ids = new ArrayList<>();
        for (HistoryData historyData: historyDataList)
        {
            HistoryDto historyDto = new HistoryDto();
            historyDto.setId(historyData.getId().toString());
            historyDto.setStatus(historyData.getStatus());
            ids.add(historyDto);
        }
        return Result.suc(ids);
    }
}
