package com.sava.lsaparser.repository;

import com.sava.lsaparser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  User findByUsername(String username);
}
