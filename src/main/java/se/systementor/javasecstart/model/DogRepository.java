package se.systementor.javasecstart.model;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DogRepository extends CrudRepository<Dog, Long> {

    List<Dog> findAllBySoldToIsNull();

    List<Dog> findAllByNameContainsOrBreedContainsOrAgeContainsOrPriceContains(
            String name, String breed, String age, int price, Sort sort);

}