package com.example.myblog.repository;


import com.example.myblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    // 단 건 조회
Optional<User> findByUsername(String username);
}
