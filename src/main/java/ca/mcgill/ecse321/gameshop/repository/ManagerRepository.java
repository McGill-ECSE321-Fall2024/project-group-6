package ca.mcgill.ecse321.gameshop.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Manager;

public interface ManagerRepository extends CrudRepository<Manager, Integer> {
    public Manager findManagerByRoleId(int roleId);
}

