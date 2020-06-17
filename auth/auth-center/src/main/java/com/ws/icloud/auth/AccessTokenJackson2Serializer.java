package com.ws.icloud.auth;

import lombok.Data;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Data
public class AccessTokenJackson2Serializer implements OAuth2AccessToken {

    private String value;

    private Date expiration;

    private String tokenType = BEARER_TYPE.toLowerCase();

    private OAuth2RefreshToken refreshToken;

    private Set<String> scope;

    private Map<String, Object> additionalInformation = Collections.emptyMap();

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return this.additionalInformation;
    }

    @Override
    public Set<String> getScope() {
        return this.scope;
    }

    @Override
    public OAuth2RefreshToken getRefreshToken() {
        return this.refreshToken;
    }

    @Override
    public String getTokenType() {
        return this.tokenType;
    }

    @Override
    public boolean isExpired() {

        return expiration != null && expiration.before(new Date());
    }

    @Override
    public Date getExpiration() {
        return this.expiration;
    }

    @Override
    public int getExpiresIn() {
        return expiration != null ? Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L)
                .intValue() : 0;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}