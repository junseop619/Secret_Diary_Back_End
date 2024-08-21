package secret_diary.secret_diary_spring.service;

import secret_diary.secret_diary_spring.DI.dto.*;

public interface UserService {

    Long joinUser(JoinRequestDTO dto);

    Long updateUser(String userEmail, UserRequestDTO dto);

    RUserRequestDTO getUserInfo(String userEmail);

    String login(LoginRequestDTO loginRequestDTO);

    UserDTO getUser(String userId);
}
