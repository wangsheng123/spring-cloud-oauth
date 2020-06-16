package com.ws.icloud.user.controller;

import com.ws.icloud.common.context.UserContext;
import com.ws.icloud.common.response.ViewResponse;
import com.ws.icloud.user.client.AuthServiceClient;
import com.ws.icloud.user.entity.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.swing.text.View;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthServiceClient authServiceClient;

    @PostMapping("login")
    public Object login(String username, String password) {
        //client_1:admin base编码
        String basic = "Basic Y2xpZW50XzE6YWRtaW4=";
        return authServiceClient.getToken(basic, "password", username, password);
        //return authServiceClient.header(basic,username);
    }
    @PostMapping("userDetail")
    public ViewResponse userDetail() {
        Authentication authentication = UserContext.getAuthentication();
        User account = (User) UserContext.getAccount();
        return ViewResponse.success(UserContext.getAccount());
    }
}
