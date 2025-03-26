package com.ader.RestApi.pojo;

import lombok.RequiredArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private Role role;
}
