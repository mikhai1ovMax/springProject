package com.max.springproject.security.jwt;

import com.max.springproject.models.User;
import com.max.springproject.models.UserRole;
import com.max.springproject.models.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {

    }

    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getRole(),
                user.getUserStatus().equals(UserStatus.ACTIVE),
                mapToGrantedAuthorities(Arrays.asList(user.getRole()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<UserRole> roles){
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }
}
