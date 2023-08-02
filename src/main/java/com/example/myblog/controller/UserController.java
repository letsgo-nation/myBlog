package com.example.myblog.controller;

import com.example.myblog.dto.ApiResponseDto;
import com.example.myblog.dto.SignupRequestDto;
import com.example.myblog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        // @Valid를 통해 받아오는 테이터의 제한을 걸어둠
        try {
            userService.signup(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("중복된 닉네임입니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(200).body(new ApiResponseDto("회원가입에 성공하셨습니다.", HttpStatus.CREATED.value()));
    }

    // 로그인 API는 JWtAuthenticationFilter
}
