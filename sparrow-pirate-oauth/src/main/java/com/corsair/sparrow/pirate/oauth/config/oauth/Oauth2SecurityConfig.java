package com.corsair.sparrow.pirate.oauth.config.oauth;

import com.corsair.sparrow.pirate.oauth.constant.SecurityConstant;
import com.corsair.sparrow.pirate.oauth.properties.RedisConfigProperties;
import com.corsair.sparrow.pirate.oauth.security.Oauth2UserDetailsService;
import com.corsair.sparrow.pirate.oauth.security.token.Oauth2JwtAccessTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author jack
 */
@Configuration
@Order(Integer.MIN_VALUE)
@EnableAuthorizationServer
public class Oauth2SecurityConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private RedisConfigProperties redisConfigProperties;
    @Autowired
    private Oauth2UserDetailsService oauth2UserDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        lettuceConnectionFactory.setHostName(redisConfigProperties.getHost());
        lettuceConnectionFactory.setDatabase(redisConfigProperties.getDatabase());
        lettuceConnectionFactory.setPassword(redisConfigProperties.getPassword());
        lettuceConnectionFactory.setPort(redisConfigProperties.getPort());
        return lettuceConnectionFactory;
    }

    @Bean
    public TokenStore tokenStore(){
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory());
        redisTokenStore.setPrefix(SecurityConstant.SYS_PREFIX);
        return redisTokenStore;
    }

    @Bean
    public TokenEnhancer tokenEnhancer(){
        return ((oAuth2AccessToken, oAuth2Authentication) -> {
            final Map<String,Object> additionalInfo = new HashMap<>(2);
            additionalInfo.put("license",SecurityConstant.SYS_LICENSE);
            UserDetails userDetails = (UserDetails) oAuth2Authentication.getUserAuthentication().getPrincipal();
            if(Objects.nonNull(userDetails)){
                additionalInfo.put("username",userDetails.getUsername());
                additionalInfo.put("authorities",userDetails.getAuthorities());
                // todo 向token中添加更多定制化信息
            }
            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
            return oAuth2AccessToken;
        });
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        Oauth2JwtAccessTokenConverter jwtAccessTokenConverter = new Oauth2JwtAccessTokenConverter();
        // token key可加强
        jwtAccessTokenConverter.setSigningKey(SecurityConstant.SYS_KEY);
        return jwtAccessTokenConverter;
    }

    @Bean
    public ClientDetailsService clientDetailsService(){
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        // 设置查询client语句
        jdbcClientDetailsService.setFindClientDetailsSql(SecurityConstant.DEFAULT_FIND_STATEMENT);
        jdbcClientDetailsService.setSelectClientDetailsSql(SecurityConstant.DEFAULT_SELECT_STATEMENT);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder());
        return jdbcClientDetailsService;
    }

    /**
     * 设置defaultTokenServices需要@Primary，不然会报错
     * @return
     */
//    @Primary
//    @Bean
//    public DefaultTokenServices defaultTokenServices(){
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(tokenStore());
//        tokenServices.setSupportRefreshToken(true);
//        //tokenServices.setClientDetailsService(clientDetails());
//        // token有效期自定义设置，默认12小时
//        tokenServices.setAccessTokenValiditySeconds(60*60*12);
//        // refresh_token默认30天
//        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
//        return tokenServices;
//    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.checkTokenAccess("permitAll()")
                .tokenKeyAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // token 增强配置
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(tokenEnhancer(),jwtAccessTokenConverter())
        );

        // 配置TokenServices参数
//        DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints.getDefaultAuthorizationServerTokenServices();
//        tokenServices.setTokenStore(tokenStore());
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
//        tokenServices.setTokenEnhancer(tokenEnhancerChain);
//        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(2));
//        tokenServices.setReuseRefreshToken(false);
//        tokenServices.setAuthenticationManager(authenticationManager);

        // 设置tokenServices
        endpoints
//                .tokenServices(tokenServices)
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .reuseRefreshTokens(false)
                .userDetailsService(oauth2UserDetailsService);

    }
}
