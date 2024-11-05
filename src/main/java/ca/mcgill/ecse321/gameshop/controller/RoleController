package ca.mcgill.ecse321.gameshop.controller;

import ca.mcgill.ecse321.gameshop.dto.RoleDTO;
import ca.mcgill.ecse321.gameshop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Create a new role
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO createdRole = roleService.createRole(roleDTO);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    // Get all roles
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    // Get a specific role by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable int id) {
        RoleDTO role = roleService.getRoleById(id);
        return role != null ? new ResponseEntity<>(role, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Update an existing role
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable int id, @RequestBody RoleDTO roleDTO) {
        RoleDTO updatedRole = roleService.updateRole(id, roleDTO);
        return updatedRole != null ? new ResponseEntity<>(updatedRole, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a role
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        boolean deleted = roleService.deleteRole(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

