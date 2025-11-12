package io.enscryptingbytes.banking_application.service;

import io.enscryptingbytes.banking_application.dto.UserDto;
import io.enscryptingbytes.banking_application.entity.User;
import io.enscryptingbytes.banking_application.exception.BankUserException;
import io.enscryptingbytes.banking_application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.enscryptingbytes.banking_application.message.ExceptionMessage.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {
    private final UserRepository userRepository;

    public UserDto createUser(UserDto user) {
        User existingUser = null;
        if (user.getMobileNumber() != null) {
            existingUser = getUserByMobileNumber(user.getMobileNumber());
        }
        if (existingUser != null) {
            throw new BankUserException(USER_ALREADY_EXISTS);
        }

        User newUser = mapUserDtoToUser(user);
        userRepository.save(newUser);
        return user;
    }

    private User getUserByMobileNumber(String mobileNumber) {
        Optional<User> userOptional = userRepository.findByMobileNumber(mobileNumber);
        return userOptional.orElse(null);
    }

    public UserDto getUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return null;
        } else {
            User user = userOptional.get();
            return mapUserToUserDto(user);
        }
    }

    public List<UserDto> getUsers() {
        List<User> allUser = userRepository.findAll();
        return mapUserToUserDto(allUser);

    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }
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

    public UserDto deleteUser(Long id) {
        return null;
    }

    public static List<UserDto> mapUserToUserDto(List<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(mapUserToUserDto(user));
        }
        return userDtoList;
    }

    public static UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .dob(user.getDob())
                .build();
    }

    public static User mapUserDtoToUser(UserDto user) {
        return User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .dob(user.getDob())
                .build();
    }
}
