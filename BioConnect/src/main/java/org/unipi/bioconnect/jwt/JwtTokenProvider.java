package org.unipi.bioconnect.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Claim;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.validity-in-milliseconds}")
    private long validityInMilliseconds;

    private Algorithm algorithm; // Sign algorithm for the token

    @PostConstruct
    protected void init() {
        algorithm = Algorithm.HMAC256(secretKey);
    }

    public String createToken(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .sign(algorithm);
    }

    // Method to validate the JWT token
    public boolean validateToken(String token) {
        try {
            JWT.require(algorithm)
                    .build()
                    .verify(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static String resolveToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);  // Remove "Bearer " from the header
        }
        return null;  // If no token is present
    }


    public UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))  // Ensure the same algorithm is used
                .build()
                .verify(token);

        String username = decodedJWT.getSubject();
        Claim roleClaim = decodedJWT.getClaim("role");
        String role = roleClaim.asString();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(authority)  // returns an immutable list with a fixed size of 1
        );

    }
}
