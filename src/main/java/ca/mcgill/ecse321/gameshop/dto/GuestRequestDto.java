package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.model.Guest;

public class GuestRequestDto {
    private List<Game> games;

    public GuestRequestDto(Guest guest) {
        this.games = guest.getGames();
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
