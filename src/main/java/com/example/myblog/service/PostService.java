package com.example.myblog.service;

import com.example.myblog.dto.PostRequestDto;
import com.example.myblog.dto.PostResponseDto;
import com.example.myblog.dto.RestApiResponseDto;
import com.example.myblog.entity.Post;
import com.example.myblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Scanner;

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

//    public PostResponseDto getPost(Long id) {
//        Post post = findPost(id);
//        PostResponseDto responseDto = new PostResponseDto(post);
//        Long data = 8L;
//        responseDto.setCommentResponseDtoList(commentRepository.findAllByPostIdOrderByCreatedAtAsc(id));
//
//        for( CommentResponseDto dto : responseDto.getCommentResponseDtoList()){
//            responseDto.setSecondCommentResponseDtoList(secondCommentServiceRepository.findAllByCommentIdOrderByCreatedAtAsc(dto.getId()));
//        }
//
//        // comment 아이디 필요 댓글에 해당하는 id 값 가져오기
//        return responseDto;
//    }
//
//    public ResponseEntity<RestApiResponseDto> createPost(PostRequestDto requestDto, User user) {
//        Post post = new Post(requestDto,user);
//        postRepository.save(post);
//        return this.resultResponse(HttpStatus.OK,"게시글 작성 완료",new PostResponseDto(post));
//    }
//
//    @Transactional
//    public ResponseEntity<RestApiResponseDto> updatePost(Long id, PostRequestDto requestDto, User user) {
//        // 게시글이 있는지
//        Post post = findPost(id);
//
//        // 게시글 작성자인지
//        Long writerId = post.getUser().getId(); // 게시글 작성자 id
//        Long loginId = user.getId(); // 현재 로그인한 id
//        // 게시글 작성자가 아니고, 관리자도 아닐 경우
//        if(!writerId.equals(loginId) && user.getRole().equals(UserRoleEnum.ADMIN)){
//            throw new IllegalArgumentException("작성자 혹은 관리자만 삭제/수정 할 수 있습니다.");
//        }

        // 게시글 내용 수정
//        post.update(requestDto);
//        return this.resultResponse(HttpStatus.OK,"게시글 수정 완료",new PostResponseDto(post));
//    }

//    public ResponseEntity<RestApiResponseDto> deletePost(Long id, User user) {
//        // 게시글이 있는지
//        Post post = findPost(id);
//        // 게시글 작성자인지
//        Long writerId = post.getUser().getId();
//        Long loginId = user.getId();
//        // 작성자가 아니고 관리자도 아님 경우 -> true && true
//        // 작성자는 아니지만 관리자일 경우 -> 수정 가능. true && false
//        if(!writerId.equals(loginId) && !user.getRole().equals(UserRoleEnum.ADMIN)){
//            throw new IllegalArgumentException("작성자 혹은 관리자만 삭제/수정 할 수 있습니다.");
//        }
//
//        List<Comment> comments = commentRepository.findByPost(post);
//        commentRepository.deleteAll(comments);
//        List<OpinionA> opinionAList = opinionARepository.findByPost(post);
//        opinionARepository.deleteAll(opinionAList);
//        List<OpinionB> opinionBList = opinionBRepository.findByPost(post);
//        opinionBRepository.deleteAll(opinionBList);
//        // 게시글 내용 삭제
//        postRepository.delete(post);
//        return this.resultResponse(HttpStatus.OK,"게시글 삭제 완료",null);
//    }

//    private Post findPost(Long id){
//        return postRepository.findById(id).orElseThrow(() ->
//                new PostNotFoundException("해당 게시글이 존재하지 않습니다."));
//    }

    private ResponseEntity<RestApiResponseDto> resultResponse(HttpStatus status, String message, Object result) {
        RestApiResponseDto restApiResponseDto = new RestApiResponseDto(status.value(), message, result);
        return new ResponseEntity<>(
                restApiResponseDto,
                status
        );
    }
}
