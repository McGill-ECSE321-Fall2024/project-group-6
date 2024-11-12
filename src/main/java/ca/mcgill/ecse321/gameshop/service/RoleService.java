package ca.mcgill.ecse321.gameshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Employee;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.model.Role;
import ca.mcgill.ecse321.gameshop.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRoleCustomer(String username, String emailAddress, String phone, String password,String address) {
        Person person = new Person(username, emailAddress, phone, password);
            Customer c= new Customer(person,address);
            return roleRepository.save(c);
    }

    public Role createRoleEmployee(String username, String emailAddress, String phone, String password) {
        Person person = new Person(username, emailAddress, phone, password);
            Employee e= new Employee(person,true);
            return roleRepository.save(e);
    }

    public List<Role> getAllRoles() {
        List<Role>roles= (List<Role>) roleRepository.findAll();
        return roles;
    }

    public Role getRoleById(int id) {
        Role roleFromDB = roleRepository.findRoleByRoleId(id);
        if (roleFromDB == null) {
            throw new IllegalArgumentException("Role id is not valid");
        }
        
        else {
            return roleFromDB;
        }
    }

    public void deleteRole(int id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        }

        else {
            throw new IllegalArgumentException("Role id is not valid");
        }
    }
}