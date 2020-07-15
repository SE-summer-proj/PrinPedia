package com.prinpedia.backend.security;

import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserSecurityServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        if(username == null || username.equals("")) {
            throw new RuntimeException("Empty username");
        }

        User user = userService.findUserByName(username);
        if(user == null) {
            throw new RuntimeException("User not exist");
        }

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        String auth = user.getAuthority().toString();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(auth);
        grantedAuthorityList.add(grantedAuthority);
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), grantedAuthorityList);
    }
}
