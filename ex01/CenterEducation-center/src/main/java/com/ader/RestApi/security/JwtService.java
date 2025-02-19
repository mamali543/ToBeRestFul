package com.ader.RestApi.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.ader.RestApi.pojo.User;

/**
 * Service class for handling JWT (JSON Web Token) operations
 * This service provides methods for creating, validating and extracting
 * information from JWTs
 */
@Service
public class JwtService {

    /** Secret key used for signing JWTs */
    private static final String SECRET_KEY = "4iSpEg/EnSthq/CkF5qk0x049yVUrCS5A1h+yCOPrqNi+MElXyKr2WitOA0KhrMS";

    /**
     * Extracts the username from a JWT token
     * 
     * @param jwt The JWT token string
     * @return The username stored in the token
     */
    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from a JWT token
     * 
     * @param jwt The JWT token
     * @return The expiration date of the token
     */
    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    /**
     * Generic method to extract a claim from the JWT token
     * 
     * @param <T>            The type of the claim to extract
     * @param jwt            The JWT token string
     * @param claimsResolver Function to extract the desired claim
     * @return The extracted claim value
     */
    private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from a JWT token
     * 
     * @param jwt The JWT token string
     * @return All claims stored in the token
     */
    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * Generates the signing key used for JWT operations
     * 
     * @return The signing key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT token for a user without any extra claims
     * 
     * @param userDetails The user details
     * @return Generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        // Assuming userDetails is your User entity with these fields
        User user = (User) userDetails;

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getUserId()); // Add user ID
        extraClaims.put("role", user.getRole()); // Add user role
        extraClaims.put("login", user.getLogin()); // Add user login

        return generateToken(extraClaims, userDetails);
    }

    /**
     * Generates a JWT token with extra claims
     * 
     * @param extraClaims Additional claims to include in the token
     * @param userDetails The user details
     * @return Generated JWT token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims) // Set the extra claims
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates if a token is valid for a given user
     * 
     * @param jwt         The JWT token to validate
     * @param userDetails The user details to validate against
     * @return true if token is valid, false otherwise
     */
    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
    }

    /**
     * Checks if a token has expired
     * 
     * @param jwt The JWT token to check
     * @return true if token is expired, false otherwise
     */
    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", String.class));
    }

    public String extractUserRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public String extractUserLogin(String token) {
        return extractClaim(token, claims -> claims.get("login", String.class));
    }
}
