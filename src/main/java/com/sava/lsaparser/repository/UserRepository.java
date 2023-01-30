package com.sava.lsaparser.repository;

import com.sava.lsaparser.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    /**
     * Retrieves a user by email
     * @param email has to be unique
     * @return an {@link Optional} of a {@link UserEntity}
     */
    Optional<UserEntity> findByEmail(String email);
}
