package com.example.helper.interceptor;

import com.example.helper.utils.ApiAuthAlgorithm;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String appId = "xxx";
        String secret = "xxx";
        Long timestamp = System.currentTimeMillis();

        String signature = ApiAuthAlgorithm.getSignature(appId, secret, timestamp);

        // 添加自定义的请求头
        template.header("appId", appId);
        template.header("timestamp", String.valueOf(timestamp));
        template.header("signature", signature);
    }
}
