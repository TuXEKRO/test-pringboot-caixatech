package com.round3.realestate.payload.user.login;

import lombok.*;

@Getter
public class UserLoginResultDto {

    private final String accessToken;
    private final String tokenType = BEARER_TOKEN_TYPE;

    private UserLoginResultDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public static UserLoginResultDto success(String token) {
        return new UserLoginResultDto(token);
    }

    public static final String BEARER_TOKEN_TYPE="Bearer";
}
