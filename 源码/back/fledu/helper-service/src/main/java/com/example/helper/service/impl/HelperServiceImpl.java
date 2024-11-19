package com.example.helper.service.impl;

import com.example.helper.clients.HelperClient;
import com.example.helper.entity.dto.HelperDto;
import com.example.helper.entity.response.ResponseMsg;
import com.example.helper.service.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelperServiceImpl implements HelperService {
    @Autowired
    private HelperClient helperClient;

    public String createHelper(HelperDto helper)
    {
        try{
            ResponseMsg<String> form = helperClient.createHelper(helper);
            System.out.println(form);
            if (form.getCode().equals(10000)){
                return form.getData();
            }else{
                return "";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public Boolean editHelper(HelperDto helper)
    {
        try{
            ResponseMsg<String> form = helperClient.editHelper(helper);
            System.out.println(form);
            if (form.getCode().equals(10000)){
                return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }
        }catch (Exception e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public Boolean getHelper(String appId, String agentId, String agentName)
    {
        try{
            ResponseMsg<HelperDto> form = helperClient.getHelper(appId, agentId, agentName);
            System.out.println(form);
            if (form.getCode().equals(10000)){
                return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }
        }catch (Exception e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public Boolean deleteHelper(String appId, String agentId, String agentName)
    {
        try{
            ResponseMsg<Boolean> form = helperClient.deleteHelper(appId, agentId, agentName);
            System.out.println(form);
            if (form.getCode().equals(10000)){
                return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }
        }catch (Exception e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

}
