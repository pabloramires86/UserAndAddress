package com.pabloramires.teste.dto.mapper;

import com.pabloramires.teste.dto.Address;
import com.pabloramires.teste.dto.User;
import com.pabloramires.teste.entity.AddressEntity;
import com.pabloramires.teste.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public UserEntity userToEntity(User user) {
        UserEntity entity = UserEntity.builder()
                .name(Objects.nonNull(user.getName()) ? user.getName() : "")
                .email(Objects.nonNull(user.getEmail()) ? user.getEmail() : "")
                .username(Objects.nonNull(user.getUsername()) ? user.getUsername() : "")
                .password(Objects.nonNull(user.getPassword()) ? user.getPassword() : "")
                .role(Objects.nonNull(user.getRole()) ? user.getRole() : null)
                .build();

        if (Objects.nonNull(user.getAddressList())) {
            List<AddressEntity> addressEntities = user.getAddressList().stream()
                    .map(address -> {
                        AddressEntity addressEntity = addressToEntity(address);
                        addressEntity.setUser(entity);
                        return addressEntity;
                    }).collect(Collectors.toList());

            entity.setAddresses(addressEntities);
        }

        return entity;
    }

    public User entityToUser(UserEntity userEntity) {
        User user = User.builder()
                .name(Objects.nonNull(userEntity.getName()) ? userEntity.getName() : "")
                .email(Objects.nonNull(userEntity.getEmail()) ? userEntity.getEmail() : "")
                .username(Objects.nonNull(userEntity.getUsername()) ? userEntity.getUsername() : "")
                .password(Objects.nonNull(userEntity.getPassword()) ? userEntity.getPassword() : "")
                .role(Objects.nonNull(userEntity.getRole()) ? userEntity.getRole() : null)
                .build();

        if (Objects.nonNull(userEntity.getAddresses())) {
            List<Address> addresses = userEntity.getAddresses().stream()
                    .map(addressEntity -> {
                        Address address = entityToAddress(addressEntity);
                        address.setUser(new User());
                        return address;
                    }).collect(Collectors.toList());

            user.setAddressList(addresses);
        }

        return user;
    }

    public AddressEntity addressToEntity(Address address) {
        AddressEntity addressEntity = AddressEntity.builder()
                .street(Objects.nonNull(address.getStreet()) ? address.getStreet() : null)
                .number(Objects.nonNull(address.getNumber()) ? address.getNumber() : null)
                .complement(Objects.nonNull(address.getComplement()) ? address.getComplement() : null)
                .neighborhood(Objects.nonNull(address.getNeighborhood()) ? address.getNeighborhood() : null)
                .city(Objects.nonNull(address.getCity()) ? address.getCity() : null)
                .state(Objects.nonNull(address.getState()) ? address.getState() : null)
                .cep(Objects.nonNull(address.getCep()) ? address.getCep() : null)
                .build();

        if (Objects.nonNull(address.getUser())) {
            addressEntity.setUser(new UserEntity());
        }

        return addressEntity;
    }

    public Address entityToAddress(AddressEntity addressEntity) {
        Address address = Address.builder()
                .street(Objects.nonNull(addressEntity.getStreet()) ? addressEntity.getStreet() : null)
                .number(Objects.nonNull(addressEntity.getNumber()) ? addressEntity.getNumber() : null)
                .complement(Objects.nonNull(addressEntity.getComplement()) ? addressEntity.getComplement() : null)
                .neighborhood(Objects.nonNull(addressEntity.getNeighborhood()) ? addressEntity.getNeighborhood() : null)
                .city(Objects.nonNull(addressEntity.getCity()) ? addressEntity.getCity() : null)
                .state(Objects.nonNull(addressEntity.getState()) ? addressEntity.getState() : null)
                .cep(Objects.nonNull(addressEntity.getCep()) ? addressEntity.getCep() : null)
                .build();

        if (Objects.nonNull(addressEntity.getUser())) {
            address.setUser(new User());
        }

        return address;
    }
}
