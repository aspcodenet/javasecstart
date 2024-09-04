package se.systementor.javasecstart.registration;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import se.systementor.javasecstart.event.RegistrationCompleteEvent;
import se.systementor.javasecstart.event.listener.RegistrationCompleteEventListener;
import se.systementor.javasecstart.registration.password.PasswordResetRequest;
import se.systementor.javasecstart.registration.password.PasswordResetToken;
import se.systementor.javasecstart.registration.password.PasswordResetTokenRepository;
import se.systementor.javasecstart.registration.token.VerificationToken;
import se.systementor.javasecstart.registration.token.VerificationTokenRepository;
import se.systementor.javasecstart.model.User;
import se.systementor.javasecstart.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;
    private final RegistrationCompleteEventListener eventListener;
    private final HttpServletRequest servletRequest;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @GetMapping(path="/new")
    public String showRegForm(Model model){
        model.addAttribute("activeFunction", "registerUser");
        model.addAttribute("user", new User());
        return "registerUser";
    }

    @PostMapping("/new")
    public String registerUser(@ModelAttribute User user, Model model, final HttpServletRequest request){
        try {
            userService.registerUser(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword());
            publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
            return "linkSent";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/registerUser";
        }
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token, Model model){
        String url = applicationUrl(servletRequest) + "/register/resend-verification-token?token=" + token;
        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getUser().isEnabled()){
            return "verified";
        }
        Calendar calendar = Calendar.getInstance();
        if(theToken.getExpirationTime().getTime() - calendar.getTime().getTime() <=0 && !theToken.getUser().isEnabled()){
            model.addAttribute("token", token);
            return "newToken";
        }
        String verificationResult = userService.validateToken(token);
        if(verificationResult.equalsIgnoreCase("valid")){
            return "redirect:/login";
        }
        return "Invalid verification link, <a href=\"" + url + "\"> Get a new verification link. </a>";
    }

    @GetMapping("/resend-verification-token")
    public String resendVerificationToken(@RequestParam("token") String oldToken, final HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User theUser = verificationToken.getUser();
        resendVerificationTokenEmail(theUser, applicationUrl(request), verificationToken);
        return "linkSent";
    }

    private void resendVerificationTokenEmail(User theUser, String applicationUrl, VerificationToken verificationToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl + "/register/verifyEmail?token=" + verificationToken.getToken();
        eventListener.sendVerificationEmail(url);
        log.info("Click the link to complete your registration : {}", url);
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(final HttpServletRequest request, @RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
        Optional<User> user = userService.findByEmail(email);
        String passwordResetUrl = "";
        if (user.isPresent()){
            User existingUser = user.get();
            if(userService.checkIfTokenExist(existingUser)) {
                PasswordResetToken prt = passwordResetTokenRepository.findAll().stream().filter(pt -> pt.getUser().getId()==existingUser.getId()).findAny().get();
                passwordResetTokenRepository.deleteById(prt.getId());
                String passwordResetToken = UUID.randomUUID().toString();
                userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
                passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(request), passwordResetToken);
            }
            else{
                String passwordResetToken = UUID.randomUUID().toString();
                userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
                passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(request), passwordResetToken);
            }
        }
        return "linkSent";
    }

    private String passwordResetEmailLink(User user, String applicationUrl, String passwordResetToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl + "/register/reset-password?token=" + passwordResetToken;
        eventListener.sendPasswordResetVerificationEmail(url, user);
        log.info("Click the link to reset your password : {}", url);
        return url;
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String passwordResetToken, Model model){
        String tokenValidationResult = userService.validatePasswordResetToken(passwordResetToken);
        if(!tokenValidationResult.equalsIgnoreCase("valid")){
            return "Invalid password reset token";
        }
        model.addAttribute("token", passwordResetToken);
        return "changePassword";
    }

    @PostMapping("/change-password")
    public String updatePassword(@RequestParam("token") String passwordResetToken, @RequestParam("password") String password){
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setNewPassword(password);
        String tokenValidationResult = userService.validatePasswordResetToken(passwordResetToken);
        if(!tokenValidationResult.equalsIgnoreCase("valid")){
            return "Invalid password reset token";
        }
        User user = userService.findUserByPasswordToken(passwordResetToken);
        if (user != null){
            userService.resetUserPassword(user, passwordResetRequest.getNewPassword());
            return "login";
        }

        return "Invalid password reset token";
    }

    public String applicationUrl(HttpServletRequest request){
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
