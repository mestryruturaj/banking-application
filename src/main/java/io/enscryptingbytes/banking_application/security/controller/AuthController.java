package io.enscryptingbytes.banking_application.security.controller;

import io.enscryptingbytes.banking_application.dto.response.GenericResponse;
import io.enscryptingbytes.banking_application.exception.BankAuthException;
import io.enscryptingbytes.banking_application.security.dto.*;
import io.enscryptingbytes.banking_application.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.enscryptingbytes.banking_application.security.message.ResponseMessage.*;

@RestController
@RequestMapping(value = "/auth", consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/signup")
    public GenericResponse<AuthResponse> signUp(@Valid @RequestBody SignupRequest signupRequest) throws BankAuthException {
        return GenericResponse.<AuthResponse>builder()
                .message(USER_SIGNED_UP)
                .httpStatus(HttpStatus.CREATED)
                .response(authService.signUp(signupRequest))
                .build();
    }

    @PostMapping(value = "/login")
    public GenericResponse<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws BankAuthException {
        return GenericResponse.<AuthResponse>builder()
                .message(USER_LOGGED_IN)
                .httpStatus(HttpStatus.OK)
                .response(authService.login(loginRequest))
                .build();
    }

    @PostMapping(value = "/forgot-password")
    public GenericResponse<AuthResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) throws BankAuthException {
        return GenericResponse.<AuthResponse>builder()
                .message(RESET_PASSWORD_TOKEN_SENT)
                .httpStatus(HttpStatus.OK)
                .response(authService.forgotPassword(forgotPasswordRequest))
                .build();
    }

    @PostMapping(value = "/change-password")
    public GenericResponse<AuthResponse> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) throws BankAuthException {
        return GenericResponse.<AuthResponse>builder()
                .message(PASSWORD_CHANGED)
                .httpStatus(HttpStatus.OK)
                .response(authService.changePassword(changePasswordRequest))
                .build();
    }
}
