package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

import ca.mcgill.ecse321.gameshop.model.Game;
import jakarta.validation.constraints.NotBlank;

public class GuestRequestDto {
    @NotBlank(message="A list of games is required.")
    private List<Game> games;

    @SuppressWarnings("unused")
    private GuestRequestDto() {
    }

    public GuestRequestDto(List<Game> games) {
        this.games = games;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
