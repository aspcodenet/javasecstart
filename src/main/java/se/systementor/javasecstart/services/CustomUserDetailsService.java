package se.systementor.javasecstart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.systementor.javasecstart.model.User;
import se.systementor.javasecstart.model.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService2 {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        //User user = userRepository.findByEmail2(email);
        if (user == null) {
            throw new UsernameNotFoundException("Användaren hittas inte: " + email);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .roles("USER")
                .build();
    }
}
