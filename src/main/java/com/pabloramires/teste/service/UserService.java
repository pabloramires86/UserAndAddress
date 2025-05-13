package com.pabloramires.teste.service;

import com.pabloramires.teste.dto.CepResponse;
import com.pabloramires.teste.dto.User;
import com.pabloramires.teste.dto.mapper.Mapper;
import com.pabloramires.teste.entity.UserEntity;
import com.pabloramires.teste.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Mapper mapper;
    @Autowired
    private CepService cepService;

    public Page<User> findByName(String name, Pageable pageable) {
        return userRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(mapper::entityToUser);
    }

    public Page<User> findByEmail(String email, Pageable pageable) {
        return userRepository.findByEmailContainingIgnoreCase(email, pageable)
                .map(mapper::entityToUser);
    }

    public Page<User> findByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return userRepository.findByCreatedAtBetween(start, end, pageable)
                .map(mapper::entityToUser);
    }


    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(mapper::entityToUser);
    }


    public User findById(Long id) {
        UserEntity entity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not Found!"));
        return mapper.entityToUser(entity);
    }

    public User save(User user) {
        callCep(user);
        UserEntity entity = mapper.userToEntity(user);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setCreatedAt(LocalDateTime.now());
        entity.getAddresses().forEach(address -> address.setUser(entity));
        userRepository.save(entity);
        return mapper.entityToUser(entity);
    }


    public User update(Long id, User updatedUser) {
        UserEntity updatedEntity = mapper.userToEntity(updatedUser);
        UserEntity entity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        entity.setName(updatedEntity.getName());
        entity.setEmail(updatedEntity.getEmail());
        userRepository.save(entity);
        return mapper.entityToUser(entity);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    void callCep(User user) {
        user.getAddressList().forEach(address -> {
            CepResponse cepResponse = cepService.getCepInfo(address.getCep());

            if (!cepService.validateAddress(address, cepResponse)) {
                throw new IllegalArgumentException("The address doesn't correspond with the given CEP");
            }
        });
    }
}
