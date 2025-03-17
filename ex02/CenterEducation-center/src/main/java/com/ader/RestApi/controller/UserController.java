package com.ader.RestApi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;         
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ader.RestApi.pojo.User;
import com.ader.RestApi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "User Controller", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    // The @PreAuthorize annotations method level security work with the role information stored in your JWT token
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all users")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userId") String sort) {
        System.out.println("getAllUsers");
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Create a new user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("createUser");
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get a user by ID")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Update a user")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        user.setUserId(userId);
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}