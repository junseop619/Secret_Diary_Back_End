package secret_diary.secret_diary_spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import secret_diary.secret_diary_spring.DI.dto.JoinRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.LoginRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.UserDTO;
import secret_diary.secret_diary_spring.DI.dto.UserRequestDTO;
import secret_diary.secret_diary_spring.service.UserService;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @PostMapping("security/join")
    public String signup(@Validated @RequestBody JoinRequestDTO joinRequestDTO){
        userService.joinUser(joinRequestDTO);
        //return "redirect:/security/login";
        return "success";
    }

    @PutMapping("security/update/{userEmail}")
    public ResponseEntity<String> updateUser(
            @PathVariable @RequestPart("userEmail") String userEmail,
            @RequestPart("userPassword") String userPassword,
            @RequestPart("userNickName") String userNickName,
            @RequestPart("userText") String userText,
            @RequestPart("userImg") MultipartFile file
    ){
        if(file.isEmpty()){
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }
        try {
            String fileName = file.getOriginalFilename();

            UserRequestDTO userJoinRequestDTO = new UserRequestDTO();
            userJoinRequestDTO.setUserEmail(userEmail);
            userJoinRequestDTO.setUserPassword(userPassword);
            userJoinRequestDTO.setUserNickName(userNickName);
            userJoinRequestDTO.setUserText(userText);
            userJoinRequestDTO.setUserImg(file);

            userService.updateUser(userEmail, userJoinRequestDTO);
            return new ResponseEntity<>("File uploaded successfully: " + fileName, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("security/login")
    public ResponseEntity<String> login(@Validated @RequestBody LoginRequestDTO loginRequestDTO){
        String token = this.userService.login(loginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @GetMapping("/home/{userId}")
    public UserDTO loginUser(@PathVariable String userId){
        long startTime = System.currentTimeMillis();
        UserDTO userDTO = userService.getUser(userId);

        return userDTO;
    }
}
