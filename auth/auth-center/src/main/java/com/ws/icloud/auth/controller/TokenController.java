package com.ws.icloud.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.icloud.auth.api.model.Account;
import com.ws.icloud.common.response.ViewResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
@Slf4j
public class TokenController {

    @Autowired
    private DefaultTokenServices defaultTokenServices;

    private AccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/check_token")

    public Map<String, ?> checkToken(@RequestParam("token") String value) throws JsonProcessingException {
        Map<String, Object> response = new HashMap<>();
        OAuth2AccessToken token = defaultTokenServices.readAccessToken(value);
        if (token == null) {
            log.error("Token was not recognised");
            response.put("error", "Token was not recognised");
            return response;
        }
        if (token.isExpired()) {
            log.error("Token has expired");

            response.put("error", "Token has expired");
            return response;
        }

        OAuth2Authentication authentication = defaultTokenServices.loadAuthentication(token.getValue());

        response = (Map<String, Object>) accessTokenConverter.convertAccessToken(token, authentication);

        //   response.put("user_name",objectMapper.writeValueAsString(authentication.getPrincipal()));


        // gh-1070
        response.put("active", true);    // Always true if token exists and not expired

        return response;
    }
    @GetMapping("hello")
    public ViewResponse hello()  {
        return ViewResponse.success("hello");
    }
}
