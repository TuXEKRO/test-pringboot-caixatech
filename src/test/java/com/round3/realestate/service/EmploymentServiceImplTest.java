package com.round3.realestate.service;

import com.round3.realestate.entity.User;
import com.round3.realestate.exception.EmailAlreadyExistsException;
import com.round3.realestate.exception.UsernameAlreadyExistsException;
import com.round3.realestate.payload.user.registration.UserRegistrationDto;
import com.round3.realestate.payload.user.registration.UserRegistrationResultDto;
import com.round3.realestate.repository.EmploymentRepository;
import com.round3.realestate.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmploymentServiceImplTest {

    @Mock
    PasswordEncoder encoder;

    @Mock
    EmploymentRepository employmentRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    EmploymentServiceImpl employmentService;

    private UserRegistrationDto userRegistrationDto;

    @BeforeEach
    public void setUp() {
        userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUsername("user");
        userRegistrationDto.setEmail("user@org.com");
        userRegistrationDto.setPassword("password");
    }

    @Test
    public void when_email_already_exists_throw() {
        // given
        given(userRepository.getUserByEmail(any()))
            .willReturn(Optional.of(mock(User.class)));

        // then
        assertThrows(
            EmailAlreadyExistsException.class,
            // when
            () -> employmentService.register(userRegistrationDto),
            "Expected to throw EmailAlreadyExistsException"
        );

        then(userRepository).should().getUserByEmail("user@org.com");
    }


    @Test
    public void when_username_already_exists_throw() {
        // given
        given(userRepository.getUserByUsername(any()))
            .willReturn(Optional.of(mock(User.class)));

        // then
        assertThrows(
            UsernameAlreadyExistsException.class,
            // when
            () -> employmentService.register(userRegistrationDto),
            "Expected to throw UsernameAlreadyExistsException"
        );

        then(userRepository).should().getUserByUsername("user");
    }

    @Test
    public void when_user_is_new_register() {
        // given
        given(userRepository.getUserByUsername(any()))
            .willReturn(Optional.empty());

        given(encoder.encode(any())).willReturn("encoded");

        // when
        UserRegistrationResultDto result = employmentService
            .register(userRegistrationDto);

        // then
        then(userRepository).should().getUserByUsername("user");
        then(encoder).should().encode("password");
        then(employmentRepository).should().save(any());

        assertEquals(
            result.getSuccess(),
            true
        );
    }
}
