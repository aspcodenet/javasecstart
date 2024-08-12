package se.systementor.javasecstart.controller;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.model.DogRepository;
import se.systementor.javasecstart.viewmodels.ListDogViewModel;
import se.systementor.javasecstart.viewmodels.NewDogViewModel;

@RestController
public class ApiController {
    @Autowired
    private DogRepository dogRepository;


    @Autowired
    private EntityManager entityManager;

    @GetMapping("/api/dogs")
    public Iterable<ListDogViewModel> dogs(Model model) {
        return dogRepository.findAllBySoldToIsNull().stream()
                .map(dog -> ListDogViewModel.builder()
                        .id(dog.getId())
                        .breed(dog.getBreed())
                        .age(dog.getAge())
                        .name(dog.getName())
                        .build()).toList();
    }


    @GetMapping("/api/dogs2")
    public Iterable<ListDogViewModel> dogs2(Model model,@RequestParam(name="breed",required = false)  String breed) {
        // SQL
        TypedQuery<Dog> query = entityManager.createQuery("from Dog where breed = '" + breed + "'",Dog.class);
        return query.getResultList().stream()
                .map(dog -> ListDogViewModel.builder()
                        .id(dog.getId())
                        .breed(dog.getBreed())
                        .age(dog.getAge())
                        .name(dog.getName())
                        .build()).toList();
    }


}
