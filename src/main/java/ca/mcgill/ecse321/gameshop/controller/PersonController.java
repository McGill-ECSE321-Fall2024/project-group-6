package ca.mcgill.ecse321.gameshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gameshop.dto.PersonResponseDto;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.service.PersonService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    /**
     * Create a new person.
     *
     * @param person The person to create.
     * @return The created person, including their ID.
     */
    @PostMapping("/person/{id}")
    public PersonResponseDto createPerson(@RequestBody PersonResponsetDto person) {
        Person personCreated = personService.createPerson(person);
        return entity;
    }
    

    @GetMapping
    public Iterable<Person> getAllPeople() {
        return personService.getAllPeople();
    }
    
    /**
     * Return the person with the given ID.
     *
     * @param id The primary key of the person to find.
     * @return The person with the given ID.
     */
    @GetMapping("/person/{id}")
    public PersonResponseDto findPersonByID(@PathVariable int id) {
        Person p = personService.getPersonByID(id);
        return new PersonResponseDto(p);
    }
    
}
