package se.systementor.javasecstart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.systementor.javasecstart.model.DogRepository;
import se.systementor.javasecstart.utils.DataSeeder;

import java.net.URISyntaxException;

@Component
public class SeedDataRunner implements CommandLineRunner {
    @Autowired
    DataSeeder dataSeeder;

    @Override
    public void run(String... args) throws Exception {

        setupDogs();
        
    }

    private void setupDogs() throws URISyntaxException {
        dataSeeder.Seed();
    }
}
