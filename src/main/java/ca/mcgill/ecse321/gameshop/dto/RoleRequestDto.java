package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Person;

public class RoleRequestDto {
    //Person Attributes
    private Person person;

    public RoleRequestDto(Person person) {
        this.person=person;

    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person= person;
    }
}