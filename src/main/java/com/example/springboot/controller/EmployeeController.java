package com.example.springboot.controller;

import com.example.springboot.exception.ResourceNotFound;
import com.example.springboot.model.Employee;
import com.example.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return  employeeRepository.save(employee);
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeByid(@PathVariable long id){
        Employee res = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFound("employee not exist "));
        return  ResponseEntity.ok(res);
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable long id, @RequestBody Employee employee){
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFound("employee not exist "));
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmailId(employee.getEmailId());
        employeeRepository.save(existingEmployee);
        return  ResponseEntity.ok(existingEmployee);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Employee> deleteByid(@PathVariable long id, @RequestBody Employee employee){
        Employee res = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFound("employee not exist "));
          employeeRepository.delete(res);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
}

