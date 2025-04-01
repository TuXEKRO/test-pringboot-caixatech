package com.round3.realestate.payload.user.session;

import lombok.*;

@Getter
@AllArgsConstructor
public class UserSessionResultDto {
    private final Long id;
    private final String username;
    private final String email;
}
