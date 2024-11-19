package com.example.doc.controller;

import com.alibaba.fastjson.JSON;
import com.example.doc.clients.DocClient;
import com.example.doc.entity.doc.*;
import com.example.doc.entity.dto.OptionDto;
import com.example.doc.entity.dto.QuestionDto;
import com.example.doc.entity.response.InfoResp;
import com.example.doc.entity.response.UploadResp;
import com.example.doc.service.*;
import com.example.doc.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Api("心理测试服务")
@RestController
public class DocController {
    @Autowired
    private AnxietyService anxietyService;
    @Autowired
    private AloneService aloneService;
    @Autowired
    private DepressionService depressionService;
    @Autowired
    private DelayService delayService;
    @Autowired
    private MatureService matureService;
    @Autowired
    private SocialAvoidanceService socialAvoidanceService;

    @Autowired
    private HistoryDataService historyDataService;
    @Autowired
    private HistoryChatService historyChatService;

    @Autowired
    private DocClient docClient;

    @ApiOperation(value="上传type 得到测试文档列表 (type 0 焦虑 | 1 孤独 | 2 拖延 | 3 抑郁 | 4 成熟 | 5 社恐) 选项的个数不一定是4个 630ms")
    @GetMapping("/getQuestionAndOptions")
    public Result getQuestionAndOptions(@RequestParam("type") String type)
    {
        try {
            if (type.equals("0"))
            {
                List<Anxiety> anxieties = anxietyService.selectAll();
                return Result.suc(anxieties);
            }else if (type.equals("1"))
            {
                List<Alone> aloneList = aloneService.selectAll();
                return Result.suc(aloneList);
            }else if (type.equals("2"))
            {
                List<Delay> delays = delayService.selectAll();
                return Result.suc(delays);
            }else if (type.equals("3"))
            {
                List<Depression> depressions = depressionService.selectAll();
                return Result.suc(depressions);
            }else if (type.equals("4"))
            {
                List<Mature> matures = matureService.selectAll();
                return Result.suc(matures);
            }else if (type.equals("5"))
            {
                List<SocialAvoidance> socialAvoidanceList = socialAvoidanceService.selectAll();
                return Result.suc(socialAvoidanceList);
            }else
            {
                return Result.fail("传入的type有误");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return Result.fail("获取失败");
    }

    @ApiOperation(value="上传选项列表[0,1...]和文档类型(String)具体说明见getQuestionAndOptions 返回fileId 1201ms")
    @PostMapping("/uploadOptions")
    public Result uploadOptions(@RequestHeader("userId") String userId,
                                @RequestBody OptionDto optionDto){
        try {
            String type = optionDto.getType();
            String text = null;
            String fileName = null;
            Integer score = null;
            List<Integer> options = optionDto.getOptions();
            if (type.equals("0"))
            {
                List<Anxiety> anxieties = anxietyService.selectAll();
                if (anxieties.size() == options.size())
                {
                    text = TextUtils.getAnxText(anxieties, options);
                    score = CalcScore.getAnxScore(options);
                    fileName = "anxietyTest.txt";
                }
            }else if (type.equals("1"))
            {
                List<Alone> aloneList = aloneService.selectAll();
                if (aloneList.size() == options.size())
                {
                    text = TextUtils.getAloneText(aloneList, options);
                    score = CalcScore.getAloneScore(options);
                    fileName = "aloneTest.txt";
                }
            }else if (type.equals("2"))
            {
                List<Delay> delays = delayService.selectAll();
                if (delays.size() == options.size())
                {
                    text = TextUtils.getDelayText(delays, options);
                    score = CalcScore.getDelayScore(options);
                    fileName = "delayTest.txt";
                }
            }else if (type.equals("3"))
            {
                List<Depression> depressions = depressionService.selectAll();
                if (depressions.size() == options.size())
                {
                    text = TextUtils.getDepText(depressions, options);
                    score = CalcScore.getDepScore(options);
                    fileName = "depressionTest.txt";
                }
            }else if (type.equals("4"))
            {
                List<Mature> matures = matureService.selectAll();
                if (matures.size() == options.size())
                {
                    text = TextUtils.getMatureText(matures, options);
                    score = CalcScore.getMatureScore(options);
                    fileName = "matureTest.txt";
                }
            }else if (type.equals("5"))
            {
                List<SocialAvoidance> socialAvoidanceList = socialAvoidanceService.selectAll();
                if (socialAvoidanceList.size() == options.size())
                {
                    text = TextUtils.getSocialText(socialAvoidanceList, options);
                    score = CalcScore.getSocialScore(options);
                    fileName = "socialAvoidanceTest.txt";
                }
            }else
            {
                return Result.fail("传入的type有误");
            }

            if (text != null)
            {
                InputStream inputStream = new ByteArrayInputStream(text.getBytes());
                MultipartFile file = new MockMultipartFile(fileName, fileName, "text/plain", inputStream);

                UploadResp r = docClient.upload(file, "wiki");
                String fid = r.getData().getFileId();
                String answer = JSON.toJSONString(options);
                HistoryData historyData = new HistoryData(fid, userId, type, answer, score);
                int s = historyDataService.insert(historyData);
                if (s == 1) return Result.suc(fid);
                else return Result.fail("上传心理测试文档失败");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return Result.fail("上传心理测试文档失败");
    }

    @ApiOperation(value="查询文档进度 上传fileId 如果返回值为vectored表示成功上传并解析文档 可以进行文档问答 207ms")
    @GetMapping("/queryUploadProcess")
    public Result queryUploadProcess(@RequestParam("fileId") String fileId)
    {
        try{
            InfoResp ir = docClient.info(fileId);
            String status = ir.getData().getFileStatus();
            return Result.suc(status);
        }catch (Exception e){
            System.out.println(e);
        }
        return Result.fail("查询心理测试文档上传进度失败");
    }

    @ApiOperation(value="获取wssURL 建立wss连接 逻辑上和uploadOptions可同时调用 13ms")
    @GetMapping("/getWssURL")
    public Result getWssURL()
    {
        try {
            String wssURL = WssUtils.createURL();
            return Result.suc(wssURL);
        }catch (Exception e){
            System.out.println(e);
        }
        return Result.fail("调用接口失败");
    }

    @ApiOperation(value="传入fileId和question 获取sendText 逻辑上必须在获得fileId后才能调用 35ms")
    @PostMapping("/getSendText")
    public Result getSendText(@RequestBody QuestionDto questionDto)
    {
        try {
            String question = questionDto.getQuestion();
            String fileId = questionDto.getFileId();
            if (question == null || fileId == null || question == "") return Result.fail("输入参数不能为空");
            String sendText = WssUtils.createSendText(question, fileId);
            return Result.suc(sendText);
        }catch (Exception e){
            System.out.println(e);
        }
        return Result.fail("调用接口失败");
    }

    @ApiOperation(value="传入fileId、question和answer 存储一次文档对话 33ms")
    @PostMapping("/saveChat")
    public Result saveChat(@RequestBody QuestionDto questionDto)
    {
        try {
            String question = questionDto.getQuestion();
            String answer = questionDto.getAnswer();
            String fileId = questionDto.getFileId();
            if (question == null || fileId == null || answer == null) return Result.fail("输入参数不能为空");
            HistoryChat historyChat = new HistoryChat(fileId, question, answer);
            int s = historyChatService.insert(historyChat);
            if (s == 1) return Result.suc("存储文档对话成功");
            else return Result.fail("存储文档失败");
        }catch (Exception e){
            System.out.println(e);
        }
        return Result.fail("调用接口失败");
    }

    @ApiOperation(value="传入fileId 获取总结(String) 逻辑上必须在获得fileId后才能调用 21ms")
    @GetMapping("/getConclusion")
    public Result getConclusion(@RequestParam("fileId") String fileId)
    {
        try {
            if (fileId == null) return Result.fail("输入参数不能为空");
            HistoryData historyData = historyDataService.selectHistoryData(fileId);
            if (historyData == null) return Result.fail("fileId出错");
            String type = historyData.getType();
            Integer score = historyData.getScore();
            String conclusion = ConclusionUtils.getConclusion(type, score);
            return Result.suc(conclusion);
        }catch (Exception e){
            System.out.println(e);
        }
        return Result.fail("调用接口失败");
    }

    @ApiOperation(value="获取该用户上传的所有FileId 18ms")
    @GetMapping("/getFileIds")
    public Result getFileIds(@RequestHeader("userId") String userId)
    {
        try{
            List<HistoryData> historyData = historyDataService.selectAllHistoryData(userId);
            return Result.suc(historyData);
        }catch (Exception e){
            System.out.println(e);
        }
        return Result.fail("调用接口失败");
    }

    @ApiOperation(value="获取该FileId下的所有聊天记录 21ms")
    @GetMapping("/getHistoryChat")
    public Result getHistoryChat(@RequestParam("fileId") String fileId)
    {
        try{
            List<HistoryChat> historyChats = historyChatService.selectHistoryChat(fileId);
            return Result.suc(historyChats);
        }catch (Exception e){
            System.out.println(e);
        }
        return Result.fail("调用接口失败");
    }

    @ApiOperation(value="获取该FileId的类型信息 15ms")
    @GetMapping("/getType")
    public Result getType(@RequestParam("fileId") String fileId)
    {
        try{
            if (fileId == null) return Result.fail("fileId为null");
            HistoryData historyData = historyDataService.selectHistoryData(fileId);
            if (historyData == null) return Result.fail("fileId错误");
            return Result.suc(historyData.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("调用接口失败");
    }

    @ApiOperation(value="获取该FileId的日期信息 25ms")
    @GetMapping("/getTime")
    public Result getTime(@RequestParam("fileId") String fileId)
    {
        try{
            if (fileId == null) return Result.fail("fileId为null");
            HistoryData historyData = historyDataService.selectHistoryData(fileId);
            if (historyData == null) return Result.fail("fileId错误");
            return Result.suc(historyData.getLastChangeTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("调用接口失败");
    }

    @ApiOperation(value="获取该FileId的测试填写情况信息 18ms")
    @GetMapping("/getAnswer")
    public Result getAnswer(@RequestParam("fileId") String fileId)
    {
        try{
            if (fileId == null) return Result.fail("fileId为null");
            HistoryData historyData = historyDataService.selectHistoryData(fileId);
            if (historyData == null) return Result.fail("fileId错误");
            return Result.suc(historyData.getAnswer());
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("调用接口失败");
    }


    @ApiOperation(value="删除该FileId下的所有聊天记录 15ms")
    @GetMapping("/deleteHistoryChat")
    public Result deleteChat(@RequestParam("fileId") String fileId)
    {
        try {
            historyChatService.delete(fileId);
            return Result.suc("成功清空历史对话");
        }catch (Exception e){
            System.out.println(e);
        }
        return Result.fail("调用接口失败");
    }

    @ApiOperation(value="删除该FileId 25ms")
    @GetMapping("/deleteFile")
    public Result deleteFile(@RequestParam("fileId") String fileId)
    {
        try {
            historyDataService.delete(fileId);
            return Result.suc("成功删除该文档对话");
        }catch (Exception e){
            System.out.println(e);
        }
        return Result.fail("调用接口失败");
    }

}
