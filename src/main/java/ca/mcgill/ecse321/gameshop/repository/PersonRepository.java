package ca.mcgill.ecse321.gameshop.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    public Person findPersonByUserId(int userId);
    public Person findPersonByEmail(String email);
}
