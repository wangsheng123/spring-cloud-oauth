package com.ws.icloud.auth.grant;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Objects;

public class CustomTokenGranterFactory {
    private AuthenticationManager authenticationManager;
    private AuthorizationServerTokenServices tokenServices;
    private ClientDetailsService clientDetailsService;
    private OAuth2RequestFactory requestFactory;

    public CustomTokenGranterFactory(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService) {
        this.authenticationManager = authenticationManager;
        this.tokenServices = tokenServices;
        this.clientDetailsService = clientDetailsService;
        this.requestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    public TokenGranter buildTokenGranter(String grantType) {
        switch (grantType) {
            case "sms_code":
                return new SmsCodeTokenGranter(this.authenticationManager, this.tokenServices, this.clientDetailsService, this.requestFactory);
            default:
                throw new InvalidGrantException("invalid_grant");
        }
    }
}
