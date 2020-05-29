package com.ws.icloud.auth.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


public interface UserService {
    @GetMapping("user/findByUsername/{username}")
    User findByUsername(@PathVariable("username") String username);
}
