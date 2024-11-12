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

import ca.mcgill.ecse321.gameshop.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gameshop.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.gameshop.dto.RoleListDto;
import ca.mcgill.ecse321.gameshop.dto.RoleResponseDto;
import ca.mcgill.ecse321.gameshop.model.Role;
import ca.mcgill.ecse321.gameshop.service.RoleService;


@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/role/employees")
    public RoleResponseDto createRoleEmployee(@RequestBody EmployeeRequestDto employee) {
        Role r = roleService.createRoleEmployee(employee.getUsername(), employee.getEmail(), employee.getPhone(), employee.getPassword());
        return new RoleResponseDto(r);
    }

    @PostMapping("/role/customers")
    public RoleResponseDto createRoleCustomer(@RequestBody CustomerRequestDto customer) {
        Role r = roleService.createRoleCustomer(customer.getUsername(), customer.getEmail(), customer.getPhone(), customer.getPassword(), customer.getShippingAddress());
        return new RoleResponseDto(r);
    }

    @GetMapping("/role")
    public RoleListDto getAllRole() {
        List<RoleResponseDto> roles = new ArrayList<>();

        for (Role r: roleService.getAllRoles()) {
            roles.add(new RoleResponseDto(r));
        }

        return new RoleListDto(roles);
    }

    @GetMapping("/role/{id}")
    public RoleResponseDto getRoleByID(@PathVariable int id) {
        Role r = roleService.getRoleById(id);
        return new RoleResponseDto(r);
    }

    /**
     * Delete the Role with the given ID.
     *
     * @param id The primary key of the Role to find.
     * @return void.
     */
    @DeleteMapping("/role/{id}")
    public void deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
    }
}

