package com.rohitpadile.springboot_backend.controller;

import com.rohitpadile.springboot_backend.exception.ResourceNotFoundException;
import com.rohitpadile.springboot_backend.model.Employee;
import com.rohitpadile.springboot_backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000/" )
@RestController
@RequestMapping("api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    //get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //CREATE EMPLOYEE REST API
    @PostMapping("/add-employee")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    //get employee by id rest api
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow( () ->
            new ResourceNotFoundException("Employee Not Exist with id " + id)
        );
        return ResponseEntity.ok(employee); //working
    }

    //update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
        Employee employee = employeeRepository.findById(id).orElseThrow( () ->
                new ResourceNotFoundException("Employee Not Exist with id " + id)
        );
        if(employeeDetails.getFirstName() != null){
            employee.setFirstName(employeeDetails.getFirstName());
        }
        if(employeeDetails.getLastName() != null){
            employee.setLastName(employeeDetails.getLastName());
        }
        if(employeeDetails.getEmailId() != null){
            employee.setEmailId(employeeDetails.getEmailId());
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee); //code : 200
    }

    //delete employee rest api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow( () ->
                new ResourceNotFoundException("Employee Not Exist with id " + id)
        );
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE); //HashMap === JSON
        return ResponseEntity.ok(response);
    }
}
