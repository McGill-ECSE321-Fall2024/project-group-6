package ca.mcgill.ecse321.gameshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
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
    public Person createPerson(String aUsername, String aEmail, String aPassword, String aPhone) {
        Person p = new Person(aUsername, aEmail, aPassword, aPhone);
        return personRepo.save(p);
    }

    // Retrieve all people from the repository
    public Iterable<Person> getAllPeople() {
        return personRepo.findAll();
    }

    // Find a person by their ID
    public Person getPersonByUserId(int id) {
        Person p = personRepo.findPersonByUserId(id);

        // Throw an exception if no person is found
        if (p == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Person with ID " + id + " does not exist."));
        }

        return p;
    }

    // Update an existing person by ID
    @Transactional
    public Person updatePerson(int id, String aUsername, String aEmail, String aPassword, String aPhone) {
        Person p = personRepo.findPersonByUserId(id);

        if (p == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Person with ID " + id + " does not exist."));
        }

        p.setUsername(aUsername);
        p.setEmail(aEmail);
        p.setPassword(aPassword);
        p.setPhone(aPhone);

        return personRepo.save(p);
    }

    // Delete a person by their ID
    @Transactional
    public void deletePerson(int id) {
        Person p = personRepo.findPersonByUserId(id);

        if (p == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Person with ID " + id + " does not exist."));
        }
        
        personRepo.delete(p);
    }
}
