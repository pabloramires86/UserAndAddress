package com.pabloramires.teste.service;

import com.pabloramires.teste.dto.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
    @InjectMocks
    private JwtService jwtService;

    @Mock
    private Environment env;

    private String secretKeyBase64;

    @BeforeEach
    void setUp() {
        String rawSecretKey = "minha-chave-secreta-super-segura";
        secretKeyBase64 = Base64.getEncoder().encodeToString(rawSecretKey.getBytes());

        ReflectionTestUtils.setField(jwtService, "secretKey", secretKeyBase64);
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        String username = "pablo";
        Role role = Role.ROLE_ADMIN;

        String token = jwtService.generateToken(username, role);

        assertNotNull(token);
    }

    @Test
    void generateToken_ShouldContainExpectedClaims() {
        String username = "pablo";
        Role role = Role.ROLE_ADMIN;

        String token = jwtService.generateToken(username, role);

        Claims claims = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyBase64)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals(username, claims.getSubject());
    }

    @Test
    void getSecretKey_ShouldReturnSecretKey() {
        assertEquals(secretKeyBase64, jwtService.getSecretKey());
    }

}
