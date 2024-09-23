package secret_diary.secret_diary_spring.service;

import secret_diary.secret_diary_spring.DI.dto.Security.JoinRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.Security.LoginRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.User.RUserRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.User.UserDTO;
import secret_diary.secret_diary_spring.DI.dto.User.UserRequestDTO;

import java.util.List;

public interface UserService {

    Long joinUser(JoinRequestDTO dto);

    Long updateUser(String userEmail, UserRequestDTO dto);

    RUserRequestDTO getUserInfo(String userEmail);

    String login(LoginRequestDTO loginRequestDTO);

    UserDTO getUser(String userId);

    //RUserRequestDTO getSearchUser(String keyword);

    List<RUserRequestDTO> getSearchUser2(String keyword, String userEmail);

    void deleteUserByEmail(String userEmail);
}
