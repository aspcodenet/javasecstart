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

    public List<Dog> getPublicDogs() {
        return dogRepository.findAllBySoldToIsNull();
    }

    public void sortDogs(List<Dog> dogs, String sortField, String sortOrder) {
        Collator sortingCollator = Collator.getInstance(new Locale("sv", "SE"));
        sortingCollator.setStrength(Collator.PRIMARY);

        Comparator<Dog> comparator;

        switch (sortField.toLowerCase()) {
            case "name":
                comparator = Comparator.comparing(Dog::getName, sortingCollator);
                break;
            case "breed":
                comparator = Comparator.comparing(Dog::getBreed, sortingCollator);
                break;
            case "age":
                comparator = Comparator.comparing(Dog::getAge, sortingCollator);
                break;
            case "size":
                comparator = Comparator.comparing(Dog::getSize, sortingCollator);
                break;
            case "price":
                comparator = Comparator.comparingInt(Dog::getPrice);
                break;
            default:
                comparator = Comparator.comparing(Dog::getName, sortingCollator);
                break;
        }

        if ("DESC".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        List<Dog> sortableList = new ArrayList<>(dogs);
        sortableList.sort(comparator);
        dogs.clear();
        dogs.addAll(sortableList);
    }

    public boolean isNumeric(String str) {
        try {
            Integer.parseInt(str.replace(" ", ""));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

