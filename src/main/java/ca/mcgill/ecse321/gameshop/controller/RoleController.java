package ca.mcgill.ecse321.gameshop.controller;

import ca.mcgill.ecse321.gameshop.dto.*;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public RolesResponseDTO getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        List<RoleResponseDTO> responseList = new ArrayList<>();
        for (Role r : roles){
            RoleResponseDTO response = new RoleResponseDTO(r);
            responseList.add(response);
        }
        return new RolesResponseDTO(responseList);
    }

    @GetMapping("/{id}")
    public RoleResponseDTO getRoleById(@PathVariable int id) {
        Role role = roleService.getRoleById(id);
        return new RoleResponseDTO(role);
    }

    @PostMapping("/addRole")
    public RoleResponseDTO createRole(@RequestBody RoleRequestDTO role) {
        Role createdRole = new Role(role.getPerson());
        return new RoleResponseDTO(createdRole);
    }

    @PutMapping("/{id}")
    public RoleResponseDTO updateRole(@PathVariable int id, @RequestBody Person person) {
        Role updatedRole = roleService.updateRole(id, person);
        return new RoleResponseDTO(updatedRole);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
    }
}
