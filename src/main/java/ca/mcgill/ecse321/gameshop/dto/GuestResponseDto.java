package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.model.Guest;

public class GuestResponseDto {
    private int guestId;
    private List<Game> games;

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

    public void setUserId(int id) {
        this.guestId = id;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
