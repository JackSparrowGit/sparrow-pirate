package com.corsair.sparrow.pirate.oauth.security;

import com.corsair.sparrow.pirate.oauth.constant.SecurityConstant;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysRole;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jack
 */
@Data
public class Oauth2UserDetails implements BaseUserDetails{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;

    private Set<SysRole> sysRoleSet;

    /**
     * 如系统无需以下定制可全默认为true
     */
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnable;

    public Oauth2UserDetails() {
    }

    public Oauth2UserDetails(Long id, String username, String password, Set<SysRole> sysRoleSet, Boolean isAccountNonExpired, Boolean isAccountNonLocked, Boolean isCredentialsNonExpired, Boolean isEnable) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.sysRoleSet = sysRoleSet;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnable = isEnable;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SysRole> sysRoleSet = this.sysRoleSet;
        if(CollectionUtils.isEmpty(sysRoleSet)){
            return null;
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        sysRoleSet.forEach(sysRole -> {
            authorities.add(new SimpleGrantedAuthority(prefixRoleName(sysRole.getRoleCode())));
        });
        // 根据实际业务判断是否需要加基础角色
//        authorities.add(new SimpleGrantedAuthority(prefixRoleName(SecurityConstant.BASE_ROLE)));
        return authorities;
    }

    /**
     * 规范化ROLE_前缀
     * @param roleName
     * @return
     */
    private String prefixRoleName(String roleName) {
        if (!StringUtils.isEmpty(roleName) && !roleName.startsWith(SecurityConstant.ROLE_PREFIX)) {
            return SecurityConstant.ROLE_PREFIX + roleName;
        }
        return roleName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnable;
    }

}
