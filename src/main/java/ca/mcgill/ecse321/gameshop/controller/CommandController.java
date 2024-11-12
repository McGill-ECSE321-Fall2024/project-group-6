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

    @PostMapping("/commands")
    public CommandResponseDto createCommand(@RequestBody CommandRequestDto c) {
        Command createdCommand = commandService.createCommand(c.getCustomer());
        return new CommandResponseDto(createdCommand);
    }

    @GetMapping("/commands/{cId}")
    public CommandResponseDto findCommandById(@PathVariable int cId){
        return new CommandResponseDto(commandService.findCommandById(cId));
    }

    @GetMapping("/commands")
    public CommandListDto getAllCommands(){
        List<CommandResponseDto> commands = new ArrayList<>();
        for (Command c : commandService.getAllCommands()){
            commands.add(new CommandResponseDto(c));
        }
        return new CommandListDto(commands);
    }

    @DeleteMapping("/commands/{cId}")
    public void deleteCommand(int cId){
        commandService.deleteCommand(cId);
    }
}
