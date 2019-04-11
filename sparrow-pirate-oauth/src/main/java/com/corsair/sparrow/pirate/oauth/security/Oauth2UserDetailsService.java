package com.corsair.sparrow.pirate.oauth.security;

import com.corsair.sparrow.pirate.oauth.exception.PhoneCodeNotMatchException;
import com.corsair.sparrow.pirate.oauth.exception.QrCodeException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author jack
 */
public interface Oauth2UserDetailsService extends UserDetailsService {

    /**
     * 手机验证码登陆
     * @param phone
     * @param code
     * @return
     * @throws PhoneCodeNotMatchException
     */
    Oauth2UserDetails loadUserByPhoneCode(String phone,String code) throws PhoneCodeNotMatchException;

    /**
     * 二维码扫码登陆
     * @param qrCode
     * @return
     * @throws QrCodeException
     */
    Oauth2UserDetails loadUserByQrCode(String qrCode) throws QrCodeException;
}
