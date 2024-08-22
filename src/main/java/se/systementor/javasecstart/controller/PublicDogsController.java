package se.systementor.javasecstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.services.DogService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PublicDogsController {
    @Autowired
    private DogService dogService;
    @GetMapping(path="/dogs")
    String list(Model model){
        model.addAttribute("activeFunction", "publicdogs");
//        setupVersion(model);

        model.addAttribute("dogs", dogService.getPublicDogs());
        return "dogs";
    }

    @GetMapping("/search")
    public String searchDogs(@RequestParam(name = "q", required = false) String query, Model model) {
        List<Dog> allDogs = dogService.getPublicDogs();

        if (query != null && !query.isEmpty()) {
            List<Dog> filteredDogs = allDogs.stream()
                    .filter(dog -> dog.getName().toLowerCase().contains(query.toLowerCase())
                            || dog.getBreed().toLowerCase().contains(query.toLowerCase())
                            || dog.getAge().toLowerCase().contains(query.toLowerCase())
                            || String.valueOf(dog.getPrice()).contains(query))
                    .collect(Collectors.toList());
            model.addAttribute("dogs", filteredDogs);
        } else {
            model.addAttribute("dogs", allDogs);
        }

        model.addAttribute("q", query);
        return "/dogs";
    }

}
