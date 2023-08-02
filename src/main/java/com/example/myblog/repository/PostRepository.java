package com.example.myblog.repository;

import com.example.myblog.entity.Post;
import com.example.myblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
}