package com.example.myblog.dto;

import com.example.myblog.entity.Comment;
import com.example.myblog.entity.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentResponseDtoList;
    private Long user_id;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUser().getUsername();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.user_id = post.getUser().getId();
        if(post.getCommentList().size()>0) {
            this.commentResponseDtoList = new ArrayList<>();
            for (Comment comment : post.getCommentList()) {
                this.commentResponseDtoList.add(new CommentResponseDto(comment));
            }
        }// end of the if()
    }

//    public void setCommentResponseDtoList(List<Comment> sortedCommentList) {
//        for (Comment comment : sortedCommentList) {
//            log.info(comment.getContent());
//            this.commentResponseDtoList.add(new CommentResponseDto(comment));
//        }
//    }

}
