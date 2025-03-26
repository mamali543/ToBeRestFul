package com.ader.RestApi.event;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ader.RestApi.pojo.User;

import lombok.RequiredArgsConstructor;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class UserEventHandler {

    private final PasswordEncoder passwordEncoder;

    @HandleBeforeCreate
    public void handleUserCreate(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @HandleBeforeSave
    public void handleUserUpdate(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}