package com.example.video.utils;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Http工具 处理结果类
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * Http连接超时时间
     */
    private static final int CONNECT_TIMEOUT = 10000;
    /**
     * Http 写入超时时间
     */
    private static final int WRITE_TIMEOUT = 3000;
    /**
     * Http Read超时时间
     */
    private static final int READ_TIMEOUT = 3000;
    /**
     * Http Async Call Timeout
     */
    private static final int CALL_TIMEOUT = 3000;
    /**
     * Http连接池
     */
    private static final int CONNECTION_POOL_SIZE = 1000;

    /**
     * 静态连接池对象
     */
    private static final ConnectionPool CONNECTION_POOL = new ConnectionPool(CONNECTION_POOL_SIZE, 30, TimeUnit.MINUTES);

    private static final OkHttpClient HTTP_CLIENT;

    static {
        HTTP_CLIENT = getHttpClient();
    }

    /**
     * 获取Http Client对象
     */
    public static OkHttpClient getHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .callTimeout(CALL_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectionPool(CONNECTION_POOL)
                .build();
    }

    /**
     * http get
     * @param url url
     * @return 响应内容字节数组
     */
    public static byte[] getBytes(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            return Objects.requireNonNull(response.body()).bytes();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("http get failed!");
        }
    }

    /**
     * http post
     * @param url url
     * @param body 请求body字符串
     * @return 响应内容字节数组
     */
    public static byte[] postBytes(String url, String body) {
        RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json;charset=utf-8"));
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            return Objects.requireNonNull(response.body()).bytes();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("http post failed!");
        }
    }

}
