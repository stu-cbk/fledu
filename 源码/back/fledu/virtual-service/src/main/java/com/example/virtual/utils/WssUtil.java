package com.example.virtual.utils;

import lombok.Data;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.SignatureException;
import java.util.concurrent.CountDownLatch;

@Data
public class WssUtil {
    String URL;

    /*
    public static void main(String[] args){
        try {
            //WssUtil wssUtil = new WssUtil("iat-api.xfyun.cn","/v2/iat");
            WssUtil wssUtil = new WssUtil("tts-api.xfyun.cn","/v2/tts");
            wssUtil.CreateWss();
        } catch (SignatureException e) {
            e.printStackTrace();
        }

    }*/

    public WssUtil(){}
    public WssUtil(String host,String requestLine) throws SignatureException
    {
        AuthUtil authUtil = new AuthUtil(host, requestLine);
        this.URL = "wss://" + host + requestLine + "?authorization=" + authUtil.getAuthorization()
                + "&date=" + authUtil.getTs() + "&host=" + host;
    }

    /*
    public void CreateWss()
    {
        // wss
        Request request = new Request.Builder().url(this.URL).build();
        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        final StringBuffer buffer = new StringBuffer();
        CountDownLatch latch = new CountDownLatch(1);

        WebSocket webSocket = okHttpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                System.out.println("WebSocket Opened");
            }
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                System.out.println("WebSocket Closed: " + reason);
                okHttpClient.connectionPool().evictAll();
                latch.countDown();
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                System.out.println("WebSocket Failure: " + t.getMessage());
                okHttpClient.connectionPool().evictAll();
                latch.countDown();
            }
        });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(webSocket);
    }*/
}
