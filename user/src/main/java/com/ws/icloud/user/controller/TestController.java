package com.ws.icloud.user.controller;

import com.ws.icloud.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TokenStore tokenStore;

    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    UserService userService;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @GetMapping("hello")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String hello() {
        return "hello user2";
    }

    @GetMapping("authDetail")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Object authDetail() {
        Object authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @GetMapping("task")
    public String task() throws ExecutionException, InterruptedException {
        return threadPoolTaskScheduler.submit(() -> "success 2").get();
    }


    @PostMapping("testMap")
    public Map<String, ?> testMap() {
        Map<String, Object> map = new LinkedHashMap<>();
       // userService.testException();
        map.put("token", "sdfsdas-1sdcfsx-sad");
        return map;
    }

}
