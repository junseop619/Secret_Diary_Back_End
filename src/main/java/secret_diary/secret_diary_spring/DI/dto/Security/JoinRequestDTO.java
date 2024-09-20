package secret_diary.secret_diary_spring.DI.dto.Security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDTO {
    private String email;
    private String password;
    private String name;
}
