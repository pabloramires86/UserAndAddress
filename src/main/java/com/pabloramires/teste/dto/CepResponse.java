package com.pabloramires.teste.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CepResponse {
    private String cep;
    private String logradouro;
    private String bairro;
    private String estado;
    private String uf;
}

