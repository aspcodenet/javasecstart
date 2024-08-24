package se.systementor.javasecstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.model.DogRepository;
import se.systementor.javasecstart.services.DogService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AdminDogController {
    @Autowired
    private DogService dogService;

    @Autowired
    private DogRepository dogRepository;

    @GetMapping(path = "/admin/dogs")
    String list(Model model, @RequestParam(defaultValue = "name") String sortCol,
                @RequestParam(defaultValue = "ASC") String sortOrder,
                @RequestParam(defaultValue = "") String q) {
        q = q.trim();

        List<Dog> dogList;

        if (!q.isEmpty()) {
            if (dogService.isNumeric(q)) {
                Integer price = Integer.parseInt(q.replace(" ", ""));
                dogList = dogRepository.findAllByPrice(price, Sort.unsorted());
            } else {
                dogList = dogRepository.findAllByNameContainsOrBreedContainsOrAgeContainsOrSizeContains(
                        q, q, q, q, Sort.unsorted());
            }
        } else {
            dogList = dogService.getPublicDogs();
        }

        dogService.sortDogs(dogList, sortCol, sortOrder);

        model.addAttribute("activeFunction", "home");
        model.addAttribute("dogs", dogList);
        model.addAttribute("q", q);

        return "admin/dogs/list";
    }
}