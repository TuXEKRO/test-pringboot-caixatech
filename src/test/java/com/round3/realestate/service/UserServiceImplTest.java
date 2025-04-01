package com.round3.realestate.service;

import com.round3.realestate.entity.User;
import com.round3.realestate.exception.UserBadCredentialsException;
import com.round3.realestate.payload.user.login.UserLoginDto;
import com.round3.realestate.payload.user.login.UserLoginResultDto;
import com.round3.realestate.repository.UserRepository;
import com.round3.realestate.security.JwtTokenUtil;
import com.round3.realestate.security.JwtUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    JwtTokenUtil jwtTokenUtil;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUserDetailsService userDetailsService;

    @InjectMocks
    UserServiceImpl userService;

    private UserDetails details;
    private User user;
    private UserLoginDto unknownUser;
    private UserLoginDto knownUser;

    @BeforeEach
    public void setUp() {
        user = User.builder()
            .id(1L)
            .username("user")
            .password("password")
            .email("user@user.com")
            .build();

        details = mock(UserDetails.class);

        unknownUser = new UserLoginDto();
        unknownUser.setPassword("password");
        unknownUser.setUsernameOrEmail("unknown");

        knownUser = new UserLoginDto();
        knownUser.setPassword("password");
        knownUser.setUsernameOrEmail("user");

    }

    @Test
    public void when_me_with_details_should_return_session() {
        // given
        given(details.getUsername()).willReturn("user");
        given(userRepository.getUserByUsername("user"))
            .willReturn(Optional.of(user));
        // when
        var result = userService.me(details);
        // then
        assertEquals(
            result.getEmail(),
            user.getEmail()
        );

        assertEquals(
            result.getUsername(),
            user.getUsername()
        );

        assertEquals(
            result.getId(),
            user.getId()
        );

        then(userRepository).should().getUserByUsername("user");
    }

    @Test
    public void when_login_with_unknown_username_and_email_should_throw() {
        // given
        given(userRepository.getUserByUsernameOrEmail(
            unknownUser.getUsernameOrEmail(),
            unknownUser.getUsernameOrEmail()
        )).willReturn(Optional.empty());

        // then
        assertThrows(
            UserBadCredentialsException.class,
            // when
            () -> userService.login(unknownUser),
            "Expected to throw UserBadCredentialsException"
        );
    }

    @Test
    void when_login_with_valid_credentials_should_return_token() {
        // given
        given(userRepository.getUserByUsernameOrEmail(
            knownUser.getUsernameOrEmail(),
            knownUser.getUsernameOrEmail()
        )).willReturn(Optional.of(user));

        given(authenticationManager.authenticate(any()))
            .willReturn(mock(Authentication.class));

        given(userDetailsService.loadUserByUsername("user"))
            .willReturn(details);

        given(jwtTokenUtil.generateToken(details)).willReturn("token");

        // when
        UserLoginResultDto result = userService.login(knownUser);

        // then
        assertEquals(
            result.getAccessToken(),
            "token"
        );

        assertEquals(
            result.getTokenType(),
            "Bearer"
        );

        then(userRepository).should()
            .getUserByUsernameOrEmail("user", "user");

        then(authenticationManager).should().authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                knownUser.getPassword()
            )
        );

        then(userDetailsService).should().loadUserByUsername("user");

        then(jwtTokenUtil).should().generateToken(details);

        verifyNoMoreInteractions(userRepository,
            authenticationManager, userDetailsService, jwtTokenUtil);
    }

    @Test
    void when_login_with_wrong_password_should_throw() {
        // given
        given(userRepository.getUserByUsernameOrEmail(
            knownUser.getUsernameOrEmail(),
            knownUser.getUsernameOrEmail()
        )).willReturn(Optional.of(user));

        given(authenticationManager.authenticate(any()))
            .willThrow(mock(AuthenticationException.class));

        // then
        assertThrows(
            UserBadCredentialsException.class,
            // when
            () -> userService.login(knownUser),
            "Expected to throw UserBadCredentialsException"
        );

        verifyNoInteractions(userDetailsService, jwtTokenUtil);
        verifyNoMoreInteractions(userRepository, authenticationManager);
    }
}
