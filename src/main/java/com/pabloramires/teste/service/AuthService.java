package com.pabloramires.teste.service;

import com.pabloramires.teste.dto.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private final JwtService jwtService;

    public String authenticate(String username, String password, Role role) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(username,role);
        }

        throw new RuntimeException("Invalid Credentials");
    }
}
