package com.pabloramires.teste.repository;

import com.pabloramires.teste.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Page<UserEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<UserEntity> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    Page<UserEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Optional<UserEntity> findByUsername(String username);

}
