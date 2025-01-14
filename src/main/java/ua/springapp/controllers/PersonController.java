package ua.springapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.springapp.dao.PersonDAO;
import ua.springapp.models.Person;
import ua.springapp.util.PersonValidator;

import java.beans.PropertyEditorSupport;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people",personDAO.getPeople());
        return "person/person_index";
    }

    @GetMapping("/{id}")
    public String person(@PathVariable("id") int id,
                         Model model) {
        model.addAttribute("person", personDAO.getPerson(id));
        model.addAttribute("BooksActual",personDAO.booksThatAssignPerson(id));
        return "person/person_for_id";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id,
                               @ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/person/person_edit";
        }
        personDAO.updatePerson(id, person);
        return "redirect:/people/" + id;
    }

    @GetMapping("/{id}/edit")
    public String personEdit(@PathVariable("id") int id,
                             Model model) {
        model.addAttribute("person", personDAO.getPerson(id));
        return "person/person_edit";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "person/person_new";
    }

    @PostMapping
    public String addPerson(@ModelAttribute("person") @Valid Person person,
                            BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "person/person_new";
        }
        personDAO.addPerson(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.deletePerson(id);
        return "redirect:/people";
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
