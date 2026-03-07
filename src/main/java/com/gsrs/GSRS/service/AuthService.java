package com.gsrs.GSRS.service;

import com.gsrs.GSRS.dto.request.LoginRequestDTO;
import com.gsrs.GSRS.dto.request.RegisterRequestDTO;
import com.gsrs.GSRS.dto.response.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO dto);
    AuthResponseDTO login(LoginRequestDTO dto);
}