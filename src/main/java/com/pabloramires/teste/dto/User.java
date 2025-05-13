package com.pabloramires.teste.dto;

import com.pabloramires.teste.dto.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String name;
    private String email;
    private String username;
    private String password;
    private Role role;
    private List<Address> addressList;
}
