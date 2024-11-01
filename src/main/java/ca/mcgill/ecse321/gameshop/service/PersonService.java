package ca.mcgill.ecse321.gameshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonService {
    // Inject PersonRepository to handle database operations
    @Autowired
    private PersonRepository personRepo;

    // Create a new person and save it in the repository
    @Transactional
    public Person createPerson(Person person) {
        return personRepo.save(person);
    }

    // Retrieve all people from the repository
    public Iterable<Person> getAllPeople() {
        return personRepo.findAll();
    }

    // Find a person by their ID
    public Person getPersonByID(int id) {
        Person p = personRepo.findPersonByUserId(id);

        // Throw an exception if no person is found
        if (p == null) {
            throw new IllegalArgumentException("Person with ID " + id + " does not exist.");
        }

        return p;
    }

    // Update an existing person's details by ID
    @Transactional
    public Person updatePerson(int id, Person personDetails) {
        Person p = personRepo.findPersonByUserId(id);

        if (p == null) {
            throw new IllegalArgumentException("Person with ID " + id + " does not exist.");
        }

        p.setEmail(personDetails.getEmail());
        p.setPassword(personDetails.getPassword());
        p.setPhone(personDetails.getPhone());
        p.setUsername(personDetails.getUsername());

        return personRepo.save(p);
    }

    // Delete a person by their ID
    @Transactional
    public void deletePerson(int id) {
        Person p = personRepo.findPersonByUserId(id);

        if (p == null) {
            throw new IllegalArgumentException("Person with ID " + id + " does not exist.");
        }

        personRepo.delete(p);
    }
}
