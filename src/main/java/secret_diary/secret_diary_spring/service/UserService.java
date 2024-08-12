package secret_diary.secret_diary_spring.service;

import secret_diary.secret_diary_spring.DI.dto.JoinRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.LoginRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.UserDTO;

public interface UserService {

    Long joinUser(JoinRequestDTO dto);

    String login(LoginRequestDTO loginRequestDTO);

    UserDTO getUser(String userId);
}
