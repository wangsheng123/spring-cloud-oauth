package com.ws.icloud.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.icloud.auth.api.model.Account;
import com.ws.icloud.common.context.UserContext;
import com.ws.icloud.common.response.ViewResponse;
import com.ws.icloud.auth.api.client.AuthServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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
    @PostMapping("login")
    public Object login(String username, String password) {
        //client_1:admin base编码
        String basic = "Basic Y2xpZW50XzE6YWRtaW4=";
        return authServiceClient.getToken(basic, "password", username, password);
        //return authServiceClient.header(basic,username);
    }
    @PostMapping("userDetail")
    public ViewResponse userDetail()  {
        Authentication authentication = UserContext.getAuthentication();
        return ViewResponse.success(authentication.getPrincipal());
    }
}
