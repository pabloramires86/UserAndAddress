package com.pabloramires.teste.dto;

import com.pabloramires.teste.dto.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

    private Role role;
    private String username;
    private String password;

}
