package com.round3.realestate.converter;

import com.round3.realestate.entity.User;
import com.round3.realestate.payload.user.registration.UserRegistrationDto;
import com.round3.realestate.payload.user.session.UserSessionResultDto;

public class UserConverter {

    public static User toEntity(UserRegistrationDto dto, String encrypted) {
        return User.builder()
            .username(dto.getUsername())
            .password(encrypted)
            .email(dto.getEmail())
            .build();
    }

    public static UserSessionResultDto toSessionDto(User user) {
        return new UserSessionResultDto(
            user.getId(),
            user.getUsername(),
            user.getEmail()
        );
    }
}
