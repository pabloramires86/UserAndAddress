package com.pabloramires.teste.service;

import com.pabloramires.teste.dto.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    private String username;
    private String password;
    private Role role;

    @BeforeEach
    void setUp() {
        username = "nome";
        password = "teste123";
        role = Role.ROLE_ADMIN;
    }

    @Test
    void authenticate_ShouldReturnToken_WhenCredentialsAreValid() {
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.lenient().when(jwtService.generateToken(username, role)).thenReturn("fake-jwt-token");

        assertDoesNotThrow(() -> authService.authenticate(username, password, role));
    }

    @Test
    void authenticate_ShouldThrowException_WhenCredentialsAreInvalid() {
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenThrow(new RuntimeException("Invalid Credentials"));

        assertThrows(RuntimeException.class, () -> authService.authenticate(username, password, role));
    }

}