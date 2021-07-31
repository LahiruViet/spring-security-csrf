package com.lahiru.demo.service;

import com.lahiru.demo.dto.JwtRequestDto;
import com.lahiru.demo.dto.JwtResponseDto;

public interface AuthenticationService {

    public JwtResponseDto authenticate(JwtRequestDto jwtRequestDto) throws Exception;
}
