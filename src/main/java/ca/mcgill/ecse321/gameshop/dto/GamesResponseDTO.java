package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;
public class GamesResponseDTO {
    private List<GameResponseDTO> games;

    public GamesResponseDTO(List<GameResponseDTO> games) {
        this.games = games;
    }

    public List<GameResponseDTO> getGames() {
        return games;
    }

    public void setRoles(List<GameResponseDTO> games) {
        this.games = games;
    }
}

