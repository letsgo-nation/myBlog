package com.example.myblog.service;

import com.example.myblog.dto.LoginRequestDto;
import com.example.myblog.dto.SignupRequestDto;
import com.example.myblog.entity.User;
import com.example.myblog.jwt.JwtUtil;
import com.example.myblog.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j(topic = "User Service")
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public void signup(SignupRequestDto requestDto) {
        // 1. 입력받은 닉네임과 비밀번호, 확인 비밀번호를 저장합니다.
        String inputUsername = requestDto.getUsername();
        String inputPassword = requestDto.getPassword();
        String inputCheckPassword = requestDto.getCheckPassword();

        // 닉네임 중복 확인
        Optional<User> checkUser = userRepository.findByUsername(inputUsername);
        if (checkUser.isPresent()) {
            log.error(inputUsername + "와 중복된 사용자가 존재합니다.");
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        // 비밀번호와 확인 비밀번호가 일치하는지 확인
        if (!inputPassword.equals(inputCheckPassword)) {
            log.info(inputCheckPassword + " 비밀번호가 일치하지 않습니다.");
            throw new IllegalArgumentException("비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 최소 길이 확인
//        if (inputPassword.length() < 4) {
//            log.info("비밀번호는 최소 4자 이상이어야 합니다.");
//            throw new IllegalArgumentException("비밀번호는 최소 4자 이상이어야 합니다.");
//        }

        // 비밀번호에 닉네임 포함 확인
        if (inputPassword.contains(inputUsername)) {
            log.info("비밀번호에 닉네임과 같은 값이 포함되어 있습니다.");
            throw new IllegalArgumentException("비밀번호에 닉네임과 같은 값이 포함되어 있습니다.");
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(inputPassword);

        // 회원 가입 진행
        User user = new User(requestDto, encodedPassword);
        userRepository.save(user);

        log.info(inputUsername + "님이 회원 가입에 성공하였습니다");
    }

    // 로그인 메서드
    public void login(LoginRequestDto requestDto, HttpServletResponse response) {
        // 클라이언트로 부터 전달 받은 id 와 password 를 가져옵니다.
        String inputName = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자를 확인하고, 비밀번호를 확인합니다.
        Optional<User> checkUser = userRepository.findByUsername(inputName);

        // DB에 없는 사용자인 경우 혹은 비밀번호가 일치하지 않을 경우
        if (!checkUser.isPresent() || !passwordEncoder.matches(password, checkUser.get().getPassword())) {
            // 서버 측에 로그를 찍는 역할을 합니다.
            log.info(requestDto.getUsername());
            log.info(requestDto.getPassword());
            log.error("닉네임 또는 패스워드를 확인해주세요.");
            throw new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가..
        String token = jwtUtil.createToken(requestDto.getUsername());
        log.info("token : " + token);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        // 서버 측에 로그를 찍는 역할을 합니다.
        log.info(inputName + "님이 로그인에 성공하였습니다");
    }

}
