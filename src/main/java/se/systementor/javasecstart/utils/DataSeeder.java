package se.systementor.javasecstart.utils;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.model.DogRepository;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Paths.get;

@Component
public class DataSeeder {
    @Autowired
    DogRepository dogRepository;

    Faker faker = new Faker();

    public void Seed() throws URISyntaxException {
        if(dogRepository.count() > 0 ){
            return;
        }
        for(int i =0; i < 100; i++) {
            dogRepository.save(RandomDog());
        }
    }

    private Dog RandomDog() throws URISyntaxException {
        Dog dog = new Dog();
        dog.setAge(faker.dog().age());
        dog.setName(faker.dog().name());
        dog.setBreed(faker.dog().breed());
        dog.setGender(faker.dog().gender());
        dog.setPrice(faker.random().nextInt(4,20) * 1000);
        dog.setSize(faker.dog().size());
        dog.setImage("/images/dogs/" + getRandomImage());
        return dog;
    }

    public String getRandomImage() throws URISyntaxException {
        //Get all files in dir
        URL resource = getClass().getClassLoader().getResource("static/images/dogs");
        Path dir = Paths.get(resource.toURI());

        Set<String> allFiles =  Stream.of(new File(String.valueOf(dir)).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());

        return getByRandomClass(allFiles);
    }


    public static <T> T getByRandomClass(Set<T> set) {
        if (set == null || set.isEmpty()) {
            throw new IllegalArgumentException("The Set cannot be empty.");
        }
        int randomIndex = new Random().nextInt(set.size());
        int i = 0;
        for (T element : set) {
            if (i == randomIndex) {
                return element;
            }
            i++;
        }
        throw new IllegalStateException("Something went wrong while picking a random element.");
    }
}