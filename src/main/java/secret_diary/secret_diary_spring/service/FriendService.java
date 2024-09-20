package secret_diary.secret_diary_spring.service;

import org.springframework.web.bind.annotation.PathVariable;
import secret_diary.secret_diary_spring.DI.dto.Friend.FriendDTO;
import secret_diary.secret_diary_spring.DI.dto.Friend.FriendRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.User.RUserRequestDTO;
import secret_diary.secret_diary_spring.DI.entity.FriendRequest;

import java.util.List;

public interface FriendService {

    String getSendFriendRequest(String userEmail, String friendEmail);

    Boolean isExistByEmail(String email);

    List<FriendRequestDTO> getReadAllRequest(String email);

    String getAcceptFriendRequest(String userEmail, String friendEmail);

    List<FriendDTO> getReadMyFriend(String userEmail);

    List<FriendDTO> getSearchMyFriend(String userEmail, String friendEmail);

    Boolean isExistMyFriend(String userEmail, String friendEmail);

    Boolean isExistMyFriendRequest(String userEmail, String friendEmail);

    void deleteRequest(String userEmail, String friendEmail);
}
