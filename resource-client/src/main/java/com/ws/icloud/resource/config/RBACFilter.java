package com.ws.icloud.resource.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.icloud.common.context.UserContext;
import com.ws.icloud.common.response.ViewResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@Slf4j
@Component
public class RBACFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        //todo 读请求url所需的权限
        Authentication authentication = UserContext.getAuthentication();
        if (Objects.isNull(authentication)) {
            chain.doFilter(request, response);
            return;
        }
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        //当前用户权限
        System.out.println(requestURI + "需要权限：=》" + authorities);
        if (requestURI.contains("hhh")) {
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(ViewResponse.failed("没有权限")));
            log.error("Access Denied Exception:{}", "没有权限");
            return;

        }
        chain.doFilter(request, response);
    }
}