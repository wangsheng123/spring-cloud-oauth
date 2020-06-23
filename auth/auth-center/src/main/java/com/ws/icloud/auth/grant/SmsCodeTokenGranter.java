package com.ws.icloud.auth.grant;

import com.ws.icloud.auth.authentication.SmsCodeAuthenticationToken;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ws
 * @version 0.1
 * @date 2020/6/23
 */
public class SmsCodeTokenGranter extends CustomTokenGranter  {
    // 仅仅复制了 ResourceOwnerPasswordTokenGranter，只是改变了 GRANT_TYPE 的值，来验证自定义授权模式的可行性
    private static final String GRANT_TYPE="sms_code";
    private final AuthenticationManager authenticationManager;
    protected SmsCodeTokenGranter(AuthenticationManager authenticationManager,AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager=authenticationManager;
    }
    public SmsCodeTokenGranter(AuthenticationManager authenticationManager,AuthorizationServerTokenServices tokenServices,ClientDetailsService clientDetailsService,OAuth2RequestFactory requestFactory){
        this(authenticationManager,tokenServices,clientDetailsService,requestFactory,GRANT_TYPE);
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
        String phone = parameters.get("phone");
        Authentication userAuth = new SmsCodeAuthenticationToken(phone);
        ((AbstractAuthenticationToken)userAuth).setDetails(parameters);

        try {
            userAuth = this.authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException var8) {
            throw new InvalidGrantException(var8.getMessage());
        } catch (BadCredentialsException var9) {
            throw new InvalidGrantException(var9.getMessage());
        }

        if (userAuth != null && userAuth.isAuthenticated()) {
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, userAuth);
        } else {
            throw new InvalidGrantException("Could not authenticate phone: " + phone);
        }
    }
}
