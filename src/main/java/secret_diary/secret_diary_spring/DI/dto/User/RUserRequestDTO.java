package secret_diary.secret_diary_spring.DI.dto.User;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RUserRequestDTO {
    private Long userId;
    private String userEmail;
    private String userNickName;
    private String userText;
    private String userImgPath;
}
