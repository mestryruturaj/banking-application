package io.enscryptingbytes.banking_application.security;

import io.enscryptingbytes.banking_application.entity.User;
import io.enscryptingbytes.banking_application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static io.enscryptingbytes.banking_application.message.ExceptionMessage.USER_DOES_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> existingUserOptional = userRepository.findByEmail(username);
        if (existingUserOptional.isEmpty()) {
            throw new UsernameNotFoundException(USER_DOES_NOT_EXISTS);
        }

        return new BankUserDetails(existingUserOptional.get());
    }
}
