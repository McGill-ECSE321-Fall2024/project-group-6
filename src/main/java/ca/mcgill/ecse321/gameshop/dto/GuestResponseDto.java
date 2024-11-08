package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Guest;

public class GuestResponseDto {
    private int guestId;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private GuestResponseDto() {
    }

    public GuestResponseDto(Guest guest) {
        this.guestId = guest.getGuestId();
    }

    public int getGuestId() {
        return guestId;
    }
}
