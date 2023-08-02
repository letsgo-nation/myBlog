package com.example.myblog.service;

import com.example.myblog.dto.SignupRequestDto;
import com.example.myblog.entity.User;
import com.example.myblog.repository.UserRepository;
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

//    public void signup(SignupRequestDto requestDto) {
//        // 1. 입력받은 id와 password 를 저장합니다.
//        //    password 는 암호화가 이뤄집니다.
//        String inputUsername = requestDto.getUsername();
//        String inputCheckpassword = requestDto.getCheckPassword();
//
//        // 비밀번호와 확인 비밀번호가 일치하는지 확인
//        if (!requestDto.getPassword().equals(requestDto.getCheckPassword())) {
//            log.info(inputCheckpassword + " 비밀번호가 일치하지 않습니다.");
//            throw new IllegalArgumentException("비밀번호와 확인 비밀번호가 일치하지 않습니다.");
//        }
//        String password = passwordEncoder.encode(requestDto.getPassword());
//
//        // 2. user 테이블에 입력받은 id와 동일한 데이터가 있는지 확인합니다.
//        Optional<User> checkUser = userRepository.findByUsername(inputUsername);
//
//        // 2-1. 중복 회원이 있을 경우
//        if (checkUser.isPresent()) {
//            // 서버 측에 로그를 찍는 역할을 합니다.
//            log.error(inputUsername + "와 중복된 사용자가 존재합니다.");
//            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
//        }
//
////    3. 회원 가입 진행
//    User user = new User(requestDto, password);
//    userRepository.save(user);
//
//        log.info(inputUsername + "님이 회원 가입에 성공하였습니다");


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
}
