package com.lahiru.demo.service.impl;

import com.lahiru.demo.dto.JwtRequestDto;
import com.lahiru.demo.dto.JwtResponseDto;
import com.lahiru.demo.service.AuthenticationService;
import com.lahiru.demo.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userService;

    @Override
    public JwtResponseDto authenticate(JwtRequestDto jwtRequestDto) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequestDto.getUsername(),
                            jwtRequestDto.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequestDto.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponseDto(token);
    }
}
