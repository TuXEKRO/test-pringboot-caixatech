package com.round3.realestate.payload.user.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationDto {
    @NotBlank
    String username;
    @Email(message = "email should be present")
    @NotBlank
    String email;
    @NotBlank
    String password;
}
