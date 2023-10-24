package com.skypro.employee.controller;


import com.skypro.employee.model.Employee;
import com.skypro.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/data_list/employee")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);

        return new ResponseEntity<>(employee, HttpStatus.CREATED);}

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employee> delete(@PathVariable Integer id) {
        Employee employee = employeeService.getEmployee(id);
        if (employee != null) {
            employeeService.delete(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }
    }
