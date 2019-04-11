package com.corsair.sparrow.pirate.oauth.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.corsair.sparrow.pirate.oauth.constant.SecurityConstant;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysRole;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysUser;
import com.corsair.sparrow.pirate.oauth.exception.AuthorityNotFoundException;
import com.corsair.sparrow.pirate.oauth.exception.PhoneCodeNotMatchException;
import com.corsair.sparrow.pirate.oauth.exception.QrCodeException;
import com.corsair.sparrow.pirate.oauth.security.Oauth2UserDetails;
import com.corsair.sparrow.pirate.oauth.security.Oauth2UserDetailsService;
import com.corsair.sparrow.pirate.oauth.service.ISysRoleService;
import com.corsair.sparrow.pirate.oauth.service.ISysUserService;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

/**
 * @author jack
 */
@Component
public class Oauth2UserDetailsServiceImpl implements Oauth2UserDetailsService {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        if(Objects.isNull(sysUser)){
            throw new UsernameNotFoundException(String.format("'%s'用户名不存在",username));
        }
        // 获取Role集合
        Set<SysRole> sysRoleSet = roleService.getRoleSetByUserId(sysUser.getId());
        // 如果系统不支持匿名登陆需要限制需要角色才能登陆
//        if(CollectionUtils.isEmpty(sysRoleSet)){
//            throw new AuthorityNotFoundException(String.format("'%s'该用户没有任何角色",username));
//        }

        return new Oauth2UserDetails(
                sysUser.getId(),
                sysUser.getUsername(),
                sysUser.getPassword(),
                sysRoleSet,
                sysUser.getIsAccountNonExpired(),
                sysUser.getIsAccountNonLocked(),
                sysUser.getIsCredentialsNonExpired(),
                sysUser.getIsEnabled()
        );
    }

    @Override
    public Oauth2UserDetails loadUserByPhoneCode(String phone, String code) throws PhoneCodeNotMatchException {
        // todo 验证手机号验证码登陆
        return null;
    }

    @Override
    public Oauth2UserDetails loadUserByQrCode(String qrCode) throws QrCodeException {
        // todo 验证二维码登陆
        return null;
    }


}
