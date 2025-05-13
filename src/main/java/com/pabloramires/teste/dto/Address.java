package com.pabloramires.teste.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private String street;//Logradouro
    private Integer number;
    private String complement;
    private String neighborhood;//Bairro
    private String city;
    private String state;
    private String cep;
    private User user;
}
