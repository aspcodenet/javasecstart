package se.systementor.javasecstart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="Dog")
public class Dog {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private int id;

    @NotBlank(message = "Enter age")
    @Pattern(regexp="(^$|^[A-Za-zåäöÅÄÖ0-9 ]+$)", message= "Only letters and numbers")
    @Size(max = 20, message = "Max 20 long")
    @Column(name="Age", nullable = false)
    private String age;

    @Column(name="Gender")
    private String gender;

    @NotBlank(message = "Enter breed")
    @Pattern(regexp="(^$|^[A-Za-zåäöÅÄÖ ]+$)", message= "Only letters")
    @Size(max = 30, message = "Max 30 characters")
    @Column(name = "Breed", nullable = false)
    private String breed;

    @Column(name="SoldTo")
    private String soldTo;

    @NotNull(message = "Enter price")
    @Min(value=1, message="Price must be higher than 0")
    @Max(value=9999999, message="Price must be lower than 10000000")
    @Column(name="Price")
    private int price;

    @NotBlank(message = "Enter name")
    @Pattern(regexp = "(^$|^[A-Za-zåäöÅÄÖ ]+$)", message = "Name can only contain letters")
    @Size(max = 50, message = "Max 50 characters")
    @Column(name="Name", nullable = false)
    private String name;

    @NotBlank(message = "Enter size")
    @Pattern(regexp = "(^$|^[A-Za-zåäöÅÄÖ]+$)", message = "Size can only contain letters")
    @Size(max = 20, message = "Max 20 characters")
    @Column(name="Size", nullable = false)
    private String size;


    @Column(name="Image")
    private String image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String hender) {
        this.gender = hender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSoldTo() {
        return soldTo;
    }

    public void setSoldTo(String soldTo) {
        this.soldTo = soldTo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}