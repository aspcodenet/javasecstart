package se.systementor.javasecstart.viewmodels;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListDogViewModel {
    private int id;
    private String name;
    private String age;
    private String breed;
}
