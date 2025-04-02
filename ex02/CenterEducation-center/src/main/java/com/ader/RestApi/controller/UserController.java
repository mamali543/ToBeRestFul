package com.ader.RestApi.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ader.RestApi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

// @RequestMapping("/users")
@RepositoryRestController
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    // Keep only the delete method which has custom logic
    // Remove getAllUsers, createUser, getUserById, updateUser methods
    // as they will be handled by Spring Data REST
    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
