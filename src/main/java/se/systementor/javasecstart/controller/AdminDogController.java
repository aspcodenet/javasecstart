package se.systementor.javasecstart.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.model.DogRepository;
import se.systementor.javasecstart.services.DogService;
import se.systementor.javasecstart.viewmodels.NewDogViewModel;

@Controller
public class AdminDogController {
    @Autowired
    private DogService dogService;
    @Autowired
    private DogRepository dogRepository;
    @GetMapping(path="/admin/dogs")
    String list(Model model){
        model.addAttribute("activeFunction", "home");
//        setupVersion(model);

        model.addAttribute("dogs", dogService.getPublicDogs());
        return "admin/dogs/list";
    }

    @PostMapping(path="/admin/dogs")
    String newDog(@Valid @RequestBody NewDogViewModel dog, Model model){

        model.addAttribute("activeFunction", "home");
//        setupVersion(model);


        Dog newDog = new Dog();
        newDog.setName(dog.getName());
        newDog.setBreed(dog.getBreed());
        newDog.setAge(dog.getAge());
        newDog.setImage("/images/dogs/dog.0.jpg");
        newDog.setSize(dog.getSize());
        dogRepository.save(newDog);
        model.addAttribute("dogs", dogService.getPublicDogs());
        return "admin/dogs/list";
    }



    @GetMapping(path="/admin/dogs/edit/{id}")
    String list(@PathVariable long id, Model model){
        Dog dog = dogRepository.findById(id).get();
        model.addAttribute("activeFunction", "home");
//        setupVersion(model);

        model.addAttribute("dog", dog);
        return "admin/dogs/edit";
    }


}
