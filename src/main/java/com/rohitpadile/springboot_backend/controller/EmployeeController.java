package com.rohitpadile.springboot_backend.controller;

import com.rohitpadile.springboot_backend.exception.ResourceNotFoundException;
import com.rohitpadile.springboot_backend.model.Employee;
import com.rohitpadile.springboot_backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
