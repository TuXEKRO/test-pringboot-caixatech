package com.round3.realestate.service;

import com.round3.realestate.entity.Employment;
import com.round3.realestate.converter.EmploymentConverter;
import com.round3.realestate.entity.EmploymentContract;
import com.round3.realestate.entity.EmploymentStatus;
import com.round3.realestate.exception.EmailAlreadyExistsException;
import com.round3.realestate.exception.UsernameAlreadyExistsException;
import com.round3.realestate.payload.employment.EmploymentCreateResultDto;
import com.round3.realestate.payload.employment.EmploymentDto;
import com.round3.realestate.payload.employment.EmploymentUpdateResultDto;
import com.round3.realestate.payload.user.registration.UserRegistrationDto;
import com.round3.realestate.payload.user.registration.UserRegistrationResultDto;
import com.round3.realestate.repository.EmploymentRepository;
import com.round3.realestate.repository.UserRepository;
import com.round3.realestate.util.EmploymentUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmploymentServiceImpl implements EmploymentService {

    private final PasswordEncoder encoder;
    private final EmploymentRepository employmentRepository;
    private final UserRepository userRepository;

    @Override
    public UserRegistrationResultDto register(UserRegistrationDto dto) {

        if (userRepository.getUserByEmail(dto.getEmail()).isPresent()) {
             throw new EmailAlreadyExistsException();
        }

        if (userRepository.getUserByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }

        employmentRepository.save(
            EmploymentConverter.toEntity(dto, encoder.encode(dto.getPassword()))
        );

        return UserRegistrationResultDto.success();
    }

    @Override
    public EmploymentCreateResultDto create(EmploymentDto dto, UserDetails details) {
        return EmploymentConverter.toCreateResultDto(update(dto, details));
    }

    private Optional<Employment> employment(UserDetails details) {
        return userRepository
            .getUserByUsername(details.getUsername())
            .map(employmentRepository::getEmploymentByUser).orElseThrow();
    }

    private Employment update(EmploymentDto dto, UserDetails details) {

        Employment employment = employment(details).orElseThrow();

        BigDecimal salary = dto.getSalary();
        EmploymentContract contract = dto.getContract();

        if (salary != null || contract != null) {
            employment.setEmploymentStatus(EmploymentStatus.employed);

            if(salary != null) {
                employment.setNetMonthly(EmploymentUtils.calculateNetMonthly(salary));
                employment.setSalary(salary);
            }

            if (contract != null) {
                employment.setContract(contract);
            }
        }

        return employmentRepository.save(employment);
    }

    @Override
    public EmploymentUpdateResultDto updateEmployment(EmploymentDto dto, UserDetails details) {
        return EmploymentConverter.toUpdateResultDto(update(dto, details));
    }
}
