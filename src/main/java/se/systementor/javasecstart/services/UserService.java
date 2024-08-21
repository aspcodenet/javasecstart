package se.systementor.javasecstart.services;


import groovy.transform.Final;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.systementor.javasecstart.model.User;
import se.systementor.javasecstart.model.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    //@Autowired
    private final UserRepository userRepository;
    //@Autowired
    private final PasswordEncoder passwordEncoder;

    public void registerNewUser(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Användarnamnet är redan taget");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, password);

        userRepository.save(newUser);
    }


    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
