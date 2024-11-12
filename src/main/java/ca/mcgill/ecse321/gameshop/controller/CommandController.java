package ca.mcgill.ecse321.gameshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gameshop.dto.CommandListDto;
import ca.mcgill.ecse321.gameshop.dto.CommandRequestDto;
import ca.mcgill.ecse321.gameshop.dto.CommandResponseDto;
import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.service.CommandService;

@RestController
public class CommandController {
    @Autowired
    private CommandService commandService;

    /**
     * Create a new command.
     *
     * @paramcommand The command to create.
     * @return The created command, including their ID.
     */
    @PostMapping("/command")
    public CommandResponseDto createCommand(@RequestBody CommandRequestDto command) {
        Command c = commandService.createCommand(command.getTotal());
        return new CommandResponseDto(c);
    }

    /**
     * Return all commands.
     *
     * @return All the commands.
     */
    @GetMapping("/command")
    public CommandListDto getAllCommands(){
        List<CommandResponseDto> commands = new ArrayList<>();

        for (Command c : commandService.getAllCommands()){
            commands.add(new CommandResponseDto(c));
        }

        return new CommandListDto(commands);
    }

    /**
     * Return the command with the given ID.
     *
     * @param id The primary key of the command to find.
     * @return The command with the given ID.
     */
    @GetMapping("/command/{id}")
    public CommandResponseDto getCommandById(@PathVariable int id){
        Command c = commandService.getCommandById(id);
        return new CommandResponseDto(c);
    }

    /**
     * Delete the command with the given ID.
     *
     * @param id The primary key of the command to find.
     * @return void.
     */
    @DeleteMapping("/command/{id}")
    public void deleteCommand(@PathVariable int id){
        commandService.deleteCommand(id);
    }
}
