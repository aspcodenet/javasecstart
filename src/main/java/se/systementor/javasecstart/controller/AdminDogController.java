package se.systementor.javasecstart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.model.DogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.systementor.javasecstart.services.DogService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AdminDogController {

    @Autowired
    private DogService dogService;

    private final DogRepository dogRepo;

    List<String> genders_List = Arrays.asList("male","female","neutered male", "spayed female");

  /*  @GetMapping(path="/admin/dogs")
    String list(Model model) {*/

 /*   @Autowired
    private DogRepository dogRepository;*/

    @GetMapping(path = "/admin/dogs")
    String list (Model model, @RequestParam(defaultValue = "name") String sortCol,
            @RequestParam(defaultValue = "ASC") String sortOrder,
            @RequestParam(defaultValue = "") String q){
        q = q.trim();

        List<Dog> dogList;


        if (!q.isEmpty()) {
            if (dogService.isNumeric(q)) {
                Integer price = Integer.parseInt(q);
                dogList = dogRepo.findAllByPrice(price, Sort.unsorted());
            } else {
                dogList = dogRepo.findAllByNameContainsOrBreedContainsOrAgeContainsOrSizeContains(
                        q, q, q, q, Sort.unsorted());
            }
        } else {
            dogList = dogService.getPublicDogs();
        }

        dogService.sortDogs(dogList, sortCol, sortOrder);

        model.addAttribute("activeFunction", "home");
        model.addAttribute("dogs", dogList);
        model.addAttribute("q", q);

      //  model.addAttribute("dogs", dogService.getPublicDogs());
        return "/admin/dogs/list";
    }


    @RequestMapping("/admin/dogs/edit/{id}")
    public String editDog(@PathVariable int id, Model model){

        model.addAttribute("dogId", id);
        Dog dog = dogRepo.findById((long) id).orElse(null);
        model.addAttribute("dog", dog);
        model.addAttribute("genders", genders_List);
        return "/admin/dogs/edit";
    }

    @PostMapping("/admin/dogs/save")
    //public String saveDog(@Valid @ModelAttribute("dog") Dog dog, BindingResult result, Model model, @RequestParam String redirect ) {
    public String saveDog(@Valid @ModelAttribute("dog") Dog dog, BindingResult result, Model model) {

        System.out.println("Save Dog..");
        // System.out.println("red1: " + redirect);
        if (result.hasErrors()) {
            System.out.println("Form errrorr ");
            model.addAttribute("genders", genders_List);
         /*   model.addAttribute("kat", "Lägg till ny kund");
            model.addAttribute("titel", "Kund");
            model.addAttribute("redirect", redirect);
            model.addAttribute("cancelRedirect", redirect);*/
            return "/admin/dogs/edit";
        }
        System.out.println("Edited Dog gender.."+dog.getGender());
        dogRepo.save(dog);
        return "redirect:/admin/dogs";
    }
}
