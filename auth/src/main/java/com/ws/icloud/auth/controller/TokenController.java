package com.ws.icloud.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.ws.icloud.auth.AccessTokenJackson2Serializer;
import com.ws.icloud.auth.ClientDetailsRowMapper;
import com.ws.icloud.auth.DefaultClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/test/")
public class TokenController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DefaultTokenServices defaultTokenServices;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    // 扩展 默认的 ClientDetailsService, 增加逻辑删除判断( status = 1)
    private static final String SELECT_CLIENT_DETAILS_SQL = "select client_id, client_secret, resource_ids, scope, authorized_grant_types, " +
            "web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove ,if_limit, limit_count " +
            "from oauth_client_details where client_id = ? and `status` = 1 ";

    @GetMapping("token")
    public Object token() {
        List<GrantedAuthority> authorities=new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority("ADMIN");
        authorities.add(simpleGrantedAuthority);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("admin", "admin",authorities);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        String clientId="testtwo";
        ClientDetails clientDetails = jdbcTemplate.queryForObject(SELECT_CLIENT_DETAILS_SQL, new ClientDetailsRowMapper(), clientId);
        TokenRequest tokenRequest = new TokenRequest(Maps.newHashMap(), clientId,clientDetails.getScope(),
                "customer");
        OAuth2Request oAuth2Request =tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication=new OAuth2Authentication(oAuth2Request,authenticate);
        OAuth2AccessToken accessToken= defaultTokenServices.createAccessToken(oAuth2Authentication);
        OAuth2AccessToken enhance = jwtAccessTokenConverter.enhance(accessToken, oAuth2Authentication);
        return enhance;
    }

    @GetMapping("hello")
    public String hello() {
       // redisTemplate.opsForValue().set("hello","auth");
        return "hello auth";
    }

    @GetMapping("header")
    public String header(@RequestHeader(value = "Authorization",required = false) String authorization, @RequestParam("username") String username) {
        return authorization+"  ==>  "+username;
    }
}
