package com.example.myblog.controller;

import com.example.myblog.dto.ApiResponseDto;
import com.example.myblog.dto.RestApiResponseDto;
import com.example.myblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    // 전체 게시글 조회
    @GetMapping("/posts")
    public ResponseEntity<RestApiResponseDto> getPostList() {
        return postService.getPostList();
    }

//    @PostMapping("/post")
}
