package ca.mcgill.ecse321.gameshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.service.PersonService;


@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/person/{id}")
    public Person findPersonByID(@PathVariable int id) {
        Person p = personService.getPersonByID(id);
        return p;
    }
    
}
