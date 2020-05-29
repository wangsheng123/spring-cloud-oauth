package com.ws.icloud.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/test/**").permitAll()
                .anyRequest().authenticated().and()
                .httpBasic();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }
    /*
  @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().requestMatcher(new OAuth2RequestedMatcher())
                .authorizeRequests()
                .antMatchers("/test/**").permitAll()
                .anyRequest().authenticated().and()
                .httpBasic();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }




    *//**
     * 定义OAuth2请求匹配器
     *//*
    private static class OAuth2RequestedMatcher implements RequestMatcher {
        @Override
        public boolean matches(HttpServletRequest request) {
            String auth = request.getHeader("Authorization");
            //判断来源请求是否包含oauth2授权信息,这里授权信息来源可能是头部的Authorization值以Bearer开头,或者是请求参数中包含access_token参数,满足其中一个则匹配成功
            boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
            boolean haveAccessToken = request.getParameter("access_token") != null;
            return haveOauth2Token || haveAccessToken;
        }
    }*/

}
