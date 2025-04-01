package com.round3.realestate.service;

import com.round3.realestate.entity.*;
import com.round3.realestate.exception.MortgageMinimumTermException;
import com.round3.realestate.exception.MortgageMissingInformationException;
import com.round3.realestate.exception.MortgagePropertyUnavailableException;
import com.round3.realestate.exception.MortgageRejectedException;
import com.round3.realestate.converter.EmploymentConverter;
import com.round3.realestate.converter.MortgageConverter;
import com.round3.realestate.payload.dashboard.DashboardResultDto;
import com.round3.realestate.payload.mortgage.EvaluateMortgageDto;
import com.round3.realestate.payload.mortgage.MortgageResultApprovedDto;
import com.round3.realestate.repository.EmploymentRepository;
import com.round3.realestate.repository.MortgageRepository;
import com.round3.realestate.repository.PropertyRepository;
import com.round3.realestate.repository.UserRepository;
import com.round3.realestate.util.MortgageUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MortgageServiceImpl implements MortgageService {

    private static final int MORTGAGE_MINIMUM_YEARS = 15;
    private static final BigDecimal PROPERTY_PRICE_MULTIPLIER = BigDecimal.valueOf(1.15);

    private final MortgageRepository mortgageRepository;
    private final PropertyRepository propertyRepository;
    private final EmploymentRepository employmentRepository;
    private final UserRepository userRepository;

    private void checkMortgageDto(EvaluateMortgageDto dto) {
        if (dto.getYears() < MORTGAGE_MINIMUM_YEARS) {
            throw new MortgageMinimumTermException();
        }
    }

    private void checkEmployment(Employment employment) {
        if (employment.getEmploymentStatus() == null ||
            EmploymentStatus.employed != employment.getEmploymentStatus() ||
            employment.getNetMonthly() == null ||
            employment.getContract() == null
        ) {
            throw new MortgageMissingInformationException();
        }
    }

    private Optional<Employment> employment(UserDetails details) {
        return userRepository
            .getUserByUsername(details.getUsername())
            .map(employmentRepository::getEmploymentByUser).orElseThrow();
    }


    private void checkProperty(Property property) {
        if (property == null || PropertyAvailability.Available != property.getAvailability()) {
            throw new MortgagePropertyUnavailableException();
        }
    }

    @Override
    public MortgageResultApprovedDto mortgage(EvaluateMortgageDto dto, UserDetails details) {

        checkMortgageDto(dto);

        Property property = Objects.requireNonNull(
            propertyRepository.findById(dto.getPropertyId()).orElse(null)
        );

        checkProperty(property);

        Employment employment = Objects.requireNonNull(
            employment(details).orElse(null)
        );

        checkEmployment(employment);

        int numberOfMonth = MortgageUtils.getNumberOfMonth(dto.getYears());

        BigDecimal monthlyPayment = MortgageUtils.calculateMonthlyPayment(
            property
                .getPrice()
                .multiply(PROPERTY_PRICE_MULTIPLIER),
            numberOfMonth
        );

        BigDecimal allowedPercentage = MortgageUtils
            .getAllowedPercentage(employment.getContract());

        BigDecimal netMonthly = employment.getNetMonthly();

        if (shouldApproveMortgage(monthlyPayment, netMonthly, allowedPercentage)) {
            return approve(property, employment, numberOfMonth, monthlyPayment, allowedPercentage, netMonthly);
        } else {
            throw new MortgageRejectedException(
                netMonthly,
                monthlyPayment,
                allowedPercentage
            );
        }
    }

    private MortgageResultApprovedDto approve(
        Property property,
        Employment employment,
        int numberOfMonth,
        BigDecimal monthlyPayment,
        BigDecimal allowedPercentage,
        BigDecimal netMonthly
    ) {
        property.setAvailability(PropertyAvailability.Unavailable);

        Mortgage mortgage = mortgageRepository.save(
            MortgageConverter.toEntity(
                monthlyPayment,
                numberOfMonth,
                property,
                employment.getUser()
            )
        );
        return MortgageResultApprovedDto.approved(
            mortgage.getId(),
            netMonthly,
            monthlyPayment,
            allowedPercentage,
            numberOfMonth
        );
    }

    private boolean shouldApproveMortgage(
        BigDecimal monthlyPayment,
        BigDecimal netMonthly,
        BigDecimal allowedPercentage
    ) {
        BigDecimal paymentThreshold = MortgageUtils.getPaymentThreshold(
            netMonthly,
            allowedPercentage
        );
        return monthlyPayment.compareTo(paymentThreshold) <= 0;
    }

    @Override
    public DashboardResultDto dashboard(UserDetails details) {

        Employment employment = Objects.requireNonNull(
            employment(details).orElse(null)
        );

        return new DashboardResultDto(
            mortgageRepository
                .findByUser(employment.getUser())
                .stream().map(MortgageConverter::toDto).toList(),
            EmploymentConverter.toDto(employment)
        );
    }
}
