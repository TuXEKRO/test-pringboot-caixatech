package com.round3.realestate.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.round3.realestate.payload.error.ErrorDto;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPointProvider {


    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint () {
        return (request, response, authException) -> {
            ObjectMapper mapper = new ObjectMapper();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            String responseMsg = mapper.writeValueAsString(ErrorDto.errorUnauthorized());
            response.getWriter().write(responseMsg);
        };
    }
}
