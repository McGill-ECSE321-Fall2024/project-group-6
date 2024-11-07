package ca.mcgill.ecse321.gameshop.controller;


import ca.mcgill.ecse321.gameshop.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;
//import ca.mcgill.ecse321.eventregistration.dto.RegistrationResponseDto;
import ca.mcgill.ecse321.gameshop.model.*;
import java.util.*;
import ca.mcgill.ecse321.gameshop.service.*;

@RestController
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;


    /**
     *
     * @param employeeToCreate
     * @return
     */
    @PostMapping("/employees")
    public EmployeeResponseDto createEmployee(@Valid @RequestBody EmployeeRequestDto employeeToCreate) {

        Employee employee = employeeService.addEmployee(employeeToCreate.getUsername(),employeeToCreate.getEmail(),employeeToCreate.getPassword(),employeeToCreate.getPhone());

        return new EmployeeResponseDto(employee);
    }

    /**
     *
     * @param eid
     * @return
     */
    @GetMapping("/employees/{eid}")
    public EmployeeResponseDto findEmployeeById(@PathVariable int eid) {
        Employee employee=employeeService.getEmployeeById(eid);

        return new EmployeeResponseDto(employee);
    }
    @GetMapping("/employees")
    public EmployeeListDto getAllEmployees() {
        List<EmployeeResponseDto> employees = new ArrayList<>();
        for (Employee e: employeeService.getAllEmployees()) {
            employees.add(new EmployeeResponseDto(e));
        }
        return new EmployeeListDto(employees);
    }

    /**
     *
     * @param eid
     */
    @DeleteMapping("/employees/{eid}")
    public void deleteEmployee(@PathVariable int eid) {
        employeeService.deactivateEmployee(eid);

    }

    /**
     *
     * @param eid
     * @param employee
     * @return
     */
    @PutMapping("/employees/{eid}")
    public EmployeeResponseDto updateEmployee(@PathVariable int eid, @RequestBody EmployeeRequestDto employee) {
        Employee e = employeeService.updateEmployee(eid, employee.getUsername(),employee.getEmail(),employee.getPhone(),employee.getPassword());

        return new EmployeeResponseDto(e);
    }

    /**
     *
     * @param eid
     * @param task
     * @return
     */
    @PutMapping("/employees/{eid}/tasks")
    public EmployeeResponseDto assignTask(@PathVariable int eid, String task) {
        Employee e = employeeService.assignTask(eid,task);
        return new EmployeeResponseDto(e);
    }

    /**
     *
     * @param eid
     * @return
     */
    @GetMapping("/employees/{eid}/tasks")
    public List <String> getAllTasks(@PathVariable int eid) {

        return employeeService.getTasks(eid);
    }

    /**
     *
     * @param eid
     * @param taskToDelete
     */
/*
    @DeleteMapping("/employees/{eid}/tasks")
    public void deleteTask(@PathVariable int eid,String taskToDelete) {

        employeeService.deleteTask(eid,taskToDelete);
    }

 */
}
