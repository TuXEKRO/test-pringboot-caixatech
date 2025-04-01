package com.round3.realestate.payload.user.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginDto {
    @NotBlank
    private String usernameOrEmail;
    @NotBlank
    private String password;
}
