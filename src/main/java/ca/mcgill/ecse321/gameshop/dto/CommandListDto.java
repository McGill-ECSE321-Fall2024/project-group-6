package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

public class CommandListDto {
    private List<CommandResponseDto> commands;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private CommandListDto() {
    }

    public CommandListDto(List<CommandResponseDto> commands){
        this.commands = commands;
    }

    public List<CommandResponseDto> getCommands(){
        return commands;
    }
}
