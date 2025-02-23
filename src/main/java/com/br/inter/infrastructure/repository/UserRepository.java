package com.br.inter.infrastructure.repository;

import com.br.inter.infrastructure.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByDocument(String document);
    boolean existsByEmail(String email);
}
