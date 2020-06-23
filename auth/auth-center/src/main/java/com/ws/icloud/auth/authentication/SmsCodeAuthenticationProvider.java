package com.ws.icloud.auth.authentication;

import com.ws.icloud.auth.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    public SmsCodeAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phone = (String) authentication.getPrincipal();
        //在服务调用方校验了验证码是否正确,这里不校验了
   //     String smscode = (String) authentication.getCredentials();
        if(StringUtils.isBlank(phone)){
            throw new UsernameNotFoundException("username用户名不可以为空");
        }

        //获取用户信息
        UserDetails user = userService.loadUserByPhone(phone);

        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new SmsCodeAuthenticationToken(user, authorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}