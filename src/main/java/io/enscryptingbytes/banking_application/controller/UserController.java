package io.enscryptingbytes.banking_application.controller;

import io.enscryptingbytes.banking_application.dto.UserDto;
import io.enscryptingbytes.banking_application.dto.response.GenericResponse;
import io.enscryptingbytes.banking_application.exception.BankUserException;
import io.enscryptingbytes.banking_application.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.enscryptingbytes.banking_application.message.ResponseMessage.*;

@RequestMapping(value = "/user", produces = "application/json")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@Slf4j
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping(consumes = "application/json")
    public GenericResponse<UserDto> createUser(@Valid @RequestBody UserDto user) throws BankUserException {
        UserDto response = userService.createUser(user);
        return GenericResponse.<UserDto>builder()
                .message(USER_CREATION_PASSED)
                .httpStatus(HttpStatus.CREATED)
                .response(response)
                .build();

    }

    @GetMapping(value = "/{id}")
    public GenericResponse<UserDto> getUser(@Min(value = 1, message = "User ID must be a positive number") @PathVariable Long id) throws BankUserException {
        UserDto response = userService.getUser(id);
        return GenericResponse.<UserDto>builder()
                .message(USER_FOUND)
                .httpStatus(HttpStatus.OK)
                .response(response)
                .build();
    }

    @GetMapping(value = "")
    public GenericResponse<List<UserDto>> getUsers() {
        List<UserDto> response = userService.getUsers();
        if (response == null) {
            log.info("No users to show.");
            return GenericResponse.<List<UserDto>>builder()
                    .message(FAILED)
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return GenericResponse.<List<UserDto>>builder()
                    .message(SUCCESS)
                    .httpStatus(HttpStatus.OK)
                    .response(response)
                    .build();
        }
    }

    @PatchMapping(value = "/{id}")
    public GenericResponse<UserDto> updateUser(@Min(value = 1, message = "User ID must be a positive number") @PathVariable Long id, @Valid @RequestBody UserDto userDto) throws BankUserException {
        UserDto response = userService.updateUser(id, userDto);
        return GenericResponse.<UserDto>builder()
                .message(SUCCESS)
                .httpStatus(HttpStatus.OK)
                .response(response)
                .build();
    }

    @DeleteMapping(value = "/{id}")
    public GenericResponse<UserDto> deleteUser(@Min(value = 1, message = "User ID must be a positive number") @PathVariable Long id) throws BankUserException {
        UserDto response = userService.deleteUser(id);
        return GenericResponse.<UserDto>builder()
                .message(SUCCESS)
                .httpStatus(HttpStatus.OK)
                .response(response)
                .build();
    }
}
