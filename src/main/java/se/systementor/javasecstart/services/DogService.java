package se.systementor.javasecstart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.model.DogRepository;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class DogService {
    @Autowired
    DogRepository dogRepository;

    public List<Dog> getPublicDogs(){
        return dogRepository.findAllBySoldToIsNull();
    }

    public void sortDogs(List<Dog> dogs, String sortField, String sortOrder) {
        Collator sortingCollator = Collator.getInstance(new Locale("sv", "SE"));
        sortingCollator.setStrength(Collator.PRIMARY);

        Comparator<Dog> comparator = Comparator.comparing(dog -> {
            switch (sortField.toLowerCase()) {
                case "name":
                    return dog.getName();
                case "breed":
                    return dog.getBreed();
                case "age":
                    return dog.getAge();
                case "size":
                    return dog.getSize();
              //  case "price":
              //      return dog.getPrice();
                default:
                    return dog.getName();
            }
        }, sortingCollator);


        if ("DESC".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }
        List<Dog> sortableList = new ArrayList<>(dogs);
        sortableList.sort(comparator);
        dogs.clear();
        dogs.addAll(sortableList);
    }
    }

