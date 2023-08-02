package entity;

import com.example.myblog.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String  username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    // 왜 쓰는 걸까?
    public User(SignupRequestDto signupRequestDto, String password) {
        this.username = signupRequestDto.getUsername();
        this.nickname = signupRequestDto.getNickname();
        this.password = password; // 왜 이건 getPassword()가 아닌거지?


    }
}
