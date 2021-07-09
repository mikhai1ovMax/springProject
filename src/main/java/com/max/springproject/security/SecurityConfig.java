package com.max.springproject.security;

import com.max.springproject.models.UserStatus;
import com.max.springproject.security.jwt.JwtConfigurer;
import com.max.springproject.security.jwt.JwtTokenFilter;
import com.max.springproject.security.jwt.JwtTokenProvider;
import com.max.springproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/login").permitAll()
                .mvcMatchers(HttpMethod.GET, "/api/v1/events", "/api/v1/files").hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/api/v1/files").hasAnyRole("MODERATOR", "ADMIN")
                .mvcMatchers(HttpMethod.PUT,  "/api/v1/files").hasAnyRole("MODERATOR", "ADMIN")
                .mvcMatchers("/api/v1/users/").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE,  "/api/v1/events").hasAnyRole("ADMIN")
                .mvcMatchers(HttpMethod.PUT,  "/api/v1/events").hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

//    private final PasswordEncoder passwordEncoder;
//    private final UserService userService;
//
//    @Autowired
//    public SecurityConfig(PasswordEncoder passwordEncoder, UserService userService) {
//        this.passwordEncoder = passwordEncoder;
//        this.userService = userService;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .mvcMatchers(HttpMethod.GET, "/api/v1/events", "/api/v1/files").hasAnyRole("USER", "ADMIN")
//                .mvcMatchers(HttpMethod.DELETE, "/api/v1/files").hasAnyRole("MODERATOR", "ADMIN")
//                .mvcMatchers(HttpMethod.PUT, "/api/v1/files").hasAnyRole("MODERATOR", "ADMIN")
//                .mvcMatchers("/api/v1/users/").hasRole("ADMIN")
//                .mvcMatchers(HttpMethod.DELETE,  "/api/v1/events").hasAnyRole("ADMIN")
//                .mvcMatchers(HttpMethod.PUT,  "/api/v1/events").hasAnyRole("ADMIN")
//
//
////                .antMatchers("/api/v1/events/**", "/api/v1/files/**").hasAnyRole("USER", "ADMIN")
////                .antMatchers("/api/v1/files/**", "/api/v1/users/**").hasAnyRole("MODERATOR", "ADMIN")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//    }
//
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        List<UserDetails> userDetailsList = new ArrayList<>();
//        userDetailsList.add( User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("1111"))
//                .roles("ADMIN")
//                .build());
//
//        userDetailsList.add(User.builder()
//                .username("moderator")
//                .password(passwordEncoder.encode("1111"))
//                .roles("MODERATOR")
//                .build());
//
//        userDetailsList.add(User.builder()
//                .username("user")
//                .password(passwordEncoder.encode("1111"))
//                .roles("USER")
//                .build());
//
//        userService.getAll().forEach(x -> {
//            if(x.getUserStatus() == UserStatus.ACTIVE){
//                userDetailsList.add(
//                        User.builder()
//                        .username(x.getName())
//                        .password(passwordEncoder.encode(x.getPassword()))
//                        .roles(String.valueOf(x.getRole()))
//                        .build()
//                );
//            }
//        });
//        return new InMemoryUserDetailsManager(userDetailsList);
//    }

}
