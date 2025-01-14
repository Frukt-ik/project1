package ua.springapp.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class Book {

    private int id;

    private Integer personId;

    @Size(min=2, max=255 , message = "Size of name should be between 2 and 255")
    private String name;

    @Size(min=2, max=100 , message = "Size of author name should be between 2 and 100")
    private String author;

    @Min(value = -2000, message = "Year of publication should be bigger then -2000")
    private Integer yearOfPublication;

    public Book(){}

    public Book(int id, Integer personId, String name, String author, Integer yearOfPublication) {
        this.id = id;
        this.personId = personId;
        this.name = name;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }
}
