package ua.springapp.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.springapp.dao.PersonDAO;
import ua.springapp.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(personDAO.checkUniqueOfFullName(person.getFullName(), person.getId()).isPresent()){
            errors.rejectValue("fullName", "person.exists" , "Full name should be unique");
        }

        if(person.getYearOfBorn() == null){
            errors.rejectValue("yearOfBorn",
                    "person.yearOfBorn.invalid",
                    "Year Of Born must be a valid number");
        }
    }
}
