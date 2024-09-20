package secret_diary.secret_diary_spring.DI.dto.User;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserRequestDTO {
    private String userEmail;
    private String userPassword;
    private String userNickName;
    private String userText;
    private MultipartFile userImg;
}
