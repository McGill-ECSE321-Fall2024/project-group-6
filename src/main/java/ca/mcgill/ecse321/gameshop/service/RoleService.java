package ca.mcgill.ecse321.gameshop.service;


import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Employee;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.model.Role;
import ca.mcgill.ecse321.gameshop.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     *  No update method as it would never be used by Role class
     *  Also, role will not have a controller. We only created a service class and tested it
     *  to ensure a high % coverage.
     */


    /**
     * Service method to create a Customer role
     * @param username
     * @param emailAddress
     * @param phone
     * @param password
     * @param address
     * @return
     */
    @Transactional
    public Role createRoleCustomer(String username, String emailAddress, String phone, String password,String address) {

        Person person = new Person(username, emailAddress, password, phone);
            Customer c= new Customer(person,address);

        if(password.length()<10){
            throw new GameShopException(HttpStatus.LENGTH_REQUIRED, String.format("Password needs to be at least 10 characters long"));
        }
        if(emailAddress==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Email can not be null"));
        }

        if(phone==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Phone number can not be null"));
        }
        if(username==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Username can not be null"));
        }
        if(address==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Shipping Address can not be null"));
        }
            return roleRepository.save(c);

    }

    /**
     * Service method to create  an employee role
     * @param username
     * @param emailAddress
     * @param phone
     * @param password
     * @return
     */
    @Transactional

    public Role createRoleEmployee(String username, String emailAddress, String phone, String password) {

        Person person = new Person(username, emailAddress, password, phone);
            Employee e= new Employee(person,true);

        if(password.length()<10){
            throw new GameShopException(HttpStatus.LENGTH_REQUIRED, String.format("Password needs to be at least 10 characters long"));
        }
        if(emailAddress==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Email can not be null"));
        }

        if(phone==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Phone number can not be null"));
        }
        if(username==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Username can not be null"));
        }
            return roleRepository.save(e);

    }

    /**
     * Service method to get all role
     * @return
     */
    @Transactional
    public List<Role> getAllRoles() {
       List<Role>roles= (List<Role>) roleRepository.findAll();
       return roles;
    }
@Transactional
/**
 * Service method to get a specific role
 */
    public Role getRoleById(int id) {
        Role roleFromDB = roleRepository.findRoleByRoleId(id);
        if (roleFromDB == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Role with ID " + id + " does not exist."));
        } else {
            return roleFromDB;

        }
    }

    /**
     * Service method to delete a role
     * @param id
     */
    public void deleteRole(int id) {
        Role role = roleRepository.findRoleByRoleId(id);
        if (role == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Role with ID " + id + " does not exist."));
        }
        roleRepository.delete(role);
    }
}