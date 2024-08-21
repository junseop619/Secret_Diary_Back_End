package secret_diary.secret_diary_spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import secret_diary.secret_diary_spring.DI.dto.*;
import secret_diary.secret_diary_spring.DI.entity.Notice;
import secret_diary.secret_diary_spring.DI.entity.User;
import secret_diary.secret_diary_spring.DI.handler.UserDataHandler;
import secret_diary.secret_diary_spring.DI.repository.UserRepository;
import secret_diary.secret_diary_spring.security.jwt.JwtUtil;
import secret_diary.secret_diary_spring.service.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service

public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    @Autowired
    UserDataHandler userDataHandler;

    @Value("${upload.path}")
    String uploadPath;


    public Long joinUser(JoinRequestDTO dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }

    public Long updateUser(String userEmail, UserRequestDTO dto){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        //image
        MultipartFile file = dto.getUserImg();

        if (file == null) {
            logger.info("No file provided.");
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null) {
            logger.info("Original file name is null.");
        }

        String saveFileName = createSaveFileName(originalFileName);

        try {
            //내 컴퓨터에 파일을 저장함
            if(saveFileName == null){
                saveFileName = "basement.jpg";
            }
            dto.getUserImg().transferTo(new File(getFullPath(saveFileName)));

        } catch (IOException e){
            logger.info("service failed");
            e.printStackTrace();
            throw new RuntimeException("Failed to save file", e);
        }

        String contentType = dto.getUserImg().getContentType();

        user.setName(dto.getUserNickName());
        user.setText(dto.getUserText());
        user.setUserImg(saveFileName);

        return userRepository.save(user).getId();
    }


    public String login(LoginRequestDTO dto){
        String email = dto.getEmail();
        String password = dto.getPassword();
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다");
        }

        // 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
        if(!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        UserDTO info = modelMapper.map(user, UserDTO.class);

        String accessToken = jwtUtil.createAccessToken(info);
        return accessToken;
    }

    public RUserRequestDTO getUserInfo(String userEmail){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new IllegalArgumentException("User not found with email: " + userEmail));

        RUserRequestDTO userRequestDTO = new RUserRequestDTO();

        userRequestDTO.setUserId(user.getId());
        userRequestDTO.setUserEmail(user.getEmail());
        userRequestDTO.setUserNickName(user.getName());
        userRequestDTO.setUserText(user.getText());
        userRequestDTO.setUserImgPath(user.getUserImg());

        logger.info("load user service: user.name = " + user.getName());
        logger.info("load user service: dto.name = " + userRequestDTO.getUserNickName());

        return userRequestDTO;
    }



    public UserDTO getUser(String userId){
        User user = userDataHandler.getUserEntity(userId);
        UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getPassword());
        return userDTO;
    }

    private String createSaveFileName(String originalFileName){
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName){
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos+1);
    }

    private String getFullPath(String fileName){
        return uploadPath + fileName;
    }

    private MultipartFile convertFileToMultipartFile(File file, String file_name, String file_path) throws  IOException {
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file_name, new FileInputStream(new File(file_path)));

        return multipartFile;
    }
}
