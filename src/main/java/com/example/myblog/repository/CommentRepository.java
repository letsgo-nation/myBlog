package com.example.myblog.repository;

import com.example.myblog.entity.Comment;
import com.example.myblog.entity.Post;
import com.example.myblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostIdOrderByCreatedAt(Long id);
}

