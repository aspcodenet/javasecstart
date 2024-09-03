package se.systementor.javasecstart.event.listener;

import se.systementor.javasecstart.event.RegistrationCompleteEvent;
import se.systementor.javasecstart.model.User;
import se.systementor.javasecstart.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;



import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;
    private final JavaMailSender mailSender;

    private User theUser;



    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        theUser = event.getUser();
        String verificationToken = UUID.randomUUID().toString();
        userService.saveUserVerificationToken(theUser, verificationToken);
        String url = event.getApplicationUrl() + "/register/verifyEmail?token=" + verificationToken;
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to complete your registration : {}", url);

    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email verifiering";
        String senderName = "Stefans Hundhem";
        String mailContent = "<p> Hej, " + theUser.getFirstName() + ", </p>" +
                "<p> Tack för att du registrerade dig hos Stefans Hundhem,"+"" +
                "Vänligen klicka på länken för att fullfölja din registrering. </p>"+
                "<a href=\"" +url+ "\">Verifiera din email för att aktivera ditt konto</a>"+
                "<p> Tack <br> Stefans Hundhem.";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("o.ekstrom@hotmail.se", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    public void sendPasswordResetVerificationEmail(String url, User user) throws MessagingException, UnsupportedEncodingException {
        String subject = "Återställning av lösenord";
        String senderName = "Stefans Hundhem";
        String mailContent = "<p> Hej, " + user.getFirstName() + ", </p>" +
                "<p>Du har begärt återställning av ditt lösenord,"+"" +
                "<p>Har du inte begärt detta så kan du ignorera detta mailet." +
                "Vänligen klicka på länken för att återställa ditt lösenord. </p>"+
                "<a href=\"" +url+ "\">Återställning av lösenord</a>"+
                "<p> Tack <br> Stefans Hundhem.";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("o.ekstrom@hotmail.se", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }


}
