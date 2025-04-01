package com.round3.realestate.service;

import com.round3.realestate.entity.User;
import com.round3.realestate.exception.UserBadCredentialsException;
import com.round3.realestate.converter.UserConverter;
import com.round3.realestate.payload.user.login.UserLoginDto;
import com.round3.realestate.payload.user.login.UserLoginResultDto;
import com.round3.realestate.payload.user.session.UserSessionResultDto;
import com.round3.realestate.repository.UserRepository;
import com.round3.realestate.security.JwtTokenUtil;
import com.round3.realestate.security.JwtUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;

    @Override
    public UserLoginResultDto login(UserLoginDto dto) {

        User user = userRepository.getUserByUsernameOrEmail(
            dto.getUsernameOrEmail(),
            dto.getUsernameOrEmail()
        ).orElseThrow(UserBadCredentialsException::new);

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    dto.getPassword()
                )
            );

            UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getUsername());

            String token = jwtTokenUtil.generateToken(userDetails);

            return UserLoginResultDto.success(token);
        } catch (AuthenticationException e) {
            throw new UserBadCredentialsException();
        }
    }

    @Override
    public UserSessionResultDto me(UserDetails details) {
        User user = userRepository
            .getUserByUsername(details.getUsername()).orElseThrow();
        return UserConverter.toSessionDto(user);
    }
}
