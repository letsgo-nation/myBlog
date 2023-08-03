package com.example.myblog.service;

import com.example.myblog.dto.PostListResponseDto;
import com.example.myblog.dto.PostRequestDto;
import com.example.myblog.dto.PostResponseDto;
import com.example.myblog.dto.RestApiResponseDto;
import com.example.myblog.entity.Post;
import com.example.myblog.entity.User;
import com.example.myblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
//    private final CommentRepository commentRepository;
//    private final OpinionARepository opinionARepository;
//    private final OpinionBRepository opinionBRepository;
//    private final SecondCommentServiceRepository secondCommentServiceRepository;
    public ResponseEntity<RestApiResponseDto> getPostList() {
        List<Post> postList = postRepository.findAll();
        List<PostResponseDto> postResponseDtoList = postList.stream()
                .map(PostResponseDto::new)
                .toList();
        return this.resultResponse(HttpStatus.OK,"게시글 전체 조회",postResponseDtoList);
    }

    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto);
        post.setUser(user);

        postRepository.save(post);

        return new PostResponseDto(post);
    }

    public PostListResponseDto getPosts() {
        List<PostResponseDto> postList = postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        return new PostListResponseDto(postList);
    }

    public PostResponseDto getPostById(Long id) {
        Post post = findPost(id);

        return new PostResponseDto(post);
    }

    public void deletePost(Long id, User user) {
        Post post = findPost(id);

        // 게시글 작성자인지
        Long writerId = post.getUser().getId();
        Long loginId = user.getId();
        // 작성자가 아니고 관리자도 아님 경우 -> true && true
        // 작성자는 아니지만 관리자일 경우 -> 수정 가능. true && false
        if(!writerId.equals(loginId)){
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        Post post = findPost(id);

        // 게시글 작성자인지
        Long writerId = post.getUser().getId(); // 게시글 작성자 id
        Long loginId = user.getId(); // 현재 로그인한 id
        // 게시글 작성자가 아닐 경우
        if(!writerId.equals(loginId)){
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");
        }

        post.update(requestDto);
        return new PostResponseDto(post);
    }

    public Post findPost(long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }

    private ResponseEntity<RestApiResponseDto> resultResponse(HttpStatus status, String message, Object result) {
        RestApiResponseDto restApiResponseDto = new RestApiResponseDto(status.value(), message, result);
        return new ResponseEntity<>(
                restApiResponseDto,
                status
        );
    }

}
