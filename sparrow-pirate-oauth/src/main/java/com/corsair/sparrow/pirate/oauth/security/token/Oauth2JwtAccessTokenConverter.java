package com.corsair.sparrow.pirate.oauth.security.token;

import com.corsair.sparrow.pirate.oauth.constant.SecurityConstant;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * @author jack
 */
public class Oauth2JwtAccessTokenConverter extends JwtAccessTokenConverter {

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String,Object> representation = (Map<String, Object>) super.convertAccessToken(token,authentication);
        representation.put("license", SecurityConstant.SYS_LICENSE);
        return super.convertAccessToken(token, authentication);
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        return super.extractAccessToken(value, map);
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        return super.extractAuthentication(map);
    }
}
