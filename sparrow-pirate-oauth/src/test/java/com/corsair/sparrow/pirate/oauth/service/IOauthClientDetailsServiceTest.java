package com.corsair.sparrow.pirate.oauth.service;

import com.corsair.sparrow.pirate.oauth.domain.bean.OauthClientDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IOauthClientDetailsServiceTest {

    @Autowired
    private IOauthClientDetailsService oauthClientDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Test
    public void testSave(){
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId("sparrow");
        oauthClientDetails.setClientSecret(passwordEncoder().encode("sparrow"));
        // grant_types : "authorization_code","client_credentials","password","implicit","refresh_token"
        oauthClientDetails.setAuthorizedGrantTypes("authorization_code,client_credentials,password,implicit,refresh_token");
        oauthClientDetails.setAdditionalInformation("made by jack");
        oauthClientDetails.setAutoapprove("true");
        oauthClientDetails.setScope("test");


        oauthClientDetailsService.save(oauthClientDetails);
    }

}