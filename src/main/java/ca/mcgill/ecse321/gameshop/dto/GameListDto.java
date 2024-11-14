package ca.mcgill.ecse321.gameshop.dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import ca.mcgill.ecse321.gameshop.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameListDto {

    private List<GameResponseDto> games;

    public GameListDto(List<GameResponseDto> games) {
        this.games=games;
    }

    public List<GameResponseDto> getGames() {
        return games;
    }

    public void setGames(List<GameResponseDto> games) {
        this.games = games;
    }
}