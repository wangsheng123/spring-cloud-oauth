package com.ws.icloud.auth.api.client;

import com.ws.icloud.auth.api.model.JWT;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "auth")
public interface AuthServiceClient {

    @PostMapping(value = "/oauth/token")
    JWT getToken(@RequestHeader(value = "Authorization") String authorization,@RequestParam Map<String, String> parameters);
    @GetMapping(value = "/test/header")
    String header(@RequestHeader(value = "Authorization") String authorization, @RequestParam("username") String username);


}

