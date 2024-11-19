package com.example.helper.clients;

import com.example.helper.entity.dto.HelperDto;
import com.example.helper.entity.dto.InteractiveDto;
import com.example.helper.entity.dto.PlayerDto;
import com.example.helper.entity.response.ResponseMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "helper-service", url = "https://ai-character.xfyun.cn/api/open")
public interface HelperClient {
    @GetMapping("/player/if-register")
    ResponseMsg<Boolean> ifRegister(@RequestParam("appId") String appId, @RequestParam("playerName") String playerName);
    @PostMapping("/player/register")
    ResponseMsg<String> register(@RequestBody PlayerDto player);
    @PostMapping("/player/modify")
    ResponseMsg<String> modify(@RequestBody PlayerDto player);
    @PostMapping("/player/delete")
    ResponseMsg<String> delete(@RequestBody PlayerDto player);
    @PostMapping("/agent/edit-character")
    ResponseMsg<String> createHelper(@RequestBody HelperDto helper);
    @PostMapping("/agent/edit-character")
    ResponseMsg<String> editHelper(@RequestBody HelperDto helper);
    @GetMapping("/agent/get-character")
    ResponseMsg<HelperDto> getHelper(@RequestParam("appId") String appId, @RequestParam("agentId") String agentId, @RequestParam("agentName") String agentName);
    @DeleteMapping("/agent/delete-character")
    ResponseMsg<Boolean> deleteHelper(@RequestParam("appId") String appId, @RequestParam("agentId") String agentId, @RequestParam("agentName") String agentName);
    @PostMapping("/interactive/generate")
    ResponseMsg<String> addMemory(@RequestBody InteractiveDto memory);
}
