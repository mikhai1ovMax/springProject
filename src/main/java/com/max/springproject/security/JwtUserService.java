package com.max.springproject.security;

import com.max.springproject.security.jwt.JwtUser;
import com.max.springproject.security.jwt.JwtUserFactory;
import com.max.springproject.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserService implements UserDetailsService {

    private final UserService userService;

    public JwtUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.max.springproject.models.User user = userService.findByName(username);
        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}
