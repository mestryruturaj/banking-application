package io.enscryptingbytes.banking_application.service;

import io.enscryptingbytes.banking_application.dto.UserDto;
import io.enscryptingbytes.banking_application.entity.User;
import io.enscryptingbytes.banking_application.exception.BankUserException;
import io.enscryptingbytes.banking_application.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static io.enscryptingbytes.banking_application.message.ExceptionMessage.*;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {
    private final UserRepository userRepository;

    public UserDto createUser(UserDto user) throws BankUserException {
        User existingUser = getUserByMobileNumber(user.getMobileNumber());
        if (existingUser != null) {
            throw new BankUserException(USER_ALREADY_EXISTS);
        }

        User newUser = mapUserDtoToUser(user);
        userRepository.save(newUser);
        return user;
    }

    private User getUserByMobileNumber(String mobileNumber) throws BankUserException {
        return userRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new BankUserException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
    }

    public UserDto getUser(Long id) throws BankUserException {
        User user = userRepository.findById(id).orElseThrow(() -> new BankUserException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        return mapUserToUserDto(user);
    }

    public List<UserDto> getUsers() {
        List<User> allUser = userRepository.findAll();
        return mapUserToUserDto(allUser);

    }

    public UserDto updateUser(Long id, UserDto userDto) throws BankUserException {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new BankUserException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));

        if (userDto.getFirstName() != null) {
            existingUser.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            existingUser.setLastName(userDto.getLastName());
        }
        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }
        if (userDto.getMobileNumber() != null) {
            existingUser.setMobileNumber(userDto.getMobileNumber());
        }
        if (userDto.getDob() != null) {
            existingUser.setDob(userDto.getDob());
        }

        User savedUser = userRepository.save(existingUser);
        return mapUserToUserDto(savedUser);
    }

    public UserDto deleteUser(Long id) throws BankUserException {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new BankUserException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        userRepository.delete(existingUser);
        return mapUserToUserDto(existingUser);
    }

    public User findUserById(Long id) throws BankUserException {
        if (id == null || id < 0) {
            throw new BankUserException(INVALID_INPUT, HttpStatus.BAD_REQUEST);
        }
        return userRepository.findById(id).orElseThrow(() -> new BankUserException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
    }

    public User findUserByMobileNumber(String mobileNumber) throws BankUserException {
        if (StringUtils.isEmpty(mobileNumber) || !Pattern.matches("[1-9][0-9]{9}", mobileNumber)) {
            throw new BankUserException(INVALID_INPUT, HttpStatus.BAD_REQUEST);
        }
        return userRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new BankUserException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
    }

    public User findUserByEmail(String email) throws BankUserException {
        if (StringUtils.isEmpty(email) || !Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^A-Za-z0-9]).{8,}$", email)) {
            throw new BankUserException(INVALID_INPUT, HttpStatus.BAD_REQUEST);
        }
        return userRepository.findByEmail(email).orElseThrow(() -> new BankUserException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
    }


    public static List<UserDto> mapUserToUserDto(List<User> users) {
        if (users == null) {
            return null;
        }
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(mapUserToUserDto(user));
        }
        return userDtoList;
    }

    public static UserDto mapUserToUserDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .dob(user.getDob())
                .build();
    }

    public static User mapUserDtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .mobileNumber(userDto.getMobileNumber())
                .dob(userDto.getDob())
                .build();
    }
}
