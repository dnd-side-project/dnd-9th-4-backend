package com.dnd.health.global.security.oauth.provider;

public interface OAuthUserInfo {
    String getProviderId();
    String getProvider();
    String getUsername();
    String getPassword();
    String getGender();
    String getBirth();
    String getAge();
    String getEmail();
}
