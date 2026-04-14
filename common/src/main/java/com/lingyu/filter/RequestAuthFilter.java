package com.lingyu.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // 放行登录接口
        if (path.startsWith("/user/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String userId = request.getHeader("userId");

        if (userId == null || userId.isBlank()) {
            response.setStatus(401);
            response.getWriter().write("请通过网关访问");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
