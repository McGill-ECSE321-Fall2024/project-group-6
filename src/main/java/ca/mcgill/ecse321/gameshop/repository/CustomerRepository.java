package ca.mcgill.ecse321.gameshop.repository;

import ca.mcgill.ecse321.gameshop.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    public Customer findCustomerByRoleId(int roleId);
}


