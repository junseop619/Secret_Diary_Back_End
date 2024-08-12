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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import secret_diary.secret_diary_spring.DI.dto.JoinRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.LoginRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.UserDTO;
import secret_diary.secret_diary_spring.DI.entity.User;
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
