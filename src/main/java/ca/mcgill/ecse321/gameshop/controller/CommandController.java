package ca.mcgill.ecse321.gameshop.controller;

import ca.mcgill.ecse321.gameshop.dto.CommandListDto;
import ca.mcgill.ecse321.gameshop.dto.CommandRequestDto;
import ca.mcgill.ecse321.gameshop.dto.CommandResponseDto;
import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CommandController {
    @Autowired
    private CommandService commandService;

    /**
     * @author Maissa
     * Create a new command.
     *
     * @paramcommand The command to create.
     * @return The created command, including their ID.
     */
    @PostMapping("/command")
    public CommandResponseDto createCommand(@RequestBody CommandRequestDto command) {
        System.out.println("Controller ");
        Command createdCommand = commandService.createCommand(command.getCustomerID());

        return new CommandResponseDto(createdCommand);
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
        Command c = commandService.findCommandById(id);
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