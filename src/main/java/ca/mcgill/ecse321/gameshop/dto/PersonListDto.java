package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

public class PersonListDto {
    private List<PersonResponseDto> people;

    public PersonListDto(List<PersonResponseDto> people) {
        this.people = people;
    }

    public List<PersonResponseDto> getPeople() {
        return people;
    }
}
