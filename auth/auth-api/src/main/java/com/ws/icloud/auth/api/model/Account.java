package com.ws.icloud.auth.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer accountId;


    /**
     * 账号
     */
    private String account;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 上一次登录时间
     */
    private Date lastLoginTime;

    /**
     * 账号是否可用。默认为1（可用）
     */
    private boolean enabled;

    /**
     * 是否过期。默认为1（没有过期）
     */
    private boolean accountNonExpired;

    /**
     * 账号是否锁定。默认为1（没有锁定）
     */
    private boolean accountNonLocked;

    /**
     * 证书（密码）是否过期。默认为1（没有过期）
     */
    private boolean credentialsNonExpired;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 修改人
     */
    private Integer updateUser;


    private transient Collection<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       /* List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (account != null) {
            //获取该用户所拥有的权限
            this.roleList.forEach(role -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleCode());
                grantedAuthorities.add(grantedAuthority);
            });
        }*/
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.account;
    }


}
