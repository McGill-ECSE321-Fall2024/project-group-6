package ca.mcgill.ecse321.gameshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gameshop.dto.PersonListDto;
import ca.mcgill.ecse321.gameshop.dto.PersonRequestDto;
import ca.mcgill.ecse321.gameshop.dto.PersonResponseDto;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.service.PersonService;


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
    @PostMapping("/person")
    public PersonResponseDto createPerson(@RequestBody PersonRequestDto person) {
        Person p = personService.createPerson(person.getUsername(), person.getEmail(), person.getPassword(), person.getPhone());
        return new PersonResponseDto(p);
    }
    
    /**
     * Return all people.
     *
     * @return All the people.
     */
    @GetMapping("/person")
    public PersonListDto getAllPeople() {
        List<PersonResponseDto> people = new ArrayList<>();

        for (Person p: personService.getAllPeople()) {
            people.add(new PersonResponseDto(p));
        }
        
        return new PersonListDto(people);
    }
    
    /**
     * Return the person with the given ID.
     *
     * @param id The primary key of the person to find.
     * @return The person with the given ID.
     */
    @GetMapping("/person/{id}")
    public PersonResponseDto getPersonByID(@PathVariable int id) {
        Person p = personService.getPersonByID(id);
        return new PersonResponseDto(p);
    }

    /**
     * Update the person with the given ID.
     *
     * @param id The primary key of the person to find.
     * @return The updated person with the given ID.
     */
    @PutMapping("/person/{id}")
    public PersonResponseDto updatePerson(@PathVariable int id, @RequestBody PersonRequestDto person) {
        Person p = personService.updatePerson(id, person.getUsername(), person.getEmail(), person.getPhone(), person.getPassword());
        return new PersonResponseDto(p);
    }

    /**
     * Delete the person with the given ID.
     *
     * @param id The primary key of the person to find.
     * @return void.
     */
    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable int id) {
        personService.deletePerson(id);
    }
}