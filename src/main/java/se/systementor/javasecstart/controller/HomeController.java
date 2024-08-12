package se.systementor.javasecstart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(path="/")
    String empty(Model model)
    {
        model.addAttribute("activeFunction", "home");
//        setupVersion(model);

//        model.addAttribute("dogs", dogRepository.findAll());
        return "home";
    }



}
