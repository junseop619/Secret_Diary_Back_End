package secret_diary.secret_diary_spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import secret_diary.secret_diary_spring.DI.dto.Security.JoinRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.Security.LoginRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.User.RUserRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.User.UserDTO;
import secret_diary.secret_diary_spring.DI.dto.User.UserRequestDTO;
import secret_diary.secret_diary_spring.security.jwt.JwtUtil;
import secret_diary.secret_diary_spring.service.UserService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtUtil jwtUtil;

    @Value("${upload.path}")
    String uploadPath;

    @PostMapping("security/join")
    public String signup(@Validated @RequestBody JoinRequestDTO joinRequestDTO){
        userService.joinUser(joinRequestDTO);
        //return "redirect:/security/login";
        return "success";
    }

    @PutMapping("security/update/{userEmail}")
    public ResponseEntity<String> updateUser(
            @PathVariable("userEmail") String userEmail,
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

    @PostMapping("security/autoLogin")
    public ResponseEntity<Void> autoLogin(@RequestHeader("Authorization") String token){
        try{
            // JWT 토큰에서 "Bearer " 부분 제거
            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

            // 토큰 검증
            if (!jwtUtil.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            return ResponseEntity.ok().build(); // 성공 시 200 OK 반환
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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

    /*
    @GetMapping("security/user/{userEmail}")
    public RUserRequestDTO myInfo(@PathVariable("userEmail") String userEmail){
        logger.info("load user: email = " + userEmail);
        return userService.getUserInfo(userEmail);
    }*/

    @GetMapping("security/user/{userEmail}")
    public ResponseEntity<RUserRequestDTO> myInfo(@PathVariable("userEmail") String userEmail) {
        if (userEmail == null || userEmail.isEmpty()) {
            logger.info("load user: request = " + ResponseEntity.badRequest().build());
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

        try {
            RUserRequestDTO userInfo = userService.getUserInfo(userEmail);

            if (userInfo != null) {
                logger.info("load user: request = " + ResponseEntity.ok(userInfo));
                return ResponseEntity.ok(userInfo); // 200 OK
            } else {
                logger.info("load user: request = " + ResponseEntity.notFound().build());
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
        } catch (Exception e) {
            // 로깅 및 에러 처리
            logger.info("load user: request = " + ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }


    @GetMapping("/user/image/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable("filename") String filename) {
        try {
            Path filePath = Paths.get(uploadPath).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            logger.debug("Requested filename: " + filename);
            logger.debug("Resolved file path: " + filePath.toString());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Could not read the file!");
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            logger.error("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IOException e) {
            logger.error("Could not determine file type.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //유저 검색
    /*
    @GetMapping("search/user")
    public ResponseEntity<RUserRequestDTO> searchUser(@RequestParam("keyword") String keyword){
        try {
            RUserRequestDTO userInfo = userService.getSearchUser(keyword);
            logger.info("load user: request = " + ResponseEntity.ok(userInfo));
            return ResponseEntity.ok(userInfo);
        } catch (Exception e){
            logger.info("load user: request = " + ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/


    @GetMapping("search/{keyword}/{userEmail}")
    public List<RUserRequestDTO> searchUser2(@PathVariable("keyword") String keyword, @PathVariable("userEmail") String userEmail){
        return userService.getSearchUser2(keyword, userEmail);
    }
}
