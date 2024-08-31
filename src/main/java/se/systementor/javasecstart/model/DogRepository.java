package se.systementor.javasecstart.model;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DogRepository extends CrudRepository<Dog, Long> {

    List<Dog> findAllBySoldToIsNull();

    List<Dog> findAllByNameContainsOrBreedContainsOrAgeContainsOrSizeContains(
            String name, String breed, String age, String size, Sort sort);

    List<Dog> findAllByPrice(Integer price, Sort sort);
}