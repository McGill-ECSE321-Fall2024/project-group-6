package ca.mcgill.ecse321.gameshop.controller;

/**
 * @author Joseph
 */
import ca.mcgill.ecse321.gameshop.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.repository.*;
import ca.mcgill.ecse321.gameshop.service.*;

@RestController
public class ManagerController {


        @Autowired
        private ManagerService managerService;
    @Autowired
    private PersonRepository personRepo;


    /**
     * Create a manager
     * @param managerToCreate
     * @return
     */
        @PostMapping("/manager")
        public ManagerResponseDto createManager(@Valid @RequestBody ManagerRequestDto managerToCreate) {
            Person person= new Person(managerToCreate.getUsername(),managerToCreate.getEmail(),managerToCreate.getPassword(),managerToCreate.getPhone());

            Manager manager = managerService.createManager(person);

            return new ManagerResponseDto(manager);
        }

    /**
     * get a manager
     * @param mid
     * @return
     */
        @GetMapping("/manager/{mid}")
        public ManagerResponseDto findManagerById(@PathVariable int mid) {
            Manager manager=managerService.findManagerById(mid);

            return new ManagerResponseDto(manager);
        }

    /**
     * Delete a manager
     * @param mid
     */

    @DeleteMapping("/manager/{mid}")
    public void deleteManager(@PathVariable int mid) {
        managerService.deleteManager(mid);

    }

    /**
     * Update manager's info
     * @param id
     * @param manager
     * @return
     */
    @PutMapping("/manager/{id}")
    public ManagerResponseDto updateManager(@PathVariable int id, @RequestBody ManagerRequestDto manager) {


        Manager m = managerService.updateManager(id, manager.getUsername(), manager.getEmail(), manager.getPassword(), manager.getPhone());

        return new ManagerResponseDto(m);
    }
    }