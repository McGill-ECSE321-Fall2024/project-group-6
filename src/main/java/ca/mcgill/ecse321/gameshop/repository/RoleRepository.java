package ca.mcgill.ecse321.gameshop.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    public Role findRoleByRoleId(int roleId);
}

