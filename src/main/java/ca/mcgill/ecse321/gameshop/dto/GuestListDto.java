package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

public class GuestListDto {
    private List<GuestResponseDto> guests;

    public GuestListDto(List<GuestResponseDto> guests) {
        this.guests = guests;
    }

    public List<GuestResponseDto> getGuests() {
        return guests;
    }

    public void setGuests(List<GuestResponseDto> guests) {
        this.guests = guests;
    }
}
