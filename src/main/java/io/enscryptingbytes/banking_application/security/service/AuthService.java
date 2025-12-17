package io.enscryptingbytes.banking_application.security.service;

import io.enscryptingbytes.banking_application.dto.UserDto;
import io.enscryptingbytes.banking_application.entity.User;
import io.enscryptingbytes.banking_application.exception.BankAuthException;
import io.enscryptingbytes.banking_application.exception.BankUserException;
import io.enscryptingbytes.banking_application.security.dto.*;
import io.enscryptingbytes.banking_application.security.type.ChangePasswordType;
import io.enscryptingbytes.banking_application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static io.enscryptingbytes.banking_application.security.message.ExceptionMessage.*;
import static io.enscryptingbytes.banking_application.security.message.ResponseMessage.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    //TODO: Integrate notification client to send out email for signup, login, reset pw, change pw
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthResponse signUp(SignupRequest signupRequest) throws BankAuthException {
        try {
            User existingUser = userService.findUserByEmail(signupRequest.getEmail());
            if (existingUser != null) {
                throw new BankAuthException(USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
            }
        } catch (BankUserException ex) {
            log.error(ex.toString());
            throw new BankAuthException(ex.getMessage(), ex.getHttpStatus());
        }

        UserDto userDto = convertSignupRequestToUserDto(signupRequest, passwordEncoder);

        try {
            UserDto user = userService.createUser(userDto);
            return AuthResponse.builder()
                    .email(user.getEmail())
                    .message(USER_SIGNED_UP)
                    .build();
        } catch (BankUserException e) {
            log.error(e.toString());
            throw new BankAuthException(e.getMessage(), e.getHttpStatus());
        }
    }

    public AuthResponse login(LoginRequest loginRequest) throws BankAuthException {
        User existingUser = findExistingUserOrThrowBankAuthException(loginRequest.getEmail());
        if (!passwordEncoder.matches(loginRequest.getPassword(), existingUser.getPassword())) {
            throw new BankAuthException(INCORRECT_CREDENTIALS, HttpStatus.UNAUTHORIZED);
        }

        UserDetails existingUserDetails = userDetailsService.loadUserByUsername(existingUser.getEmail());
        String jwtToken = jwtService.generateToken(existingUserDetails);
        return AuthResponse.builder()
                .email(loginRequest.getEmail())
                .message(USER_LOGGED_IN)
                .jwtToken(jwtToken)
                .build();

    }

    public AuthResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws BankAuthException {
        User existingUser = findExistingUserOrThrowBankAuthException(forgotPasswordRequest.getEmail());
        //TODO: generate reset password token
        //TODO: commit to db, and set expiration and used field
        return AuthResponse.builder()
                .email(forgotPasswordRequest.getEmail())
                .message(RESET_PASSWORD_TOKEN_SENT)
                .build();
    }

    public AuthResponse changePassword(ChangePasswordRequest changePasswordRequest) throws BankAuthException {
        if (StringUtils.isBlank(changePasswordRequest.getNewPassword())
                || StringUtils.equals(changePasswordRequest.getNewPassword(), changePasswordRequest.getConfirmNewPassword())) {
            throw new BankAuthException(NEW_PASSWORD_MISMATCH, HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.equals(ChangePasswordType.PASSWORD.name(), changePasswordRequest.getChangePasswordType().name())
                && StringUtils.isBlank(changePasswordRequest.getOldPassword())) {
            throw new BankAuthException(OLD_PASSWORD_REQUIRED, HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.equals(ChangePasswordType.TOKEN.name(), changePasswordRequest.getChangePasswordType().name())
                && StringUtils.isBlank(changePasswordRequest.getResetPasswordToken())) {
            throw new BankAuthException(TOKEN_REQUIRED, HttpStatus.BAD_REQUEST);
        }


        //TODO: if token validation then validate, make token used, commit to db
        //TODO: if password then validate old password
        //TODO: reset if validation successful
        if (StringUtils.equals(ChangePasswordType.PASSWORD.name(), changePasswordRequest.getChangePasswordType().name())) {
            try {
                User user = userService.findUserByEmail(changePasswordRequest.getEmail());
                user.setPassword(changePasswordRequest.getNewPassword());
                String token = jwtService.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
                return new AuthResponse(changePasswordRequest.getEmail(), "Token created", token);
            } catch (BankUserException ex) {
                log.error(ex.toString());
                throw new BankAuthException(ex.getMessage(), ex.getHttpStatus());
            }
        } else if (StringUtils.equals(ChangePasswordType.TOKEN.name(), changePasswordRequest.getChangePasswordType().name())) {
            //TODO: validate reset token to email. check if reset token is already consumed. if yes throw ex, otherwise reset password.
            return null;
        } else {
            throw new BankAuthException("Invalid change password request type.", HttpStatus.BAD_REQUEST);
        }
    }

    private User findExistingUserOrThrowBankAuthException(String email) throws BankAuthException {
        try {
            User existingUser = userService.findUserByEmail(email);
            if (existingUser == null) {
                throw new BankAuthException(USER_DOES_NOT_EXIST, HttpStatus.BAD_REQUEST);
            }
            return existingUser;
        } catch (BankUserException e) {
            log.error(e.toString());
            throw new BankAuthException(e.getMessage(), e.getHttpStatus());
        }
    }

    public static UserDto convertSignupRequestToUserDto(SignupRequest signupRequest, BCryptPasswordEncoder passwordEncoder) {
        return UserDto.builder()
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .mobileNumber(signupRequest.getMobileNumber())
                .dob(signupRequest.getDob())
                .build();
    }

}
