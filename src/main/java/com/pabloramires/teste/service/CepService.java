package com.pabloramires.teste.service;

import com.pabloramires.teste.dto.Address;
import com.pabloramires.teste.dto.CepResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";

    public CepResponse getCepInfo(String cep) {
        validateCep(cep);
        String url = String.format(VIA_CEP_URL, cep);
        return restTemplate.getForObject(url, CepResponse.class);
    }

    private void validateCep(String cep) {
        if (!cep.matches("^\\d{5}-\\d{3}$")) {
            throw new IllegalArgumentException("Invalid Cep, must contain 9 digits");
        }
    }
    public boolean validateAddress(Address address, CepResponse cepResponse) {
        return address.getStreet().equalsIgnoreCase(cepResponse.getLogradouro()) &&
                address.getNeighborhood().equalsIgnoreCase(cepResponse.getBairro()) &&
                address.getState().equalsIgnoreCase(cepResponse.getEstado()) &&
                address.getCity().equalsIgnoreCase(cepResponse.getUf());
    }
}
