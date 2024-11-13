package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

public class GameListDto {
    private List<GameResponseDto> games;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private GameListDto() {
    }

    public GameListDto(List<GameResponseDto> games) {
        this.games = games;
    }

    public List<GameResponseDto> getGames() {
        return games;
    }

    public void setGames(List<GameResponseDto> games) {
        this.games = games;
    }
}