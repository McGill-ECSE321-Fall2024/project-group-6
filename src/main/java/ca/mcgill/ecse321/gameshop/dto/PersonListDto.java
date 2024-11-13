package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

public class PersonListDto {
    private List<PersonResponseDto> people;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private PersonListDto() {
    }

    public PersonListDto(List<PersonResponseDto> people) {
        this.people = people;
    }

    public List<PersonResponseDto> getPeople() {
        return people;
    }
}
