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

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final UserDetailService userService;

    private final JwtUtil jwtUtil;

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

        /*
        http.formLogin(form -> form
                .loginPage("/security/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .permitAll()
        );*/

        //JwtAuthFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
        http.addFilterBefore(new JwtAuthFilter(userService, jwtUtil), UsernamePasswordAuthenticationFilter.class);


        //권한 규칙 작성
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/security/login","/security/join","/notice/upload","/upload","/findAll","/findAll2","/notice/image/{filename}","/search/notice","/security/update/{userEmail}"
                ).permitAll() //권한이 필요하지 않은 경로
                .anyRequest().authenticated() //권한이 필요한 경로
        );

        //로그아웃
        http.logout(logout -> logout
                .logoutSuccessUrl("/security/login") //로그아웃 성공시 이동 할 페이지
                .invalidateHttpSession(true) //세션 무효화
                .permitAll() //로그아웃 url 접근 허용
        );

        /*
        //인증과 인가 실패시 Exception handler
        http.exceptionHandling((exceptionHandling) -> exceptionHandling
                .authenticationEntryPoint(authenticationEntryPoint) //인증되지 않은 사용자에 대해 처리하는 Handler
                .accessDeniedHandler(accessDeniedHandler) //인증되었지만, 특정 리소스에 대한 권한이 없을 경우 호출되는 Handler
        ); */

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
