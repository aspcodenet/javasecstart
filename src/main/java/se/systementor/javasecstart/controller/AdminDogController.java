package se.systementor.javasecstart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.model.DogRepository;
import se.systementor.javasecstart.services.DogService;

@Controller
@RequiredArgsConstructor
public class AdminDogController {

    @Autowired
    private DogService dogService;

    private final DogRepository dogRepo;

    @GetMapping(path="/admin/dogs")
    String list(Model model){
        model.addAttribute("activeFunction", "home");
//        setupVersion(model);

        model.addAttribute("dogs", dogService.getPublicDogs());
        return "admin/dogs/list";
    }

    @RequestMapping("/admin/dogs/edit/{id}")
    public String editDog(@PathVariable int id, Model model){
        model.addAttribute("dogId", id);
        Dog dog = dogRepo.findById((long) id).orElse(null);
        model.addAttribute("dog", dog);
        return "admin/dogs/edit";
    }

}
