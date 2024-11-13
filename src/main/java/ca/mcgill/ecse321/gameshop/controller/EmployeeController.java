package ca.mcgill.ecse321.gameshop.controller;


import ca.mcgill.ecse321.gameshop.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;
import ca.mcgill.ecse321.gameshop.model.*;
import java.util.*;
import ca.mcgill.ecse321.gameshop.service.*;

@RestController
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;


    /**
     * Create an employee
     * @param employeeToCreate
     * @return
     */
    @PostMapping("/employees")
    public EmployeeResponseDto createEmployee(@Valid @RequestBody EmployeeRequestDto employeeToCreate) {

        Employee employee = employeeService.addEmployee(employeeToCreate.getUsername(),employeeToCreate.getEmail(),employeeToCreate.getPhone(),employeeToCreate.getPassword());

        return new EmployeeResponseDto(employee);
    }

    /**
     * get employee
     * @param eid
     * @return
     */
    @GetMapping("/employees/{eid}")
    public EmployeeResponseDto findEmployeeById(@PathVariable int eid) {
        Employee employee=employeeService.getEmployeeById(eid);

        return new EmployeeResponseDto(employee);
    }

    /**
     * Get all employees
     * @return
     */
    @GetMapping("/employees")
    public EmployeeListDto getAllEmployees() {
        List<EmployeeResponseDto> employees = new ArrayList<>();
        for (Employee e: employeeService.getAllEmployees()) {
            employees.add(new EmployeeResponseDto(e));
        }
        return new EmployeeListDto(employees);
    }

    /**
     * Deactivate employee
     * @param eid
     */
    @PutMapping("/employees/deactivate/{eid}")
    public EmployeeResponseDto deactivateEmployee(@PathVariable int eid) {
       Employee employee= employeeService.deactivateEmployee(eid);
        return new EmployeeResponseDto(employee);
    }

    /**
     * Update employee's information
     * @param eid
     * @param employee
     * @return
     */
    @PutMapping("/employees/{eid}")
    public EmployeeResponseDto updateEmployee(@PathVariable int eid, @RequestBody EmployeeRequestDto employee) {
        Employee e = employeeService.updateEmployee(eid, employee.getUsername(),employee.getEmail(), employee.getPassword(), employee.getPhone());

        return new EmployeeResponseDto(e);
    }

    /**
     * assign a task to an employee
     * @param eid
     * @param task
     * @return
     */
    @PutMapping("/employees/{eid}/tasks")
    public EmployeeResponseDto assignTask(@PathVariable int eid, @RequestBody String task) {
        Employee e = employeeService.assignTask(eid,task);
        return new EmployeeResponseDto(e);
    }



}
