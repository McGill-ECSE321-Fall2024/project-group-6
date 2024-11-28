package ca.mcgill.ecse321.gameshop.controller;

/**
 * @author Joseph
 */
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gameshop.dto.EmployeeListDto;
import ca.mcgill.ecse321.gameshop.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.gameshop.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.gameshop.model.Employee;
import ca.mcgill.ecse321.gameshop.service.EmployeeService;

@RestController
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;


    /**
     * Create an employee
     * @param employeeToCreate
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @PostMapping("/employees")
    public EmployeeResponseDto createEmployee(@RequestBody EmployeeRequestDto employeeToCreate) {

        Employee employee = employeeService.addEmployee(employeeToCreate.getUsername(),employeeToCreate.getEmail(),employeeToCreate.getPhone(),employeeToCreate.getPassword());

        return new EmployeeResponseDto(employee);
    }

    /**
     * get employee
     * @param eid
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/employees/{eid}")
    public EmployeeResponseDto findEmployeeById(@PathVariable int eid) {
        Employee employee=employeeService.getEmployeeById(eid);

        return new EmployeeResponseDto(employee);
    }

    /**
     * Get all employees
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
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
    @CrossOrigin(origins = "http://localhost:8087")
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
    @CrossOrigin(origins = "http://localhost:8087")
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
    @CrossOrigin(origins = "http://localhost:8087")
    @PutMapping("/employees/{eid}/tasks")
    public EmployeeResponseDto assignTask(@PathVariable int eid, @RequestBody String task) {
        Employee e = employeeService.assignTask(eid,task);
        return new EmployeeResponseDto(e);
    }
}
