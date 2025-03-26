package com.ader.RestApi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.ader.RestApi.pojo.User;
import java.util.Optional;

@RepositoryRestResource(path = "users")
public interface UserRepository extends JpaRepository<User, Long> {
    //However, the method will still be available for use in your service classes, security configuration, or anywhere else in your application code where you need 
    //to look up a user by their login. This is a common pattern - having repository methods that are used internally by your application logic but aren't exposed as API endpoints.
    Optional<User> findByLogin(String login);
    
    // Secure findAll (GET /users) - Only authenticated users
    @Override
    @PreAuthorize("isAuthenticated()")
    Page<User> findAll(Pageable pageable);
    
    // Secure findById (GET /users/{id}) - Only authenticated users
    @Override
    @PreAuthorize("isAuthenticated()")
    Optional<User> findById(Long id);
    
    // Secure save for create (POST /users) - Only administrators
    // Spring Data JPA determines whether to perform an insert or update based on the entity's ID:
    // If ID is null or 0: Spring performs an INSERT (create)
    // If ID exists: Spring performs an UPDATE
    @Override
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    <S extends User> S save(S entity);
    //generic type parameter S that must be User or a subclass of User
    
    // Secure save for update (PUT /users/{id}) - Only administrators
    // This is the same method as above, but Spring Data REST uses it for updates too
    
    // Hide the delete methods - use custom controller for this
    @Override
    @RestResource(exported = false)
    void delete(User entity);
    
    @Override
    @RestResource(exported = false)
    void deleteById(Long id);
}
