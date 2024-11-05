package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.dto.RoleDTO;
import ca.mcgill.ecse321.gameshop.dto.RoleMapper;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.model.Role;
import ca.mcgill.ecse321.gameshop.repository.PersonRepository;
import ca.mcgill.ecse321.gameshop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PersonRepository personRepository;

    public RoleDTO createRole(RoleDTO roleDTO) {
        Person person = personRepository.findById(roleDTO.getPersonId())
                .orElseThrow(() -> new IllegalArgumentException("Person not found"));
        Role role = RoleMapper.toEntity(roleDTO, person);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.toDTO(savedRole);
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RoleDTO getRoleById(int id) {
        Role role = roleRepository.findById(id).orElse(null);
        return role != null ? RoleMapper.toDTO(role) : null;
    }

    public RoleDTO updateRole(int id, RoleDTO roleDTO) {
        Optional<Role> existingRoleOpt = roleRepository.findById(id);
        if (existingRoleOpt.isPresent()) {
            Role existingRole = existingRoleOpt.get();

            // Update the fields of the existing role with data from RoleDTO
            existingRole.setRoleId(id); // Ensure ID consistency
            existingRole.setRoleId(roleDTO.getRoleId());

            // Retrieve and set the associated Person entity if the personId has changed
            if (roleDTO.getPersonId() != existingRole.getPerson().getId()) {
                Person person = personRepository.findById(roleDTO.getPersonId())
                        .orElseThrow(() -> new IllegalArgumentException("Person not found"));
                existingRole.setPerson(person);
            }

            // Save the updated role and convert it back to a DTO
            Role updatedRole = roleRepository.save(existingRole);
            return RoleMapper.toDTO(updatedRole);
        }
        return null; // Role not found
    }

    public boolean deleteRole(int id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

