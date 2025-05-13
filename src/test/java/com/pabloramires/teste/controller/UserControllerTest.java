package com.pabloramires.teste.controller;

import com.pabloramires.teste.dto.User;
import com.pabloramires.teste.dto.enums.Role;
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
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Pablo", "pablo@example.com", "pablo", "teste123", Role.ROLE_USER, new ArrayList<>());
    }

    @Test
    void getUserById_ShouldReturnUser() {
        Mockito.when(userService.findById(1L)).thenReturn(user);

        ResponseEntity<User> response = userController.getUserById(1L);

        assertNotNull(response);
    }


    @Test
    void deleteUser_ShouldReturnNoContent() {
        Mockito.doNothing().when(userService).delete(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertNotNull(response);
    }

    @Test
    void searchUsers_ShouldReturnPageOfUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(user));

        Mockito.when(userService.findByName("Pablo", pageable)).thenReturn(userPage);

        Page<User> result = userController.searchUsers("Pablo", null, null, null, pageable);

        assertNotNull(result);
    }

}