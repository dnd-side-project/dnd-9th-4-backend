package com.dnd.health.config.jwt.service;

import com.dnd.health.config.jwt.JwtProperties;
import com.dnd.health.config.jwt.handler.exception.CustomJwtException;
import com.dnd.health.member.domain.Member;
import com.dnd.health.member.domain.MemberRepository;
import com.dnd.health.member.dto.CMResp;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JwtService {

    private final MemberRepository memberRepository;
    private static String secretKey = JwtProperties.SECRET;

    byte[] secretKeyBytes = secretKey.getBytes();

    @Transactional(readOnly = true)
    public Member getMemberByRefreshToken(String refreshToken) {
        return memberRepository.findMemberByRefreshToken(refreshToken).orElseThrow( () -> new CustomJwtException("refresh token error"));
    }
    // 2023-01-25 -> setRefreshToken, removeRefreshToken mapper로직으로 수정.
    public void setRefreshToken(String username, String refreshToken) {

        Member memberOp = memberRepository.findByUsername(username);

        if(memberOp != null) {
            memberOp.changeRefreshToken(refreshToken);
            memberRepository.save(memberOp);
        } else {
            throw new CustomJwtException("Refresh Token update error");
        }
    }

    public void removeRefreshToken(String refreshToken) {
        Optional<Member> memberOp = memberRepository.findMemberByRefreshToken(refreshToken);

        if(memberOp.isPresent()) {
            Member findMember = memberOp.get();
            findMember.changeRefreshToken(null);

            memberRepository.save(findMember);
        } else {
            throw new CustomJwtException("Refresh Token update error");
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // 2-2. HttpServletRequest에서 쿠키 값들 가져온다.
        Cookie[] cookies = request.getCookies();

        if(cookies != null) {
            // 2-3. 쿠키를 for문으로 하나하나 돌려서
            for(Cookie cookie : cookies) {
                // 2-4. access_token이라는 쿠키가 있다면
                if(cookie.getName().equals("access_token")) {
                    // 2-5. 쿠키에서 access_token 값 String으로 가져온다.
                    String access_token = cookie.getValue().toString();

                    try {
                        // 2-6. access_token에 들어있는 userId 값을 가져온다.
                        Long userId = (Long) Jwts.parser().setSigningKey(secretKeyBytes).parseClaimsJws(access_token).getBody().get("userId");

                        // 2-7. 해당 userId로 DB에서 해당 member를 찾는다.
                        Optional<Member> memberOp = memberRepository.findById(userId);

                        // 2-8. 해당 member가 DB에 존재하면
                        if(memberOp.isPresent()) {
                            // 2-9. 해당 member get
                            Member findUser = memberOp.get();

                            // 2-10. 찾아온 멤버에 저장된 refresh_token 값을 null로 초기화해주고
                            findUser.changeRefreshToken(null);

                            // 2-11. update 시킨다.
                            memberRepository.save(findUser);
                        }

                        // 2-12. access_token 쿠키 제거
                        cookie.setMaxAge(0);
                        // 2-13. HttpServletResponse에 maxAge가 0인 access_token 쿠키 장착(쿠키 소멸)
                        response.addCookie(cookie);

                    } catch (ExpiredJwtException e) {
                        // 2-14. 로그아웃 진행시 토큰이 만료되어서 ExpiredJwtException이 터져도 쿠키 제거
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);

                        // 2023-02-06 -> 로그아웃 예외처리 완료.
                    }
                }
            }
        }
    }

    public void checkHeaderValid(HttpServletRequest request) {
        String access_token = request.getHeader(JwtProperties.HEADER_STRING);
        log.info("access_token : {}", access_token);
        if(access_token == null) {
            throw new CustomJwtException("Access token 값이 null입니다.");
        }
    }

    public boolean isNeedToUpdateRefreshToken(String refresh_token) {
        try {
            Date expiresAt = Jwts.parser().setSigningKey(secretKeyBytes).parseClaimsJws(refresh_token).getBody().getExpiration();
            Date current_date = new Date(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current_date);
            calendar.add(Calendar.DATE, 7);
            Date after7dayFromToday = calendar.getTime();
            if(expiresAt.after(after7dayFromToday)) {
                log.info("refresh_token 만료 예정 일 : " + after7dayFromToday);
                return true;
            }
        } catch (CustomJwtException e) {
            return true;
        }
        return false;
    }

    public boolean isNeedToUpdateAccessToken(String access_token) {
        try {
            Date expiresAt = Jwts.parser().setSigningKey(secretKeyBytes).parseClaimsJws(access_token).getBody().getExpiration();

            Date current_date = new Date(System.currentTimeMillis());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current_date);
            calendar.add(Calendar.DATE, 2);

            Date after1dayFromToday = calendar.getTime();

            if(expiresAt.before(after1dayFromToday)) {
                log.info("access_token 만료 예정 일 : " + after1dayFromToday);
                return true;
            }
        } catch (CustomJwtException e) {
            return true;
        }
        return false;
    }

    // 1-19. 토큰의 유효성 및 만료일자 확인
    public boolean validationToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secretKeyBytes).parseClaimsJws(jwtToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 Jwt 서명입니다.");

        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다.");

        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰입니다.");

        } catch (IllegalArgumentException e) {
            log.error("잘못된 토큰입니다.");
        }
        return false;
    }

    public void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        // 2023-01-30 -> objectMapper 이용해서 json 에러 형태 반환 까지 완료.
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(new CMResp<>(1, message, null));
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(result);
    }
}
