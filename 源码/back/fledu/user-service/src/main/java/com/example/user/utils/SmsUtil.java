package com.example.user.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;

import java.util.Random;

public class SmsUtil {
    private static final String ACCESS_KEY_ID = "xxx";
    private static final String ACCESS_KEY_SECRET = "xxx";
    private static final String ENDPOINT = "xxx";
    private static final String TemplateCode = "xxx";
    private static final String SignName = "xxx";
    

    public static String sendSms(String phoneNumbers)
    {
        String code = generateVerificationCode();
        Config config = new Config()
                .setAccessKeyId(ACCESS_KEY_ID)
                .setAccessKeySecret(ACCESS_KEY_SECRET)
                .setEndpoint(ENDPOINT);
        try {
            Client client = new Client(config);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phoneNumbers)
                    .setSignName(SignName)
                    .setTemplateCode(TemplateCode)
                    .setTemplateParam(String.format("{\"code\":\"%s\"}", code));
            SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1000000";
    }

    public static void main(String[] args_){
        String code = sendSms("xxx");
        System.out.println(code);
    }

    public static String generateVerificationCode()
    {
        Random random = new Random();
        int code = random.nextInt(1000000);
        return String.format("%06d", code);
    }
}
