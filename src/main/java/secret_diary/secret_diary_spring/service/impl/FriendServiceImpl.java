package secret_diary.secret_diary_spring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import secret_diary.secret_diary_spring.DI.dto.Friend.FriendDTO;
import secret_diary.secret_diary_spring.DI.dto.Friend.FriendRequestDTO;
import secret_diary.secret_diary_spring.DI.entity.Friend;
import secret_diary.secret_diary_spring.DI.entity.FriendRequest;
import secret_diary.secret_diary_spring.DI.repository.FriendRepository;
import secret_diary.secret_diary_spring.DI.repository.FriendRequestRepository;
import secret_diary.secret_diary_spring.DI.repository.UserRepository;
import secret_diary.secret_diary_spring.service.FriendService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FriendServiceImpl implements FriendService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Autowired
    FriendRepository friendRepository;

    @Override
    public String getSendFriendRequest(String userEmail, String friendEmail){
        return friendRequestRepository.save(FriendRequest.builder()
                .userEmail(userEmail)
                .friendEmail(friendEmail)
                .build()).getUserEmail();
    }

    @Override
    public Boolean isExistByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<FriendRequestDTO> getReadAllRequest(String email){
        List<FriendRequest> entities = friendRequestRepository.findByFriendEmail(email);
        List<FriendRequestDTO> dtos = new ArrayList<>();

        for(FriendRequest entity : entities){
            FriendRequestDTO friendRequestDTO = new FriendRequestDTO();

            friendRequestDTO.setUserEmail(entity.getUserEmail());
            friendRequestDTO.setFriendEmail(entity.getFriendEmail());
            dtos.add(friendRequestDTO);
        }
        return dtos;
    }

    @Override
    public String getAcceptFriendRequest(String userEmail, String friendEmail){
        return friendRepository.save(Friend.builder()
                .userEmail(userEmail)
                .friendEmail(friendEmail)
                .build()).getUserEmail();
    }

    @Override
    @Transactional
    public void deleteRequest(String userEmail, String friendEmail){
        friendRequestRepository.deleteByUserEmailAndFriendEmail(friendEmail, userEmail);
    }

    @Override
    public List<FriendDTO> getReadMyFriend(String userEmail){
        List<Friend> entities = friendRepository.findByUserEmail(userEmail);
        List<FriendDTO> dtos = new ArrayList<>();

        for(Friend entity : entities){
            FriendDTO friendDTO = new FriendDTO();

            friendDTO.setUserEmail(entity.getUserEmail());
            friendDTO.setFriendEmail(entity.getFriendEmail());
            dtos.add(friendDTO);
        }
        return dtos;
    }

    @Override
    public List<FriendDTO> getSearchMyFriend(String userEmail, String friendEmail){
        List<Friend> entities = friendRepository.findByUserEmailAndFriendEmailContaining(userEmail, friendEmail);
        List<FriendDTO> dtos = new ArrayList<>();

        for (Friend entity : entities){
            FriendDTO friendDTO = new FriendDTO();

            friendDTO.setUserEmail(entity.getUserEmail());
            friendDTO.setFriendEmail(entity.getFriendEmail());
            dtos.add(friendDTO);
        }
        return dtos;
    }

    @Override
    public Boolean isExistMyFriend(String userEmail, String friendEmail){
        Optional<Friend> friend = friendRepository.findByUserEmailAndFriendEmail(userEmail, friendEmail);
        return friend.isPresent();
    }

    @Override
    public Boolean isExistMyFriendRequest(String userEmail, String friendEmail){
        Optional<FriendRequest> request = friendRequestRepository.findByUserEmailAndFriendEmail(userEmail, friendEmail);
        return request.isPresent();
    }
}
