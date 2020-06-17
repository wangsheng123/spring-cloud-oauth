package com.ws.icloud.resource.token;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

@AllArgsConstructor
@Configuration
public class RedisTokenStoreConfig {

    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @Qualifier("tokenStore")
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

}