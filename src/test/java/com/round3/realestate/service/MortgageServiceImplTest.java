package com.round3.realestate.service;

import com.round3.realestate.entity.*;
import com.round3.realestate.exception.MortgageMinimumTermException;
import com.round3.realestate.exception.MortgageMissingInformationException;
import com.round3.realestate.exception.MortgagePropertyUnavailableException;
import com.round3.realestate.exception.MortgageRejectedException;
import com.round3.realestate.payload.mortgage.EvaluateMortgageDto;
import com.round3.realestate.payload.mortgage.MortgageResultApprovedDto;
import com.round3.realestate.repository.EmploymentRepository;
import com.round3.realestate.repository.MortgageRepository;
import com.round3.realestate.repository.PropertyRepository;
import com.round3.realestate.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class MortgageServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    EmploymentRepository employmentRepository;

    @Mock
    MortgageRepository mortgageRepository;

    @Mock
    PropertyRepository propertyRepository;

    @InjectMocks
    MortgageServiceImpl mortgageService;

    private EvaluateMortgageDto evaluateMortgageDto10years;
    private EvaluateMortgageDto evaluateMortgageDto20years;
    private Property unavailableProperty;
    private Property availableProperty;
    private UserDetails details;
    private Employment unemployed;
    private Employment sufficientIncome;
    private Employment insufficientIncome;
    private Mortgage mortgage;
    private User user;

    @BeforeEach
    public void setUp() {
        evaluateMortgageDto10years = new EvaluateMortgageDto();
        evaluateMortgageDto10years.setPropertyId(1L);
        evaluateMortgageDto10years.setYears(10);

        evaluateMortgageDto20years = new EvaluateMortgageDto();
        evaluateMortgageDto20years.setPropertyId(1L);
        evaluateMortgageDto20years.setYears(20);

        unavailableProperty = Property.builder()
            .id(1L)
            .availability(PropertyAvailability.Unavailable)
            .location("some location")
            .name("some name")
            .price(BigDecimal.valueOf(680_000))
            .rooms("some rooms")
            .size("some size")
            .build();

        availableProperty = Property.builder()
            .id(1L)
            .availability(PropertyAvailability.Available)
            .location("some location")
            .name("some name")
            .price(BigDecimal.valueOf(680_000))
            .rooms("some rooms")
            .size("some size")
            .build();

        user = User.builder()
            .id(1L)
            .username("User")
            .password("Password")
            .email("user@org.com")
            .build();

        details = mock(UserDetails.class);

        unemployed = Employment.builder()
            .id(1L)
            .user(user)
            .employmentStatus(EmploymentStatus.unemployed)
            .build();

        sufficientIncome = Employment.builder()
            .id(1L)
            .user(user)
            .employmentStatus(EmploymentStatus.employed)
            .contract(EmploymentContract.indefinite)
            .salary(BigDecimal.valueOf(1_000_000))
            .netMonthly(new BigDecimal("43674.7"))
            .build();

        insufficientIncome = Employment.builder()
            .id(1L)
            .user(user)
            .employmentStatus(EmploymentStatus.employed)
            .contract(EmploymentContract.indefinite)
            .salary(BigDecimal.valueOf(50_000))
            .netMonthly(new BigDecimal("2983.09"))
            .build();

        mortgage = Mortgage.builder()
            .id(1L)
            .user(user)
            .property(availableProperty)
            .numberOfMonths(evaluateMortgageDto20years.getYears() * 12)
            .monthlyPayment(BigDecimal.ONE)
            .build();
    }

    @Test
    public void when_mortgage_term_is_too_low_throw() {
        // given

        // then
        assertThrows(
            MortgageMinimumTermException.class,
            // when
            () -> mortgageService.mortgage(evaluateMortgageDto10years, null),
            "Expected to throw MortgageMinimumTermException with 10 years mortgage"
        );
    }

    @Test
    public void when_mortgage_property_unavailable_throw() {
        // given
        given(propertyRepository.findById(any())).willReturn(Optional.of(unavailableProperty));

        // then
        assertThrows(
            MortgagePropertyUnavailableException.class,
            // when
            () -> mortgageService.mortgage(evaluateMortgageDto20years, details),
            "Expected to throw MortgagePropertyUnavailableException"
        );

        then(propertyRepository).should().findById(1L);
    }

    @Test
    public void when_is_not_employed_throw() {
        // given
        given(details.getUsername()).willReturn("user");
        given(propertyRepository.findById(any())).willReturn(Optional.of(availableProperty));
        given(userRepository.getUserByUsername(any())).willReturn(Optional.of(user));
        given(employmentRepository.getEmploymentByUser(any())).willReturn(Optional.of(unemployed));

        // then
        assertThrows(
            MortgageMissingInformationException.class,
            // when
            () -> mortgageService.mortgage(evaluateMortgageDto20years, details),
            "Expected to throw MortgageMissingInformationException"
        );

        then(propertyRepository).should().findById(1L);
        then(userRepository).should().getUserByUsername("user");
        then(employmentRepository).should().getEmploymentByUser(user);
    }

    @Test
    public void when_employed_with_sufficient_income_should_approve() {
        // given
        given(details.getUsername()).willReturn("user");
        given(propertyRepository.findById(any())).willReturn(Optional.of(availableProperty));
        given(userRepository.getUserByUsername(any())).willReturn(Optional.of(user));
        given(employmentRepository.getEmploymentByUser(any())).willReturn(Optional.of(sufficientIncome));
        given(mortgageRepository.save(any())).willReturn(mortgage);

        // when
        MortgageResultApprovedDto result = mortgageService
            .mortgage(evaluateMortgageDto20years, details);

        // then
        then(propertyRepository).should().findById(1L);
        then(userRepository).should().getUserByUsername("user");
        then(employmentRepository).should().getEmploymentByUser(user);
        then(mortgageRepository).should().save(any());

        assertEquals(
            result.getApproved(),
            true
        );

        assertEquals(
            result.getMessage(),
            "Mortgage approved."
        );
    }

    @Test
    public void when_employed_with_insufficient_income_should_throw() {
        // given
        given(details.getUsername()).willReturn("user");
        given(propertyRepository.findById(any())).willReturn(Optional.of(availableProperty));
        given(userRepository.getUserByUsername(any())).willReturn(Optional.of(user));
        given(employmentRepository.getEmploymentByUser(any())).willReturn(Optional.of(insufficientIncome));

        // then
        var exception = assertThrows(
            MortgageRejectedException.class,
            // when
            () -> mortgageService.mortgage(evaluateMortgageDto20years, details),
            "Expected to throw MortgageMissingInformationException"
        );

        assertEquals(
            exception.getMonthlyPayment().doubleValue(),
            3956.01,
            0.01
        );

        assertEquals(
            exception.getAllowedPercentage().doubleValue(),
            0.3,
            0.00001
        );

        then(propertyRepository).should().findById(1L);
        then(userRepository).should().getUserByUsername("user");
        then(employmentRepository).should().getEmploymentByUser(user);
    }
}