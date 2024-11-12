package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

import ca.mcgill.ecse321.gameshop.model.Employee;

public class EmployeeResponseDto {

    private String username;
    private String email;
    private String phone;
    private int employeeId;

    private List <String> assignedTasks;

    @SuppressWarnings("unused")
    private EmployeeResponseDto() {
    }

    public EmployeeResponseDto(Employee employee) {
        this.employeeId = employee.getRoleId();
        this.username = employee.getPerson().getUsername();
        this.email = employee.getPerson().getEmail();
        this.phone = employee.getPerson().getPhone();
        this.assignedTasks=employee.getAssignedTasks();

    }

    /**
     * Getters
     * @return
     */
    public List <String> getAssignedTasks() {
        return assignedTasks;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * Setters
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTasks(List<String>tasks) {
        this.assignedTasks = tasks;
    }
}