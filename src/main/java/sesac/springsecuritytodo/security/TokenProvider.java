package sesac.springsecuritytodo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sesac.springsecuritytodo.SpringSecurityTodoApplication;
import sesac.springsecuritytodo.entity.UserEntity;

import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class TokenProvider {
    // JWT 토큰 생성을 위한 비밀키
    private static final String SECRET_KEY = "your_secret_key";

    public String createJWT(UserEntity userEntity) {
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // 토큰 서명
                .setSubject(String.valueOf(userEntity.getId())) // payload에 들어갈 내용
                .setIssuer("spring-security-todo") // 토큰 발급자
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(expiredDate) // 토큰 만료 시간
                .compact(); // 생성
    }
    // 토큰 파싱후 위조 검사
    // 메서드를 사용해 간단히 구축
    public String validatedAndGetUserId(String token) {
        // header에 저장된
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
