package com.dnd.health.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;
    private final Map<String, Object> response;
    private final Map<String, Object> profile;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.response = (Map<String, Object>) attributes.get("kakao_account");
        this.profile = (Map<String, Object>) response.get("profile");
    }

    @Override
    public String getProviderId() {

        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String) response.get("email");
    }

    @Override
    public String getName() {
        return (String) profile.get("nickname");
    }

    @Override
    public String getAge() {
        return (String) profile.get("age");
    }

    @Override
    public String getGender() {
        return (String) profile.get("gender");
    }
}
