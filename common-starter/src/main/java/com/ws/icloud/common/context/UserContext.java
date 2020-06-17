package com.ws.icloud.common.context;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Collection;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserContext {

    /**
     * 获取用户名(手机号)
     *
     * @return
     */
    public static String getUsername() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        } else {
            return authentication.getName();
        }
    }

    /**
     * 获取用户权限
     */
    public static Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthentication().getAuthorities();
    }

    /**
     * 获取账号
     */
    public static Object getAccount() {
        return getAuthentication().getPrincipal();
    }


    /**
     * 获取已认证信息
     *
     * @return
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
