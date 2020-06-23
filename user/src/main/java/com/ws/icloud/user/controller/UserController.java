package com.ws.icloud.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.icloud.common.context.UserContext;
import com.ws.icloud.common.response.ViewResponse;
import com.ws.icloud.auth.api.client.AuthServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("userDetail")
    public ViewResponse userDetail()  {
        Authentication authentication = UserContext.getAuthentication();
        return ViewResponse.success(authentication.getPrincipal());
    }
    @GetMapping("hello")
    public ViewResponse hello()  {
        return ViewResponse.success("hello");
    }
}
