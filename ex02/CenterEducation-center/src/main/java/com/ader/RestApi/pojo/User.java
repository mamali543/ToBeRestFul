package com.ader.RestApi.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import com.ader.RestApi.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users", schema = "spring")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "userId", "firstName", "lastName", "login", "password", "role" })
@Builder
public class User implements UserDetails {

    @Id // Marks this field as the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates values for this field using database identity
                                                        // column
    @Column(name = "user_id") // Maps to the user_id column in the database table
    private Long userId; // Field to store the user's unique identifier

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Role role;

    // Relationships
    @JsonIgnore // Prevents infinite recursion when serializing to JSON
    @ManyToMany(mappedBy = "students") // Indicates this is the inverse side of the many-to-many relationship defined
                                       // in Course class
    @RestResource(exported = false) // Prevent Spring Data REST from exposing this association
    private List<Course> enrolledCourses = new ArrayList<>(); // List of courses that this user (as a student) is
                                                              // enrolled in

    @JsonIgnore
    @ManyToMany(mappedBy = "teachers")
    @RestResource(exported = false) // Prevent Spring Data REST from exposing this association
    private List<Course> taughtCourses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)
    @RestResource(exported = false) // Prevent Spring Data REST from exposing this association
    private List<Lesson> lessons = new ArrayList<>();

    // Spring Security methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
