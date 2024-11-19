package com.example.video.interceptor;

import com.example.video.utils.AuthUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.security.SignatureException;

@Component
public class CustomFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template){
        String url = template.url();
        System.out.println(url);
        AuthUtil authUtil = null;
        try {
            if (url.contains("dts_create")) authUtil = new AuthUtil("/v1/private/dts_create");
            else if (url.contains("dts_query")) authUtil = new AuthUtil("/v1/private/dts_query");
            String line = String.format("?host=%s&date=%s&authorization=%s",
                    authUtil.getHost(), authUtil.getTs(), authUtil.getAuthorization());
            template.uri(line,true);
            System.out.println(template.url());
        }catch (SignatureException e) {
            e.printStackTrace();
        }
    }
}
