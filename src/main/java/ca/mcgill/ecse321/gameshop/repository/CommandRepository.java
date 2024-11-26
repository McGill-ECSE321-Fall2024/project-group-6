package ca.mcgill.ecse321.gameshop.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Command;

public interface CommandRepository extends CrudRepository<Command, Integer> {
    public Command findCommandByCommandId(int commandId);
}