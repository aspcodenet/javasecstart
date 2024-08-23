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

    private DogRepository dogRepository;
    @GetMapping(path="/admin/dogs")
    String list(Model model, @RequestParam(defaultValue = "name") String sortCol,
                @RequestParam(defaultValue = "ASC") String sortOrder,
                @RequestParam(defaultValue = "") String q) {

        q = q.trim();

        List<Dog> dogList;

        int qint = Integer.parseInt(q);

        if (!q.isEmpty()) {
            List<Dog> filteredDogList = dogRepository.findAllByNameContainsOrBreedContainsOrAgeContainsOrPriceContains(q,
                    q, q, qint, Sort.unsorted());
            dogList = new ArrayList<>(filteredDogList);
        } else {
            dogList = dogService.getPublicDogs();
        }
        List <Dog> sortedList = new ArrayList<>(dogList);

        dogService.sortDogs(sortedList, sortCol, sortOrder);
       //  sortedList = dogService.sortDogs(sortedList, sortCol, sortOrder);

        model.addAttribute("activeFunction", "home");
//        setupVersion(model);
        model.addAttribute("dogs", sortedList);
        model.addAttribute("q", q);

        return "admin/dogs/list";
    }


}
