package com.example.gateway.filter;

import com.example.gateway.utils.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class JwtFilter implements GlobalFilter, Ordered {
    // 存储特定路径的列表
    private final List<String> excludedPaths = Arrays.asList("/user/login", "/user/register",
            "/user/exist","/user/sendCode","/user/loginByPhone",
            "/user/v2/api-docs","/helper/v2/api-docs","/doc/v2/api-docs",
            "/ppt/v2/api-docs","/video/v2/api-docs","/virtual/v2/api-docs");

    private final Pattern videoMp4Pattern = Pattern.compile("^/video/mp4/[^/]+$");
    private final Pattern videoPptPattern = Pattern.compile("^/video/pptx/[^/]+$");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestPath = exchange.getRequest().getPath().toString();
        System.out.println("requestPath: " + requestPath);
        // 排除特定路径
        if (excludedPaths.contains(requestPath)
                || videoMp4Pattern.matcher(requestPath).matches()
                || videoPptPattern.matcher(requestPath).matches()) {
            return chain.filter(exchange);
        }
        // 从请求头中获取 token
        String token = exchange.getRequest().getHeaders().getFirst("token");
        System.out.println(token);
        // 验证 token
        if (token == null || !JwtUtils.judge(token)) {
            // 如果 token 无效，直接返回响应
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // 如果 token 有效，从 JWT 中获取 userId 和 userType
        List<String> idsAndTypes = JwtUtils.getIdAndType(token);
        String userId = idsAndTypes.get(0);
        String userType = idsAndTypes.get(1);

        // 将 userId 和 userType 添加到请求头中
        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("userId", userId)
                .header("userType", userType)
                .build();
        ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();

        return chain.filter(modifiedExchange);
    }

    /**
     * 指定全局过滤器执行的顺序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return -1;
    }

}
