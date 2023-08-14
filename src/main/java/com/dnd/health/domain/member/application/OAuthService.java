package com.dnd.health.domain.member.application;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.dto.response.UserKakaoLoginResponseDto;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuthService {

    public String getKakaoAccessToken(String code) {

        String client_key = "0239e3b799683b851fa867e85be71696";
        String redirect_uri = "https://dnd-9th-4-newple-app.vercel.app/auth";
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth.token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=").append(client_key);
            sb.append("&redirect_uri=").append(redirect_uri);
            sb.append("&code=").append(code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            log.info("response_Code : {}", responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            log.info("response body : " + result);

            JsonElement element = JsonParser.parseString(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            log.info("access_token : {}", access_Token);
            log.info("refresh_Token : {}", refresh_Token);

            br.close();
            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return access_Token;
    }

    public HashMap<String, Object> getUserKakaoInfo(String access_token) {

        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = conn.getResponseCode();
            log.info("response code : {}", responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.info("response body : {}", result);

            JsonElement element = JsonParser.parseString(result);

            String id = element.getAsJsonObject().get("id").getAsString();

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();

            JsonObject kakao_account = properties.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();

            userInfo.put("nickname", nickname);
            userInfo.put("id", id);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userInfo;
    }

//    public UserKakaoLoginResponseDto kakaoLogin(String access_token) {
//
//        UserKakaoLoginResponseDto userKakaoSingupRequestDto = getUserKakaoSignupRequestDto(getUserKakaoInfo(access_token));
//        UserResponseDto userResponseDto = findByUserKakaoIdentifier(userKakaoSignupRequestDto.getUserKakaoIdentifier());
//
//        if (userResponseDto == null) {
//            signUp(userKakaoSingupRequestDto);
//            userResponseDto = findByUserKakaoIdentifier(userKakaoSingupRequestDto.getUserKakaoIdentifier());
//        }
//        String token = jwtTokenProvider.createToken(userResponseDto.getUserEmail());
//        return new UserKakaoLoginResponseDto(HttpStatus.OK, token, userResponseDto.getUserEmail());
//    }
//
//    public UserResponseDto findByUserKakaoIdentifier(String kakoIdentifier) {
//        List<Member> member = memberRepository.findMemberByMemberKakaoIdentifier(kakaoIdentifier)
//                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
//
//        if (member.size() == 0) {
//            return null;
//        }
//        return new UserResponseDto(member.get(0));
//    }
//
//    @Transactional
//    public Long singUp(UserKakaoSingupRequestDto userKakaoSingupRequestDto) {
//        Long id;
//        try {
//            id = memberRepository.save(userKakaoSingupRequestDto.toEntity()).getUserId();
//        } catch (Exception e) {
//            throw e;
//        }
//        return id;
//    }
//
//    private UserKakaoSignupRequestDto getUserKakaoSignupRequestDto(HashMap<String, Object> userInfo) {
//        String userPassword = "-1";
//        UserKakaoSignupRequestDto userKakaoSignupRequestDto =
//                new UserKakaoSignupRequestDto(userInfo.get("email").toString(),
//                        userInfo.get("nickname").toString(), userPassword, userInfo.get("nickname").toString(),
//                        userInfo.get("id").toString());
//        return userKakaoSignupRequestDto;
//    }
//
//    public void kakaoLogout(String accessToken) {
//        String reqURL = "http://kapi.kakao.com/v1/user/logout";
//        try {
//            URL url = new URL(reqURL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode = " + responseCode);
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            String result = "";
//            String line = "";
//
//            while((line = br.readLine()) != null) {
//                result+=line;
//            }
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
