package com.actia.projects.security;

import java.io.IOException;
import java.util.ArrayList;


import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.actia.projects.SpringApplicationContext;
import com.actia.projects.dto.UserDto;
import com.actia.projects.services.JwtUtil;
import com.actia.projects.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {

            UserDto user = new ObjectMapper().readValue(req.getInputStream(), UserDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        String email = ((User) auth.getPrincipal()).getUsername();
        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        JwtUtil jwtUtil = (JwtUtil) SpringApplicationContext.getBean("jwtUtil");
        UserDto user = userService.getUserByEmail(email);
       
        String token = jwtUtil.generateToken(user);
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        res.addHeader("user_id", user.getEmail());

        res.getWriter().write("{\"token\": \"" + token + "\", \"id\": \"" + user.getId() + "\"}");


    }


}