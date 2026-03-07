package com.gsrs.GSRS.service.impl;

import com.gsrs.GSRS.exception.BadRequestException;
import com.gsrs.GSRS.exception.ResourceNotFoundException;
import com.gsrs.GSRS.exception.UnauthorizedException;
import com.gsrs.GSRS.config.JwtUtil;
import com.gsrs.GSRS.dto.request.LoginRequestDTO;
import com.gsrs.GSRS.dto.request.RegisterRequestDTO;
import com.gsrs.GSRS.dto.response.AuthResponseDTO;
import com.gsrs.GSRS.entity.User;
import com.gsrs.GSRS.exception.BadRequestException;
import com.gsrs.GSRS.exception.ResourceNotFoundException;
import com.gsrs.GSRS.repository.UserRepository;
import com.gsrs.GSRS.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already registered");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .build();

        User saved = userRepository.save(user);
        String token = jwtUtil.generateToken(saved.getEmail(), saved.getRole(), saved.getId());

        return AuthResponseDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .role(saved.getRole())
                .token(token)
                .build();
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());

        return AuthResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }
}