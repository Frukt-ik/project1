package ua.springapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.springapp.dao.BookDAO;
import ua.springapp.dao.PersonDAO;
import ua.springapp.models.Book;
import ua.springapp.models.Person;
import ua.springapp.util.BookValidator;

import java.beans.PropertyEditorSupport;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO,
                          PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String books(Model model) {
        model.addAttribute("books",bookDAO.getBooks());
        return "book/book_index";
    }

    @GetMapping("/{id}")
    public String book(@PathVariable("id") int id,
                       @ModelAttribute("person") Person person,
                       Model model) {

        model.addAttribute("book", bookDAO.getBook(id));
        model.addAttribute("people", personDAO.getPeople());
        model.addAttribute("personActual", bookDAO.personThatAssignBook(id));
        return "book/book_for_id";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id,
                             @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "book/book_edit";
        }
        bookDAO.updateBook(id, book);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assignBook( @PathVariable("id") int id,
                              @ModelAttribute("person") Person person) {
//        System.out.println(person.getId());
        bookDAO.assignBookToUser(person.getId(), id);

//        return "redirect:/books";
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        bookDAO.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String bookEdit(@PathVariable("id") int id,
                           Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "book/book_edit";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "book/book_new";
    }

    @PostMapping
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "book/book_new";
        }
        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.trim().isEmpty()) {
                    setValue(null); // Дозволяємо null для порожніх рядків
                } else {
                    try {
                        setValue(Integer.parseInt(text)); // Пробуємо конвертувати
                    } catch (NumberFormatException e) {
                        setValue(null); // Якщо конверсія не вдалася, залишаємо поле null
                    }
                }
            }
        });
    }

}
