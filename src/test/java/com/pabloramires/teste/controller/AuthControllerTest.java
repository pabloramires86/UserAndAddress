package com.pabloramires.teste.controller;

import com.pabloramires.teste.dto.LoginRequest;
import com.pabloramires.teste.dto.User;
import com.pabloramires.teste.dto.enums.Role;
import com.pabloramires.teste.service.AuthService;
import com.pabloramires.teste.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest(Role.ROLE_ADMIN, "teste123", "nome");
    }

    @Test
    void login_ShouldReturnToken() {
        Mockito.when(authService.authenticate(Mockito.anyString(), Mockito.anyString(), Mockito.any(Role.class)))
                .thenReturn("fake-jwt-token");

        String result = authController.login(loginRequest);

        assertNotNull(result);
    }

    @Test
    void getUserData_ShouldReturnUserData() {
        String result = authController.getUserData();

        assertEquals("Dados do usuário!", result);
    }

    @Test
    void getAdminData_ShouldReturnAdminData() {
        String result = authController.getAdminData();

        assertEquals("Dados do Administrador!", result);
    }

    @Test
    void getAllUsers_ShouldReturnPageOfUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(new User("Pablo", "pablo@example.com", "pablo", "teste123", Role.ROLE_USER, new ArrayList<>())));

        Mockito.when(userService.findAll(pageable)).thenReturn(userPage);

        Page<User> result = authController.getAllUsers(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

}