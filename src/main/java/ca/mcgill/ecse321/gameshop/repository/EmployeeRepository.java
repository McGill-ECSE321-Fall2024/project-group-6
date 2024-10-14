package ca.mcgill.ecse321.gameshop.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    public Employee findEmployeeByRoleId(int roleId);
}


