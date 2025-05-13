package com.pabloramires.teste.service;

import com.pabloramires.teste.dto.Address;
import com.pabloramires.teste.dto.CepResponse;
import com.pabloramires.teste.dto.User;
import com.pabloramires.teste.dto.enums.Role;
import com.pabloramires.teste.dto.mapper.Mapper;
import com.pabloramires.teste.entity.UserEntity;
import com.pabloramires.teste.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Mapper mapper;

    @Mock
    private CepService cepService;

    private UserEntity userEntity;
    private User user;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity(1L, "Pablo", "pablo@example.com", "pablo", "encodedPassword", Role.ROLE_USER, new ArrayList<>(), LocalDateTime.now());
        user = new User("Pablo", "pablo@example.com", "pablo", "teste123", Role.ROLE_USER, new ArrayList<>());
    }

    @Test
    void findById_ShouldReturnUser() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        Mockito.when(mapper.entityToUser(userEntity)).thenReturn(user);

        User result = userService.findById(1L);

        assertNotNull(result);
    }

    @Test
    void findByName_ShouldReturnPageOfUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserEntity> userEntityPage = new PageImpl<>(List.of(userEntity));
        Page<User> userPage = new PageImpl<>(List.of(user));

        Mockito.when(userRepository.findByNameContainingIgnoreCase("Pablo", pageable)).thenReturn(userEntityPage);
        Mockito.when(mapper.entityToUser(userEntity)).thenReturn(user);

        Page<User> result = userService.findByName("Pablo", pageable);

        assertNotNull(result);
    }

    @Test
    void findByEmail_ShouldReturnPageOfUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserEntity> userEntityPage = new PageImpl<>(List.of(userEntity));

        Mockito.when(userRepository.findByEmailContainingIgnoreCase("pablo@example.com", pageable)).thenReturn(userEntityPage);
        Mockito.when(mapper.entityToUser(userEntity)).thenReturn(user);

        Page<User> result = userService.findByEmail("pablo@example.com", pageable);

        assertNotNull(result);
    }

    @Test
    void findByDateRange_ShouldReturnPageOfUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();

        Page<UserEntity> userEntityPage = new PageImpl<>(List.of(userEntity));

        Mockito.when(userRepository.findByCreatedAtBetween(start, end, pageable)).thenReturn(userEntityPage);
        Mockito.when(mapper.entityToUser(userEntity)).thenReturn(user);

        Page<User> result = userService.findByDateRange(start, end, pageable);

        assertNotNull(result);
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserEntity> userEntityPage = new PageImpl<>(List.of(userEntity));

        Mockito.when(userRepository.findAll(pageable)).thenReturn(userEntityPage);
        Mockito.when(mapper.entityToUser(userEntity)).thenReturn(user);

        Page<User> result = userService.findAll(pageable);

        assertNotNull(result);
    }

    @Test
    void save_ShouldSaveUser() {
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");
        Mockito.when(mapper.userToEntity(user)).thenReturn(userEntity);
        Mockito.when(mapper.entityToUser(userEntity)).thenReturn(user);
        Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);

        User result = userService.save(user);

        assertNotNull(result);
    }

    @Test
    void callCep_ShouldValidateCepSuccessfully() {
        User user = new User("Pablo", "pablo@example.com", "pablo", "teste123", Role.ROLE_USER, new ArrayList<>());
        Address address = new Address("Rua Cirino de Abreu", 123, "", "Guaiaúna", "São Paulo", "SP", "03630-010", user);
        user.setAddressList(List.of(address));

        CepResponse cepResponse = new CepResponse();
        cepResponse.setCep("03630-010");
        cepResponse.setLogradouro("Rua Cirino de Abreu");
        cepResponse.setBairro("Guaiaúna");
        cepResponse.setEstado("São Paulo");
        cepResponse.setUf("SP");

        Mockito.when(cepService.getCepInfo(address.getCep())).thenReturn(cepResponse);
        Mockito.when(cepService.validateAddress(address, cepResponse)).thenReturn(true);

        assertDoesNotThrow(() -> userService.callCep(user));
    }

    @Test
    void delete_ShouldDeleteUser() {
        userService.delete(1L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }
}
