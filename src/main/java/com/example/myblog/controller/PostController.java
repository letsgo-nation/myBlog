package com.example.myblog.controller;

import com.example.myblog.dto.ApiResponseDto;
import com.example.myblog.dto.PostRequestDto;
import com.example.myblog.dto.PostResponseDto;
import com.example.myblog.dto.RestApiResponseDto;
import com.example.myblog.security.UserDetailsImpl;
import com.example.myblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

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

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto) {
        PostResponseDto result = postService.createPost(requestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(result);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto result = postService.getPostById(id);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody PostRequestDto requestDto) {
            PostResponseDto result = postService.updatePost(id, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResponseDto> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            postService.deletePost(id, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("게시글 삭제 성공", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }
}
