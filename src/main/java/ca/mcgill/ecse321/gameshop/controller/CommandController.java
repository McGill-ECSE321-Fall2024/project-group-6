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
     * Creates a new command for a specific customer.
     *
     * @param c
     * @return A DTO response
     */
    @PostMapping("/commands")
    public CommandResponseDto createCommand(@RequestBody CommandRequestDto c) {
        Command createdCommand = commandService.createCommand(c.getCustomer());
        return new CommandResponseDto(createdCommand);
    }

    /**
     * @author Maissa
     * Retrieves a command by its ID.
     *
     * @param ID
     * @return A DTO response
     */
    @GetMapping("/commands/{ID}")
    public CommandResponseDto findCommandById(@PathVariable int ID) {
        return new CommandResponseDto(commandService.findCommandById(ID));
    }

    /**
     * @author Maissa
     * Retrieves all commands in the system.
     *
     * @return A DTO list of all commands.
     */
    @GetMapping("/commands")
    public CommandListDto getAllCommands() {
        List<CommandResponseDto> commands = new ArrayList<>();
        for (Command c : commandService.getAllCommands()) {
            commands.add(new CommandResponseDto(c));
        }
        return new CommandListDto(commands);
    }

    /**
     * @author Maissa
     * Deletes a command by its ID.
     *
     * @param ID
     */
    @DeleteMapping("/commands/{ID}")
    public void deleteCommand(@PathVariable int ID) {
        commandService.deleteCommand(ID);
    }
}
