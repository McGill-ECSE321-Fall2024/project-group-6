package ca.mcgill.ecse321.gameshop.service;


import ca.mcgill.ecse321.gameshop.model.Employee;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.model.Role;
import ca.mcgill.ecse321.gameshop.repository.*;
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
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public Role createRole(String username, String emailAddress, String phone, String password,String address) {

        Person person = new Person(username, emailAddress, phone, password);
        if(address==null){
            Employee employee= new Employee(person,true);
            return employee;
        }else{
            Customer customer= new Customer(person,address);
            return customer;
        }
    }

    public List<Role> getAllRoles() {
       List<Role>roles= (List<Role>) roleRepository.findAll();
       return roles;
    }

    public Role getRoleById(int id) {
        Role roleFromDB = roleRepository.findRoleByRoleId(id);
        if (roleFromDB == null) {
            throw new IllegalArgumentException("Role id is not valid");
        } else {
            return roleFromDB;

        }
    }

    public void deleteRole(int id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);

        }
        else{
            throw new IllegalArgumentException("Role id is not valid");
        }

    }
}