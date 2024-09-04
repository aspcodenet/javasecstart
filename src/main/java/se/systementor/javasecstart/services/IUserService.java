package se.systementor.javasecstart.services;

import se.systementor.javasecstart.registration.token.VerificationToken;
import se.systementor.javasecstart.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    String registerUser(String email, String firstName, String lastName, String password);

    Optional<User> findByEmail(String email);

    void saveUserVerificationToken(User theUser, String verificationToken);

    String validateToken(String theToken);

    VerificationToken generateNewVerificationToken(String oldToken);

    void createPasswordResetTokenForUser(User user, String passwordToken);

    String validatePasswordResetToken(String passwordResetToken);

    User findUserByPasswordToken(String passwordResetToken);

    void resetUserPassword(User user, String newPassword);
}
