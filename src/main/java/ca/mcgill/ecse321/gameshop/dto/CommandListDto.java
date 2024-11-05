package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

public class CommandListDto {
    private List<CommandResponseDto> commands;

    public CommandListDto(List<CommandResponseDto> commands){
        this.commands=commands;
    }

    public List<CommandResponseDto> getCommands(){
        return commands;
    }

    public void setCategories(List<CommandResponseDto> commands){
        this.commands=commands;
    }

}