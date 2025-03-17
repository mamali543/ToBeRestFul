package com.ader.RestApi.pojo;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String login;
    private String password;
}
