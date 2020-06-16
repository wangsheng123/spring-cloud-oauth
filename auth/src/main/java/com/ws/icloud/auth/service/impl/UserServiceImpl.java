package com.ws.icloud.auth.service.impl;


import com.ws.icloud.auth.entity.Account;
import com.ws.icloud.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 测试写死账号
     * @param username
     * @return
     */
    @Override
    public UserDetails findByUsername(String username) {
        List<GrantedAuthority> authorities=new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority("ADMIN");
        authorities.add(simpleGrantedAuthority);
        Account account=new Account();
        account.setAccount(username);
        account.setPassword(passwordEncoder.encode("admin"));
        account.setAccountId(10001);
        account.setAccountNonLocked(true);
        account.setAccountNonExpired(true);
        account.setCredentialsNonExpired(true);
        account.setEnabled(true);
        return account;
    }
}
