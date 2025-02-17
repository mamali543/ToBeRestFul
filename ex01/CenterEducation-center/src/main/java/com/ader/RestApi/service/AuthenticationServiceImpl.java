package com.ader.RestApi.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.ader.RestApi.pojo.AuthenticationRequest;
import com.ader.RestApi.pojo.AuthenticationResponse;
import com.ader.RestApi.pojo.RegisterRequest;
import com.ader.RestApi.pojo.User;
import com.ader.RestApi.repositories.UserRepository;
import com.ader.RestApi.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .login(request.getLogin())
            .password(request.getPassword())
            .role(request.getRole())
            .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword())
        );
        var user = userRepository.findByLogin(request.getLogin())
            .orElseThrow(() -> new RuntimeException("User not found"));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }
}