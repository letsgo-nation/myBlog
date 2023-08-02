package com.example.myblog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.bridge.IMessage;

@Getter
@Setter
public class SignupRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$",
            message = "최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @Pattern(regexp = "^{4,}$",
            message = "최소 4자 이상으로 구성되어야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String checkPassword;

}
