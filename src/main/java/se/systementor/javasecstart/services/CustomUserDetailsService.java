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
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Optional<User> optionalUser = userRepository.findByEmail(username);
        //System.out.println(username);
        User user = optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("Användaren hittas inte: " + username)
        );

        // Return UserDetails with retrieved user data
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")  // Adjust roles as needed
                .build();
    }
}