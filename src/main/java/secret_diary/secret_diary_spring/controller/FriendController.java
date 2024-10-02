package secret_diary.secret_diary_spring.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import secret_diary.secret_diary_spring.DI.dto.Friend.FriendDTO;
import secret_diary.secret_diary_spring.DI.dto.Friend.FriendRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.User.RUserRequestDTO;
import secret_diary.secret_diary_spring.DI.entity.FriendRequest;
import secret_diary.secret_diary_spring.service.FriendService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FriendController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FriendService friendService;

    //친구 요청 보내기
    @PostMapping("friend/request/{userEmail}/{friendEmail}")
    public String sendFriendRequest(@PathVariable("userEmail") String userEmail, @PathVariable("friendEmail") String friendEmail) throws Exception{

        if(!friendService.isExistByEmail(userEmail)){
            throw new Exception("not exist my user email");
        }

        if(!friendService.isExistByEmail(friendEmail)){
            throw new Exception("not exist friend email");
        }

        friendService.getSendFriendRequest(userEmail, friendEmail);
        return "request success";
    }

    //친구 요청 목록 보기
    @GetMapping("friend/request/list/{userEmail}")
    public List<FriendRequestDTO> readAllRequest(@PathVariable("userEmail") String userEmail){
        return friendService.getReadAllRequest(userEmail);
    }

    // 친구 요청 수락하기
    @PostMapping("friend/accept/{userEmail}/{friendEmail}")
    public String acceptFriendRequest(@PathVariable("userEmail") String userEmail, @PathVariable("friendEmail") String friendEmail){
        friendService.getAcceptFriendRequest(userEmail, friendEmail);
        //friendService.getAcceptFriendRequest(friendEmail, userEmail);
        friendService.deleteRequest(userEmail, friendEmail);
        return "accept success";
    }

    //내 친구 보기
    @GetMapping("friend/my/{userEmail}")
    public List<FriendDTO> readMyFriend(@PathVariable("userEmail") String userEmail){
        return friendService.getReadMyFriend(userEmail);
    }


    //내 친구 검색
    @GetMapping("friend/my/search/{userEmail}/{friendEmail}")
    public List<FriendDTO> searchMyFriend(@PathVariable("userEmail") String userEmail, @PathVariable("friendEmail") String friendEmail){
        return friendService.getSearchMyFriend(userEmail, friendEmail);
    }

    //친구 검증
    @GetMapping("friend/check/{userEmail}/{friendEmail}")
    public boolean checkFriendExist(@PathVariable("userEmail") String userEmail, @PathVariable("friendEmail") String friendEmail){
        return friendService.isExistMyFriend(userEmail, friendEmail);
    }

    @GetMapping("friend/request/check/{userEmail}/{friendEmail}")
    public boolean checkFriendRequestExist(@PathVariable("userEmail") String userEmail, @PathVariable("friendEmail") String friendEmail){
        return friendService.isExistMyFriendRequest(userEmail, friendEmail);
    }
}
