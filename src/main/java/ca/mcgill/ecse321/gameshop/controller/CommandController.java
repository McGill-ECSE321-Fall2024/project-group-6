package ca.mcgill.ecse321.gameshop.controller;

import ca.mcgill.ecse321.gameshop.dto.CommandRequestDto;
import ca.mcgill.ecse321.gameshop.dto.CommandResponseDto;
import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommandController {
    @Autowired
    private CommandService commandService;

    @PostMapping("/commands")
    public CommandResponseDto createCommand(@RequestBody CommandRequestDto c) {
        Command createdCommand = commandService.createCommand(c.getTotal());
        return new CommandResponseDto(createdCommand);
    }

    @GetMapping("/commands/{cId}")
    public CommandResponseDto findCommandById(@PathVariable int cId){
        return new CommandResponseDto(commandService.findCommandById(cId));
    }

    @GetMapping("/commands")
    public Iterable<Command> getAllCommands(){
        return commandService.getAllCommands();
    }

    @DeleteMapping("/commands/{cId}")
    public void deleteCommand(int cId){
        commandService.deleteCommand(cId);
    }
}
