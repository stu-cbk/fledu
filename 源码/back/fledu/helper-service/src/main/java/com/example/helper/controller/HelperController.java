package com.example.helper.controller;

import com.example.helper.entity.dto.*;
import com.example.helper.entity.request.InteractiveRequest;
import com.example.helper.service.*;
import com.example.helper.utils.ModelWssUtil;
import com.example.helper.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api("聊天服务")
@RestController
public class HelperController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private InteractiveService interactiveService;

    @Autowired
    private HistoryChatService historyChatService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private PlayerInfoService playerInfoService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private HelperService helperService;

    @Autowired
    private NewChatService newChatService;

    @Autowired
    private AsyncTaskService asyncTaskService;

    private final String appId = "xxx";

    @ApiOperation(value="获取sparkDesk对话模型wss的url 无传参 15ms")
    @GetMapping("/getBaseWssURL")
    public Result getBaseWssURL()
    {
        ModelWssUtil modelWssUtil = new ModelWssUtil();
        String line = modelWssUtil.getWssURL();
        if (line.equals("mistake")) return Result.fail("系统内部错误");
        return Result.suc(line);
    }

    @ApiOperation(value="获取sparkDesk对话模型wss的sendText 只需要提供content也即是问题 21ms")
    @PostMapping("/getBaseSendText")
    public Result getBaseSendText(@RequestHeader("userId") String userId, @RequestBody TextDto textDto)
    {
        /*
         * 上下文无关
         */
        if (userId == null || userId.equals("")) return Result.fail("系统内部错误");
        if (textDto == null || textDto.getContent() == null) return Result.fail("传参为null");
        textDto.setRole("user");
        ModelWssUtil modelWssUtil = new ModelWssUtil();

        TextDto system = new TextDto();
        system.setRole("system");
        system.setContent("你的名字是茯苓教学助手，你需要解答学生的问题");

        List<TextDto> textDtoList = new ArrayList<>();

        textDtoList.add(system);
        textDtoList.add(textDto);

        String sendText = modelWssUtil.getSendText(textDtoList);
        return Result.suc(sendText);
    }

    @ApiOperation(value="实时英文对话 获取sparkDesk对话模型wss的sendText 只需要提供content也即是问题 12ms")
    @PostMapping("/getEnglishSendText")
    public Result getEnglishSendText(@RequestHeader("userId") String userId, @RequestBody TextDto textDto)
    {
        /*
         * 上下文无关
         */
        if (userId == null || userId.equals("")) return Result.fail("系统内部错误");
        if (textDto == null || textDto.getContent() == null) return Result.fail("传参为null");
        textDto.setRole("user");
        ModelWssUtil modelWssUtil = new ModelWssUtil();

        TextDto system = new TextDto();
        system.setRole("system");
        system.setContent("Your name is Fu Ling and you should answer the user's questions in English");

        List<TextDto> textDtoList = new ArrayList<>();

        textDtoList.add(system);
        textDtoList.add(textDto);

        String sendText = modelWssUtil.getSendText(textDtoList);
        return Result.suc(sendText);
    }

    @ApiOperation(value="创造一个新的对话 返回对话的id 16ms")
    @GetMapping("/createChat")
    public Result createChat(@RequestHeader("userId") String userId)
    {
        if (userId == null || userId.equals("")) return Result.fail("系统内部错误");
        NewChatInfo newChatInfo = new NewChatInfo(userId);
        int s = newChatService.insert(newChatInfo);
        if (s == 1) return Result.suc(newChatInfo.getId().toString());
        else return Result.fail("新增对话失败");
    }

    @ApiOperation(value="删除已有的对话 需要提供对话id 33ms")
    @GetMapping("/deleteChat")
    public Result deleteChat(@RequestHeader("userId") String userId, @RequestParam("id") String cid)
    {
        if (userId == null || userId.equals("")) return Result.fail("系统内部错误");
        if (cid == null || cid.equals("")) return Result.fail("传参为null");
        try{
            Long id = Long.valueOf(cid);
            newChatService.delete(id);
            historyChatService.deleteHistoryData(userId, cid);
            return Result.suc("删除对话成功");
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("调用接口失败");
        }
    }

    @ApiOperation(value="查询主对话外所有对话id string格式放在userId里面了 18ms")
    @GetMapping("/queryChat")
    public Result queryChat(@RequestHeader("userId") String userId)
    {
        if (userId == null || userId.equals("")) return Result.fail("系统内部错误");
        List<NewChatInfo> newChatInfoList = newChatService.selectByUserId(userId);
        for (NewChatInfo newChatInfo : newChatInfoList)
            newChatInfo.setUserId(newChatInfo.getId().toString());
        return Result.suc(newChatInfoList);
    }

    @ApiOperation(value="存储历史问答数据 需传入q&a和cid 这里的cid指的是对话id或角色id 如果不传对话id默认使用主对话 22ms")
    @PostMapping("/saveChat")
    public Result saveChat(@RequestHeader("userId") String userId, @ApiParam("传入问答数据") @RequestBody ChatDto chatDto)
    {
        if (userId == null || userId.equals("")) return Result.fail("系统内部错误");
        if (chatDto == null) {return Result.fail("传入数据为null");}
        try{
            if (chatDto.getCid() == null) chatDto.setCid(userId);
            HistoryChatInfo historyChatInfo = new HistoryChatInfo(userId, chatDto);
            int s = historyChatService.insert(historyChatInfo);
            if (s == 1) return Result.suc("存储成功");
            else return Result.fail("存储失败");
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("调用接口失败");
        }
    }

    @ApiOperation(value="获取历史问答数据 如果是模型对话 需传入对话id 否则默认主对话 如果是角色对话 需传入角色id 15ms")
    @GetMapping("/getHistoryData")
    public Result getHistoryData(@RequestHeader("userId") String userId, @RequestParam("id") String id)
    {
        if (userId == null || userId.equals("")) return Result.fail("系统内部错误");
        try{
            if (id == null || id.equals("")) id = userId;
            List<HistoryChatInfo> historyChatInfoList = historyChatService.selectHistoryData(userId, id);
            if (historyChatInfoList != null)
            {
                for (HistoryChatInfo historyChatInfo : historyChatInfoList)
                {
                    historyChatInfo.setUserId(null);
                    historyChatInfo.setCid(null);
                }

            }
            return Result.suc(historyChatInfoList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.fail("调用接口失败");
        }
    }

    @ApiOperation(value="清空历史问答数据 如果是模型对话 需传入对话id 否则默认主对话 如果是角色对话 需传入角色id 15ms")
    @GetMapping("/clearHistoryData")
    public Result clearHistoryData(@RequestHeader("userId") String userId, @RequestParam("id") String cid)
    {
        if (userId == null || userId.equals("")) return Result.fail("系统内部错误");
        try{
            if (cid == null || cid.equals("")) cid = userId;
            int s = historyChatService.deleteHistoryData(userId, cid);
            return Result.suc(s);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.fail("调用接口失败");
        }
    }

    @ApiOperation(value = "初始化角色模拟功能 22ms")
    @GetMapping("/info")
    public Result info(@RequestHeader("userId") String userId)
    {
        if (userId == null || userId.equals("")) return Result.fail("系统内部错误");
        try{
            Long id = Long.valueOf(userId);
            PlayerInfo playerInfo = playerInfoService.select(id);
            if (playerInfo == null)
            {
                PlayerDto playerDto = new PlayerDto();
                playerDto.setPlayerName(userId);
                playerDto.setAppId(appId);
                String playerId = playerService.register(playerDto);
                if (playerId.equals("")) return Result.fail("初始化失败");
                playerInfo = new PlayerInfo();
                playerInfo.setId(id);
                playerInfo.setPlayerId(playerId);
                playerInfoService.insert(playerInfo);
            }
            return Result.suc("初始化成功");
        }catch (Exception e) {
            e.printStackTrace();
            return Result.fail("调用接口失败");
        }
    }

    @ApiOperation(value="创建对话人格 传入角色名字和业务场景 注意角色名不能重复 返回角色id 488ms")
    @PostMapping("/createCharacter")
    public Result createCharacter(@RequestHeader("userId") String userId,@RequestBody CharacterDto characterDto)
    {
        if (userId == null || userId.equals("")) return Result.fail("系统内部错误");
        if (characterDto == null || characterDto.getName() == null) return Result.fail("传参为null");
        try{
            Long id = Long.valueOf(userId);
            PlayerInfo playerInfo = playerInfoService.select(id);
            HelperDto helperDto = new HelperDto();
            helperDto.setAppId(appId);
            helperDto.setPlayerId(playerInfo.getPlayerId());
            helperDto.setAgentName(characterDto.getName());
            helperDto.setMission(characterDto.getMission());
            String cid = helperService.createHelper(helperDto);
            if (cid.equals("")) return Result.fail("创建人格失败");
            CharacterInfo characterInfo = new CharacterInfo(userId, characterDto.getName(),
                    characterDto.getMission(), cid);
            int s = characterService.insert(characterInfo);
            if (s == 1) return Result.suc(characterInfo.getId().toString());
            else return Result.fail("创建人格失败");
        }catch (Exception e) {
            e.printStackTrace();
            return Result.fail("调用接口失败");
        }
    }

    @ApiOperation(value="删除对话人格 传入角色id 34ms")
    @GetMapping("/deleteCharacter")
    public Result deleteCharacter(@RequestHeader("userId") String userId, @RequestParam("id") String cid)
    {
        if (userId == null || userId.equals("")) return Result.fail("系统内部错误");
        if (cid == null || cid.equals("")) return Result.fail("传参为null");
        try{
            Long id = Long.valueOf(cid);
            CharacterInfo characterInfo = characterService.selectOne(id);
            // 异步接口
            asyncTaskService.deleteCharacter(appId, characterInfo.getCid(), characterInfo.getName());

            characterService.delete(id);
            historyChatService.deleteHistoryData(userId, cid);
            return Result.suc("删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            return Result.fail("调用接口失败");
        }
    }

    @ApiOperation(value="查看所有对话人格id string格式放在cid里面了 20ms")
    @GetMapping("/queryAllId")
    public Result queryAllId(@RequestHeader("userId") String userId)
    {
        if (userId == null || userId.equals("")) return Result.fail("系统内部错误");
        try{
            List<CharacterInfo> characterInfoList = characterService.selectAll(userId);
            for (CharacterInfo characterInfo : characterInfoList)
            {
                characterInfo.setUserId(null);
                characterInfo.setMission(null);
                characterInfo.setCid(characterInfo.getId().toString());
            }
            return Result.suc(characterInfoList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.fail("调用接口失败");
        }
    }

    @ApiOperation(value="获取自定义人格的wssURL和sendText 需要传入question和人格id 90ms")
    @PostMapping("/customizeChat")
    public Result customizeChat(@RequestHeader("userId") String userId, @RequestBody ChatDto chatDto)
    {
        /*
         * 上下文无关
         */

        if (userId == null || userId.equals("")) return Result.fail("系统内部错误");
        if (chatDto == null || chatDto.getCid() == null || chatDto.getQuestion() == null)
            return Result.fail("传入的数据不完整");
        try {
            Long id = Long.valueOf(userId);
            PlayerInfo playerInfo = playerInfoService.select(id);
            CharacterInfo characterInfo = characterService.selectOne(Long.valueOf(chatDto.getCid()));
            String chatId = UUID.randomUUID().toString().substring(0, 10);
            ChatInfo chat = new ChatInfo(userId,chatDto.getCid(),chatId);
            ChatInfo lastChat = chatService.select(chat);
            String lastChatId = chatId;
            if (lastChat == null) {
                chatService.insert(chat);
            }else{
                // lastChatId = lastChat.getLatestChatId();
                chatService.update(chat);
            }

            String text = chatDto.getQuestion();
            List<InteractiveRequest.Text> textList = new ArrayList<>();
            if (!text.equals("")){
                InteractiveRequest.Text text1 = new InteractiveRequest.Text();
                text1.setContent(text);
                text1.setRole("user");
                textList.add(text1);
            }

            Map<String, String> map = interactiveService.chat(playerInfo.getPlayerId(), characterInfo.getCid(),chatId,lastChatId,textList);
            return Result.suc(map);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.fail("调用接口失败");
        }
    }
}
