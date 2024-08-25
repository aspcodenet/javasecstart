package se.systementor.javasecstart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
        return "/admin/dogs/list";
    }

    @RequestMapping("/admin/dogs/edit/{id}")
    public String editDog(@PathVariable int id, Model model){
        model.addAttribute("dogId", id);
        Dog dog = dogRepo.findById((long) id).orElse(null);
        model.addAttribute("dog", dog);
        return "/admin/dogs/edit";
    }

    @PostMapping("/admin/dogs/save")
    //public String saveDog(@Valid @ModelAttribute("dog") Dog dog, BindingResult result, Model model, @RequestParam String redirect ) {
    public String saveDog(@Valid @ModelAttribute("dog") Dog dog, BindingResult result, Model model) {

        System.out.println("Save Dog..");
        // System.out.println("red1: " + redirect);
        if (result.hasErrors()) {
            System.out.println("Form errrorr ");
         /*   model.addAttribute("kat", "Lägg till ny kund");
            model.addAttribute("titel", "Kund");
            model.addAttribute("redirect", redirect);
            model.addAttribute("cancelRedirect", redirect);*/
            return "/admin/dogs/edit";
        }
        dogRepo.save(dog);
        //return "redirect:" + redirect;
        return "redirect:/admin/dogs";
    }
}
