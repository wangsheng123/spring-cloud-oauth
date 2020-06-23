package com.ws.icloud.user;

import com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {})
@ComponentScan(basePackages = {"com.ws.icloud"})
@EnableFeignClients(basePackages = {"com.ws.icloud.*.*.client"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
