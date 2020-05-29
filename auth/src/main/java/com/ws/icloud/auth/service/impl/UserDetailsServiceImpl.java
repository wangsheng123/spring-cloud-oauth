package com.ws.icloud.auth.service.impl;

import com.ws.icloud.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username)   {
        User byUsername = userService.findByUsername(username);
        if (byUsername == null) {
            throw new UsernameNotFoundException("用户:" + username + ",不存在!");
        }

        return byUsername;
    }
}
