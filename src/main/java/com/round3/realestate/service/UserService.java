package com.round3.realestate.service;

import com.round3.realestate.payload.user.login.UserLoginDto;
import com.round3.realestate.payload.user.login.UserLoginResultDto;
import com.round3.realestate.payload.user.session.UserSessionResultDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserLoginResultDto login(UserLoginDto dto);

    UserSessionResultDto me(UserDetails details);
}
