package com.riyeyuedu.config;

/**
 * @author haojie tang
 * @date 2018/9/25 11:18
 */

import com.riyeyuedu.entity.RoleEntity;
import com.riyeyuedu.entity.UserEntity;
import com.riyeyuedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private UserService userService;

    @Autowired
    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity myUser = userService.getReaderByName(username);
        if (myUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 根据用户名查找到对于的密码和权限
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (RoleEntity role : myUser.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            grantedAuthorities.add(grantedAuthority);
        }
        return new org.springframework.security.core.userdetails.User(
                myUser.getUsername(),
                myUser.getPassword(),
                grantedAuthorities);
    }
}
