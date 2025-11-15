package io.enscryptingbytes.banking_application.controller;

import io.enscryptingbytes.banking_application.dto.UserDto;
import io.enscryptingbytes.banking_application.dto.response.GenericResponse;
import io.enscryptingbytes.banking_application.exception.BankUserException;
import io.enscryptingbytes.banking_application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.enscryptingbytes.banking_application.message.ResponseMessage.*;

@RequestMapping(value = "/user", produces = "application/json")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping(consumes = "application/json")
    public GenericResponse<UserDto> createUser(@RequestBody UserDto user) {
        UserDto response = null;
        try {
            response = userService.createUser(user);
            return GenericResponse.<UserDto>builder()
                    .message(USER_CREATION_PASSED)
                    .httpStatus(HttpStatus.CREATED)
                    .response(response)
                    .build();
        } catch (BankUserException e) {
            log.error(e.getMessage());
            return GenericResponse.<UserDto>builder()
                    .message(USER_WITH_MOBILE_NUMBER_EXIST)
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
    }

    @GetMapping(value = "/{id}")
    public GenericResponse<UserDto> getUser(@PathVariable Long id) {
        UserDto response = userService.getUser(id);
        if (response == null) {
            log.info("No user to show.");
            return GenericResponse.<UserDto>builder()
                    .message(USER_DOES_NOT_EXIST)
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return GenericResponse.<UserDto>builder()
                    .message(USER_FOUND)
                    .httpStatus(HttpStatus.OK)
                    .response(response)
                    .build();
        }
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
    public GenericResponse<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto response = userService.updateUser(id, userDto);
        if (response == null) {
            log.info("No user to update.");
            return GenericResponse.<UserDto>builder()
                    .message(FAILED)
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return GenericResponse.<UserDto>builder()
                    .message(SUCCESS)
                    .httpStatus(HttpStatus.OK)
                    .response(response)
                    .build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public GenericResponse<UserDto> deleteUser(@PathVariable Long id) {
        UserDto response = userService.deleteUser(id);
        if (response == null) {
            log.info("No user to delete.");
            return GenericResponse.<UserDto>builder()
                    .message(FAILED)
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return GenericResponse.<UserDto>builder()
                    .message(SUCCESS)
                    .httpStatus(HttpStatus.OK)
                    .response(response)
                    .build();
        }
    }
}
