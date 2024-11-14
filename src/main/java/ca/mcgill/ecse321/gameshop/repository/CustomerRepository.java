package ca.mcgill.ecse321.gameshop.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    public Customer findCustomerByRoleId(int roleId);
}


