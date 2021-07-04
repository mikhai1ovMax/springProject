package com.max.springproject.controllers;

import com.max.springproject.dto.AuthRequestDto;
import com.max.springproject.models.User;
import com.max.springproject.security.jwt.JwtTokenProvider;
import com.max.springproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/auth/")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthRequestDto requestDto){
         try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
            User user = userService.findByName(requestDto.getUsername());
            if(user == null){
                throw new UsernameNotFoundException(requestDto.getUsername() + "not found");
            }
            String token = jwtTokenProvider.createToken(user.getName(), Arrays.asList(user.getRole()));

            Map<Object, Object> response1 = new HashMap<>();
             response1.put("name", user.getName());
             response1.put("token", token);

            return ResponseEntity.ok(response1);
         }catch (AuthenticationException e) {
             throw new BadCredentialsException("invalid username or password");
         }
    }
}
