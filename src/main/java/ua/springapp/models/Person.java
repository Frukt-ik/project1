package ua.springapp.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Person {

    private int id;

    @Pattern(regexp = "^[A-ZА-ЯІЇЄ][a-zа-яіїє']+ [A-ZА-ЯІЇЄ][a-zа-яіїє']+ [A-ZА-ЯІЇЄ][a-zа-яіїє']+$",
            message = "first name, middle name and last name must be capitalized and separated by a space")
    @NotEmpty(message = "Name is required!")
    @Size(min=2, max=255 , message = "Size of full name should be between 2 and 255")
    private String fullName;

    @Min(value = 1900, message = "Age should be bigger then 1900")
    private Integer yearOfBorn;

    public Person(){}

    public Person(int id, String fullName, Integer yearOfBorn) {
        this.id = id;
        this.fullName = fullName;
        this.yearOfBorn = yearOfBorn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getYearOfBorn() {
        return yearOfBorn;
    }

    public void setYearOfBorn(Integer yearOfBorn) {
        this.yearOfBorn = yearOfBorn;
    }
}
