package com.corsair.sparrow.pirate.oauth.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

/**
 * @author jack
 */
public interface BaseUserDetails extends UserDetails {

    /**
     * 主键
     * @return
     */
    Serializable getId();
}
