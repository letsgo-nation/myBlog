package com.example.myblog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$",
            message = "최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.")
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String username; // == nickname

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+{}:\"<>?,.\\\\/]{4,}$",
            message = "최소 4자 이상이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자 로 구성되어야 합니다.")
    @NotBlank(message = "비밀번호 입력이 안되었습니다.")
    private String password;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String checkPassword;

}
