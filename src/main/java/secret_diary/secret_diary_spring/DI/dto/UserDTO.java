package secret_diary.secret_diary_spring.DI.dto;

import lombok.*;
import secret_diary.secret_diary_spring.DI.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String name;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }
}
