package secret_diary.secret_diary_spring.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter { // OncePerRequestFilter -> 한 번 실행 보장

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //클라이언트가 보낸 HTTP 요청의 Authorization 헤더값을 가져옴
        String authorizationHeader = request.getHeader("Authorization");

        //JWT가 헤더에 있는 경우
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { //요청에서 Authorization 헤더를 읽어 Bearer로 시작하는 토큰 확인
            String token = authorizationHeader.substring(7);
            //JWT 유효성 검증
            if (jwtUtil.validateToken(token)) {
                //Long userId = jwtUtil.getUserId(token);
                String userEmail = jwtUtil.getUserEmail(token);

                //유저와 토큰 일치 시 userDetails 생성
                //토큰이 유효하다면 UserDetailsService를 통해 사용자 정보 load
                //UserDetails userDetails = userDetailsService.loadUserByUsername(userId.toString());
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail.toString());
                logger.info("JwtAuthFilter first complete");

                if (userDetails != null) {
                    //UserDetsils, Password, Role -> 접근권한 인증 Token 생성
                    //인증된 사용자 정보와 권한을 포함한 UsernamePasswordAuthenticationToken을 생성하여 Spring Security의 SecurityContextHolder에 설정
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    //현재 Request의 Security Context에 접근권한 설정
                    //이로인해 이후의 요청 처리가 인증된 사용자로 진행
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    logger.info("JwtAuthFilter second complete");
                }
            }
        }

        logger.info("JwtAuthFilter third complete");
        filterChain.doFilter(request, response); // 다음 필터로 넘기기
    }
}
