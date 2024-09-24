package secret_diary.secret_diary_spring.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import secret_diary.secret_diary_spring.security.jwt.JwtAuthFilter;
import secret_diary.secret_diary_spring.security.jwt.JwtUtil;

import java.util.stream.Stream;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    //SecurityConfig는 Spring Security의 설정을 정의하는 곳으로 애플리케이션의 요청 경로에 대한 인증 및 권한 설정, JWT 필터 적용, 암호화 방식등을 설정함
    private final UserDetailService userService;

    private final JwtUtil jwtUtil;


    private static final String[] permit_userController_list = {
            "security/join","security/update/{userEmail}","security/login",
            "security/logout","/home/{userId}",
            "security/user/{userEmail}","/user/image/{filename}",
            "search/{keyword}/{userEmail}","delete/{userEmail}"
    };

    private static final String[] permit_noticeController_list = {
            "upload","findAll2","read/notice/user","read/detail/notice",
            "search/notice","/notice/image/{filename}"
    };

    private static final String[] permit_friendController_list = {
            "friend/request/{userEmail}/{friendEmail}","friend/request/list/{userEmail}",
            "friend/accept/{userEmail}/{friendEmail}","friend/my/{userEmail}",
            "friend/my/search/{userEmail}/{friendEmail}",
            "friend/check/{userEmail}/{friendEmail}",
            "friend/request/check/{userEmail}/{friendEmail}"

    };


    String[] allPermitList = Stream.concat(
            Stream.concat(Stream.of(permit_userController_list), Stream.of(permit_noticeController_list)),
            Stream.of(permit_friendController_list)
    ).toArray(String[]::new);


    @Bean
    public WebSecurityCustomizer configure(){
        return (web -> web.ignoring()
                //spring security가 지정된 경로를 무시하도록 설정
                //.requestMatchers("home"));
                .requestMatchers("static/**")); //static/**의 경우 정적 리소스 폴더(예: CSS, JS 파일등)로 들어오는 모든 요청 무시
    }

    //SecurityFilterChain의 requestMatcheres의 경우 특정 경로에 대해 인증 및 권한 검사 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf 보안 비활성화
        http.csrf((csrf) -> csrf.disable());


        //FormLogin, BasicHttp 비활성화
        http.formLogin((form) -> form.disable());
        http.httpBasic(AbstractHttpConfigurer :: disable);


        //JwtAuthFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
        http.addFilterBefore(new JwtAuthFilter(userService, jwtUtil), UsernamePasswordAuthenticationFilter.class);


        //권한 규칙 작성
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        allPermitList
                ).permitAll() //권한이 필요하지 않은 경로
                .requestMatchers(
                        "/security/autoLogin"
                ).authenticated()
                .anyRequest().authenticated() //권한이 필요한 경로
        );


        //로그아웃
        http.logout(logout -> logout
                .logoutUrl("security/logout")
                .logoutSuccessUrl("/security/login") //로그아웃 성공시 이동 할 페이지
                .invalidateHttpSession(true) //세션 무효화
                .permitAll() //로그아웃 url 접근 허용
        );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
