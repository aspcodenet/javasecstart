package se.systementor.javasecstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.services.DogService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PublicDogsController {
    @Autowired
    private DogService dogService;
    @GetMapping(path="/dogs")
    String list(Model model){
        model.addAttribute("activeFunction", "publicdogs");

        model.addAttribute("dogs", dogService.getPublicDogs());
        return "dogs";
    }

    @GetMapping("/search")
    public String searchAndSortDogs(@RequestParam(name = "q", required = false) String query,
                                    @RequestParam(name = "sortBy", required = false, defaultValue = "name") String sortBy,
                                    Model model) {
        String q = query.trim();
        List<Dog> allDogs = dogService.getPublicDogs();

        Optional<Integer> priceQuery = Optional.empty();
        try {
            priceQuery = Optional.of(Integer.parseInt(query));
        } catch (NumberFormatException e) {

        }
        Optional<Integer> finalPriceQuery = priceQuery;

        List<Dog> filteredDogs = allDogs.stream()
                .filter(dog -> dog.getName().toLowerCase().contains(query.toLowerCase())
                        || dog.getBreed().toLowerCase().contains(query.toLowerCase())
                        || String.valueOf(dog.getAge()).contains(query)
                        || (finalPriceQuery.isPresent() && dog.getPrice() == finalPriceQuery.get()))
                .collect(Collectors.toList());

        List<Dog> sortedDogs = filteredDogs.stream()
                .sorted(getComparator(sortBy))
                .collect(Collectors.toList());

        model.addAttribute("dogs", sortedDogs);
        model.addAttribute("q", query);
        model.addAttribute("sortBy", sortBy);

        return "/dogs";
    }
    private Comparator<Dog> getComparator(String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "breed":
                return Comparator.comparing(Dog::getBreed);
            case "age":
                return Comparator.comparing(Dog::getAge);
            case "price":
                return Comparator.comparingInt(Dog::getPrice);
            case "name":
            default:
                return Comparator.comparing(Dog::getName);
        }


    }
}
