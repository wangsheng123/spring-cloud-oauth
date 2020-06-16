package com.ws.icloud.resource.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.icloud.common.response.ResultCode;
import com.ws.icloud.common.response.ViewResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {

    private ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ViewResponse viewResponse=ViewResponse.failed(ResultCode.ACCESS_DENIED_ERROR);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(viewResponse));
    }
}