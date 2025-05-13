package com.pabloramires.teste.controller;

import com.pabloramires.teste.dto.LoginRequest;
import com.pabloramires.teste.dto.User;
import com.pabloramires.teste.service.AuthService;
import com.pabloramires.teste.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.authenticate(request.getUsername(), request.getPassword(),request.getRole());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String getUserData() {
        return "Dados do usuário!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminData() {
        return "Dados do Administrador!";
    }

    @GetMapping("/user")
    public Page<User> getAllUsers(Pageable pageable) {
        return userService.findAll(pageable);
    }
}
