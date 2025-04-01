package com.round3.realestate.errors;

import com.round3.realestate.exception.*;
import com.round3.realestate.payload.error.ErrorDto;
import com.round3.realestate.payload.mortgage.MortgageResultRejectedDto;
import com.round3.realestate.payload.user.registration.UserRegistrationResultDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
class GlobalControllerExceptionHandler {

    private static final Map<Class<? extends Exception>, ErrorDto> errors
        = new HashMap<>();

    static {
        errors.put(UserBadCredentialsException.class, ErrorDto.errorCredentials());
        errors.put(MortgagePropertyUnavailableException.class, ErrorDto.errorPropertyUnavailable());
        errors.put(MortgageMissingInformationException.class, ErrorDto.errorMissingInfo());
        errors.put(MortgageMinimumTermException.class, ErrorDto.errorMinimumTerm());
        errors.put(AuctionPropertyNotFoundException.class, ErrorDto.errorPropertyNotFound());
        errors.put(AuctionPropertyNotAvailableException.class, ErrorDto.errorPropertyNotAvailable());
        errors.put(AuctionAlreadyClosedException.class, ErrorDto.errorAuctionClosed());
        errors.put(AuctionBidMustNotExceedMinimumException.class, ErrorDto.errorBidAmount());
    }

    @ExceptionHandler({UserBadCredentialsException.class,
        MortgagePropertyUnavailableException.class,
        MortgageMissingInformationException.class,
        MortgageMinimumTermException.class,
        AuctionPropertyNotFoundException.class,
        AuctionPropertyNotAvailableException.class,
        AuctionAlreadyClosedException.class,
        AuctionBidMustNotExceedMinimumException.class})
    protected ResponseEntity<ErrorDto> handleErrors(Exception ex) {
        return badRequest(errors.get(ex.getClass()));
    }

    @ExceptionHandler(MortgageRejectedException.class)
    protected ResponseEntity<MortgageResultRejectedDto> rejected(MortgageRejectedException ex) {
        return ResponseEntity.ok(
            MortgageResultRejectedDto.rejected(
                ex.getNetMonthly(),
                ex.getMonthlyPayment(),
                ex.getAllowedPercentage()));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    protected ResponseEntity<UserRegistrationResultDto> handleUsernameExists() {
        return badRequest(UserRegistrationResultDto.errorUsernameAlreadyExists());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    protected ResponseEntity<UserRegistrationResultDto> handleEmailExists() {
        return badRequest(UserRegistrationResultDto.errorEmailAlreadyExists());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(
        MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
            .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    private <T> ResponseEntity<T> badRequest(T error) {
        return ResponseEntity.badRequest().body(error);
    }
}