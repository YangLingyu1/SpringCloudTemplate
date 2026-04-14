package com.lingyu.config;

import com.lingyu.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

@Configuration
@RequiredArgsConstructor
public class AuthFilter {
    private final JwtUtil jwtUtil;
    // 白名单：直接放行，不校验token
    private static final String[] WHITE_LIST = {
            "/user/login"
    };

    @Bean
    public GlobalFilter authGlobalFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            String path = request.getPath().value();

            // 1. 白名单放行（登录接口）
            for (String url : WHITE_LIST) {
                if (path.startsWith(url)) {
                    return chain.filter(exchange);
                }
            }

            // 2. 获取 token
            String token = request.getHeaders().getFirst("token");

            // 3. 没有 token 直接 401
            if (token == null || token.isBlank()) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            try {
                // ====================== 网关校验核心 ======================
                // 4. 校验并解析 token（你自己网关里的 JwtUtil）
                Long userId = jwtUtil.getUserId(token);

                // 5. 校验通过 → 把 userId 转发给微服务
                ServerHttpRequest newRequest = request.mutate()//修改请求
                        .header("userId", String.valueOf(userId))//添加请求头
                        .build();//创建新的请求

                // chain.filter：放行，继续走过滤器/路由
                // exchange.mutate()：准备修改本次请求
                // request(newRequest)：把旧请求 → 替换成我们新建的请求
                // build()：修改完成，生成新的交换对象
                return chain.filter(exchange.mutate().request(newRequest).build());

            } catch (Exception e) {
                // 6.  token 过期、伪造、错误 → 401
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        };
    }
}