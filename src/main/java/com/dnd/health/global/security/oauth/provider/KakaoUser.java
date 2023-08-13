package com.dnd.health.global.security.oauth.provider;

import java.util.Map;

public class KakaoUser implements OAuthUserInfo{

    private Map<String, Object> attributes;
    private Map<String, Object> response;
    private Map<String, Object> profile;

    public KakaoUser(Map<String, Object> attributes) {
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
    public String getUsername() {
        return getProvider() + "_" + getProviderId();
    }

    @Override
    public String getPassword() {
        return getProviderId();
    }

    @Override
    public String getGender() {
        return "";
    }

    @Override
    public String getBirth() {
        return "";
    }

    @Override
    public String getAge() {
        return "";
    }

    @Override
    public String getEmail() {
        return (String) response.get("email");
    }
}
