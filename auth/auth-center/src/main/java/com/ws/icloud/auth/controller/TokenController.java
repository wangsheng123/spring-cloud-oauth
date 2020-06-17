package com.ws.icloud.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.icloud.auth.api.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("token")
public class TokenController {

    @Autowired
    private DefaultTokenServices defaultTokenServices;

    private AccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/oauth/check_token")
    @ResponseBody
    public Map<String, ?> checkToken(@RequestParam("token") String value) throws JsonProcessingException {

        OAuth2AccessToken token = defaultTokenServices.readAccessToken(value);
        if (token == null) {
            throw new InvalidTokenException("Token was not recognised");
        }

        if (token.isExpired()) {
            throw new InvalidTokenException("Token has expired");
        }

        OAuth2Authentication authentication = defaultTokenServices.loadAuthentication(token.getValue());

        Map<String, Object> response = (Map<String, Object>) accessTokenConverter.convertAccessToken(token, authentication);

     //   response.put("user_name",objectMapper.writeValueAsString(authentication.getPrincipal()));


        // gh-1070
        response.put("active", true);	// Always true if token exists and not expired

        return response;
    }

}
