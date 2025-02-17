package com.ader.RestApi.service;

import com.ader.RestApi.pojo.AuthenticationRequest;
import com.ader.RestApi.pojo.AuthenticationResponse;
import com.ader.RestApi.pojo.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
