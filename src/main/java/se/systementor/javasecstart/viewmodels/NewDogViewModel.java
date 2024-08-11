package se.systementor.javasecstart.viewmodels;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewDogViewModel {

    @Column(length = 50)
    private String name;
    private String breed;
    private String age;
    private String size;

    @Min(0)
    @Max(30000)
    private int price;
}
