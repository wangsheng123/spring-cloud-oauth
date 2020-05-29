package com.ws.icloud.auth.service.impl;


import com.ws.icloud.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User findByUsername(String username) {
        List<GrantedAuthority> authorities=new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority("ADMIN");
        authorities.add(simpleGrantedAuthority);
        return new User("admin",passwordEncoder.encode("admin"),authorities);
    }
}
