package com.round3.realestate.payload.user.registration;

import lombok.*;

@Getter
@AllArgsConstructor
public class UserRegistrationResultDto {

    private final Boolean success;
    private final String message;

    public static UserRegistrationResultDto success() {
        return new UserRegistrationResultDto(true, SUCCESS_MESSAGE);
    }

    public static UserRegistrationResultDto errorUsernameAlreadyExists() {
        return new UserRegistrationResultDto(false, ERROR_USERNAME_EXISTS_MESSAGE);
    }

    public static UserRegistrationResultDto errorEmailAlreadyExists() {
        return new UserRegistrationResultDto(false, ERROR_EMAIL_EXISTS_MESSAGE);
    }

    private static final String SUCCESS_MESSAGE = "User successfully registered";
    private static final String ERROR_USERNAME_EXISTS_MESSAGE = "The username already exists";
    private static final String ERROR_EMAIL_EXISTS_MESSAGE = "Email already exists";
}
