package ca.mcgill.ecse321.gameshop.controller;

/**
 * @author Joseph
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gameshop.dto.ManagerRequestDto;
import ca.mcgill.ecse321.gameshop.dto.ManagerResponseDto;
import ca.mcgill.ecse321.gameshop.model.Manager;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.PersonRepository;
import ca.mcgill.ecse321.gameshop.service.ManagerService;

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
    @CrossOrigin(origins = "http://localhost:8087")
        @PostMapping("/manager")
        public ManagerResponseDto createManager(@RequestBody ManagerRequestDto managerToCreate) {
            Person person= new Person(managerToCreate.getUsername(),managerToCreate.getEmail(),managerToCreate.getPassword(),managerToCreate.getPhone());

            Manager manager = managerService.createManager(person);

            return new ManagerResponseDto(manager);
        }

    /**
     * get a manager
     * @param mid
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
        @GetMapping("/manager/{mid}")
        public ManagerResponseDto findManagerById(@PathVariable int mid) {
            Manager manager=managerService.findManagerById(mid);

            return new ManagerResponseDto(manager);
        }

    /**
     * Delete a manager
     * @param mid
     */
    @CrossOrigin(origins = "http://localhost:8087")
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
    @CrossOrigin(origins = "http://localhost:8087")
    @PutMapping("/manager/{id}")
    public ManagerResponseDto updateManager(@PathVariable int id, @RequestBody ManagerRequestDto manager) {


        Manager m = managerService.updateManager(id, manager.getUsername(), manager.getEmail(), manager.getPassword(), manager.getPhone());

        return new ManagerResponseDto(m);
    }
    /**
     * Method to extract managerId using userId, no need to integrate or unit test
     * @param mid
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/manager/id/{mid}")
    public int getManagerManagerId(@PathVariable int mid) {
        return managerService.getManagerManagerId(mid);
    }
    }