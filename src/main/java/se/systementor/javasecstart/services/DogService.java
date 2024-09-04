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
        Collator sortingCollator = Collator.getInstance(Locale.ENGLISH);
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
                comparator = new Comparator<Dog>() {          //icke lambda showcase
                    @Override
                    public int compare(Dog o1, Dog o2) {
                        return Integer.compare(getAgeCategoryPrio(o1.getAge()), getAgeCategoryPrio(o2.getAge()));
                    }
                };
                break;
            case "size":
                comparator = (o1, o2) -> Integer.compare(getSizeCategoryPrio(o1.getSize()), getSizeCategoryPrio(o2.getSize()));
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
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private int getAgeCategoryPrio(String age) {
        switch (age.toLowerCase()) {
            case "puppy":
                return 1;
            case "young":
                return 2;
            case "adult":
                return 3;
            case "senior":
                return 4;
            default:
                return Integer.MAX_VALUE;
        }
    }

        private int getSizeCategoryPrio (String size){
            switch (size.toLowerCase()) {
                case "small":
                    return 1;
                case "medium":
                    return 2;
                case "large":
                    return 3;
                case "extra large":
                    return 4;
                default:
                    return Integer.MAX_VALUE;


            }
        }
}

