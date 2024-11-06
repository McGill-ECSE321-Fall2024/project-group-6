package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.model.Guest;

public class GuestResponseDto {
    private int guestId;
    private List<Game> games;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private GuestResponseDto() {
    }

    public GuestResponseDto(Guest guest) {
        this.guestId = guest.getGuestId();
        this.games = guest.getGames();
    }

    public int getUserId() {
        return guestId;
    }

    public List<Game> getGames() {
        return games;
    }
}
