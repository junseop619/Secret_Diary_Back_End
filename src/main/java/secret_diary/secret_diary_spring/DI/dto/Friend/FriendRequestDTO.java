package secret_diary.secret_diary_spring.DI.dto.Friend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendRequestDTO {

    private String userEmail;
    private String friendEmail;
}
