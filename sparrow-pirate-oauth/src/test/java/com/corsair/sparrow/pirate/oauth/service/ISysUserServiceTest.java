package com.corsair.sparrow.pirate.oauth.service;

import com.corsair.sparrow.pirate.oauth.domain.bean.SysRole;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysUser;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysUserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ISysUserServiceTest {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Test
    public void saveUser(){
        // 生成用户
        SysUser sysUser = SysUser.builder()
                .username("jack")
                .password(passwordEncoder().encode("123456"))
                .birthday(new Date())
                .email("9874118@qq.com")
                .phone("18320941632")
                .gender("male")
                .build();
        sysUserService.save(sysUser);

        // 生成角色
        SysRole sysRole = SysRole.builder()
                .roleCode("ROLE_ROOT")
                .roleName("ROOT")
                .build();
        sysRoleService.save(sysRole);

        // 创建关联
        SysUserRole sysUserRole = SysUserRole.builder()
                .userId(sysUser.getId())
                .roleId(sysRole.getId())
                .build();
        sysUserRoleService.save(sysUserRole);
    }

}