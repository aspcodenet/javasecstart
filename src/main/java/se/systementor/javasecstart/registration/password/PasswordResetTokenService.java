package se.systementor.javasecstart.registration.password;

import se.systementor.javasecstart.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    public void createPasswordResetTokenForUser(User user, String passwordToken){
        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, user);
        passwordResetTokenRepository.save(passwordResetToken);
    }
    public String validatePasswordResetToken(String theToken){

            PasswordResetToken token = passwordResetTokenRepository.findByToken(theToken);
            if(token == null){
                return "Invalid password reset token.";
            }
            User user = token.getUser();
            Calendar calendar = Calendar.getInstance();
            if((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
                return "Link already expired, please click the link below to receive a new verification link.";
            }
            return "valid";
    }

    public Optional<User> findUserByPasswordToken(String passwordToken){
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordToken).getUser());
    }

}
