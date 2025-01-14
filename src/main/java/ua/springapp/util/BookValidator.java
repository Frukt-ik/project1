package ua.springapp.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.springapp.models.Book;

@Component
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if(book.getYearOfPublication() == null){
            errors.rejectValue("yearOfPublication",
                    "book.yearOfPublication.invalid",
                    "Year Of Publication must be a valid number");
        }
    }
}
