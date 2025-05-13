package com.pabloramires.teste.service;

import com.pabloramires.teste.dto.Address;
import com.pabloramires.teste.dto.CepResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CepServiceTest {

    @InjectMocks
    private CepService cepService;

    @Mock
    private RestTemplate restTemplate;

    private CepResponse validCepResponse;
    private Address validAddress;

    @BeforeEach
    void setUp() {
        validCepResponse = new CepResponse();
        validCepResponse.setCep("00000-000");
        validCepResponse.setLogradouro("Rua Exemplo");
        validCepResponse.setBairro("Bairro");
        validCepResponse.setEstado("São Paulo");
        validCepResponse.setUf("SP");

        validAddress = new Address("Rua Exemplo", 123, "", "Bairro", "SP", "São Paulo", "00000-000", null);
    }

    @Test
    void getCepInfo_ShouldReturnValidCepResponse() {
        String cep = "03630-010";
        String url = String.format("https://viacep.com.br/ws/%s/json/", cep);

        Mockito.lenient().when(restTemplate.getForObject(url, CepResponse.class)).thenReturn(validCepResponse);
        CepResponse result = cepService.getCepInfo(cep);

        assertNotNull(result);
    }

    @Test
    void validateCep_ShouldThrowException_WhenCepIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> cepService.getCepInfo("12345678"));
        assertThrows(IllegalArgumentException.class, () -> cepService.getCepInfo("03630-ABC"));
    }

    @Test
    void validateAddress_ShouldReturnTrue_WhenAddressMatchesCepResponse() {
        boolean isValid = cepService.validateAddress(validAddress, validCepResponse);
        assertTrue(isValid);
    }

    @Test
    void validateAddress_ShouldReturnFalse_WhenAddressDoesNotMatchCepResponse() {
        Address wrongAddress = new Address("Rua Errada", 123, "", "Bairro X", "Cidade Y", "SP", "99999-999", null);

        boolean isValid = cepService.validateAddress(wrongAddress, validCepResponse);
        assertFalse(isValid);
    }

}