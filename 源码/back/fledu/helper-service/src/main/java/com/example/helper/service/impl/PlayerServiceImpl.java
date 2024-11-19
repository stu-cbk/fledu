package com.example.helper.service.impl;

import com.example.helper.clients.HelperClient;
import com.example.helper.entity.dto.PlayerDto;
import com.example.helper.entity.response.ResponseMsg;
import com.example.helper.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private HelperClient helperClient;

    public Boolean ifRegister(String appId,String playerName)
    {
        try{
            ResponseMsg<Boolean> form = helperClient.ifRegister(appId, playerName);
            System.out.println(form);
            if (form.getCode().equals(10000)){
                return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }
        }catch (Exception e){
            return Boolean.FALSE;
        }
    }

    public String register(PlayerDto player)
    {
        try{
            ResponseMsg<String> form = helperClient.register(player);
            System.out.println(form);
            if (form.getCode().equals(10000)){
                return form.getData();
            }else{
                return "";
            }
        }catch (Exception e){
            return "";
        }
    }

    public Boolean delete(PlayerDto player)
    {
        try{
            ResponseMsg<String> form = helperClient.delete(player);
            System.out.println(form);
            if (form.getCode().equals(10000)){
                return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }
        }catch (Exception e){
            return Boolean.FALSE;
        }
    }
}
