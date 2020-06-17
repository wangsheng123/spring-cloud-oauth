
package com.ws.icloud.auth.config;

import com.ws.icloud.auth.error.MssWebResponseExceptionTranslator;
import com.ws.icloud.auth.token.RedisTemplateTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    private DataSource dataSource;

    private RedisConnectionFactory redisConnectionFactory;

    private RedisTemplateTokenStore redisTemplateTokenStore;

    @Autowired
    private TokenEndpoint tokenEndpoint;

    public AuthorizationServerConfig(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, DataSource dataSource, RedisConnectionFactory redisConnectionFactory, RedisTemplateTokenStore redisTemplateTokenStore) {
        this.authenticationManager = authenticationManager;
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.redisConnectionFactory = redisConnectionFactory;
        this.redisTemplateTokenStore = redisTemplateTokenStore;
    }

  /*  @Bean
    RedisTokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
*/

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
    }

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator() {
        return new MssWebResponseExceptionTranslator();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

      /*  endpoints.tokenStore(jwtTokenStore()) // token使用jwt持久化策略
                .tokenEnhancer(jwtTokenEnhancer()) //OAuth2AccessToken 转化为jwt token*/

        endpoints.authenticationManager(authenticationManager);

        endpoints.tokenServices(defaultTokenServices()); //负责token的创建刷新等crud操作
        endpoints.exceptionTranslator(webResponseExceptionTranslator());//认证异常


    }


    /**
     * 负责token生成
     *
     * @return
     */

    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(redisTemplateTokenStore); //jwt持久化 可以db redis
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetails()); //从db加载clientDetail
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 12); // token有效期自定义设置，默认12小时
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);//默认30天，这里修改
        return tokenServices;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许表单认证
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(passwordEncoder);  // client_secret用BCrypt加密
    }


    /**
     * @Description: 获取token时接受get方式
     * @author: ws
     * @Date: 2020/5/29
     */
    @PostConstruct
    public void reconfigure() {
        Set<HttpMethod> allowedMethods =
                new HashSet<>(Arrays.asList(HttpMethod.GET, HttpMethod.POST));
        tokenEndpoint.setAllowedRequestMethods(allowedMethods);
    }
}
