package se.systementor.javasecstart.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import se.systementor.javasecstart.event.RegistrationCompleteEvent;
import se.systementor.javasecstart.model.User;
import se.systementor.javasecstart.registration.RegistrationRequest;
import se.systementor.javasecstart.services.UserService;

@Controller
@RequiredArgsConstructor

public class LoginController {

    //@Autowired
    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @GetMapping(path="/login")
    public String login(Model model) {
        model.addAttribute("activeFunction", "login");
        return "login";
    }

    @GetMapping(path="/regUser")
    public String showRegForm(Model model) {
        model.addAttribute("activeFunction", "regUser");
        model.addAttribute("user", new User());
        return "regUser";
    }

    @PostMapping("/regUser")
    public String regUser(@ModelAttribute User user, Model model) {
        System.out.println(user.getEmail());
        try {
            userService.registerUser(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword());
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/regUser";
        }
    }



    public String applicationUrl(HttpServletRequest request){
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
