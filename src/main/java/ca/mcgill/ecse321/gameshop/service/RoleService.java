package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.model.Role;
import ca.mcgill.ecse321.gameshop.repository.RoleRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles(){
        return (List<Role>)roleRepository.findAll();
    }

    public Role getRoleById(int id) {
        Role role = roleRepository.findRoleByRoleId(id);
        if (role == null) {
            throw new IllegalArgumentException("There is no role with ID" + id + ".");
        }
        return role;
    }

    public Role createRole(Person person) {
        Role newRole = new Role(person);
        return roleRepository.save(newRole);
    }

    @Transactional
    public Role updateRole(int id, Person person) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        role.setPerson(person);
        return roleRepository.save(role);
    }

    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }
}
