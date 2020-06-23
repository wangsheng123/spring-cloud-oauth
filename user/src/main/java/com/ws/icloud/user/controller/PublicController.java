package com.ws.icloud.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.icloud.auth.api.client.AuthServiceClient;
import com.ws.icloud.common.context.UserContext;
import com.ws.icloud.common.response.ViewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    ObjectMapper objectMapper;
    @PostMapping("login")
    public Object login(String username, String password) {
        //client_1:admin base编码
        String basic = "Basic Y2xpZW50XzE6YWRtaW4=";
        Map<String,String> parameters=new HashMap<>();
        parameters.put("grant_type","password");
        parameters.put("username",username);
        parameters.put("password",password);
        return authServiceClient.getToken(basic,  parameters);
        //return authServiceClient.header(basic,username);
    }
    @PostMapping("smsLogin")
    public Object smsLogin(String phone, String smsCode) {
        //client_1:admin base编码
        String basic = "Basic Y2xpZW50XzE6YWRtaW4=";
        Map<String,String> parameters=new HashMap<>();
        parameters.put("grant_type","sms_code");
        parameters.put("phone",phone);
        return authServiceClient.getToken(basic, parameters);
        //return authServiceClient.header(basic,username);
    }

}
