package com.vit.codechef.courtOfJustice.controller;

import com.vit.codechef.courtOfJustice.entity.UserEntity;
import com.vit.codechef.courtOfJustice.repository.UserRepository;
import com.vit.codechef.courtOfJustice.service.JWTService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Auth controller.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Register string.
     *
     * @param user the user
     * @return the string
     */
    @PostMapping("/signup")
    public String register(@RequestBody UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User Registered Successfully";
    }

    /**
     * Login string.
     *
     * @param request the request
     * @return the string
     */
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        // Since we mapped our User entity to UserDetails in Config, we need to adapt here or just pass details
        // For simplicity, we just need the username for the token
        return jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        java.util.Collections.emptyList())
        );
    }
}

/**
 * The type Auth request.
 */
// Simple DTO for login request
@Data
class AuthRequest {
    private String username;
    private String password;
}
