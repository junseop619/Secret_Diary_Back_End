package secret_diary.secret_diary_spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import secret_diary.secret_diary_spring.DI.dto.JoinRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.LoginRequestDTO;
import secret_diary.secret_diary_spring.DI.dto.UserDTO;
import secret_diary.secret_diary_spring.DI.entity.User;
import secret_diary.secret_diary_spring.DI.handler.UserDataHandler;
import secret_diary.secret_diary_spring.DI.repository.UserRepository;
import secret_diary.secret_diary_spring.security.jwt.JwtUtil;
import secret_diary.secret_diary_spring.service.UserService;

@RequiredArgsConstructor
@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    @Autowired
    UserDataHandler userDataHandler;


    public Long joinUser(JoinRequestDTO dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
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

    public UserDTO getUser(String userId){
        User user = userDataHandler.getUserEntity(userId);
        UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getPassword());
        return userDTO;
    }
}
