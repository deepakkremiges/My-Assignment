package com.deepak.assignment.controller.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.deepak.assignment.dto.EmployeeDetailsDTO;
import com.deepak.assignment.dto.EmployeeDto;
import com.deepak.assignment.dto.UpdateEmployeeRequest;
import com.deepak.assignment.entity.Employee;

@Service
public interface EmployeeService {

    public void addEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> getAllEmployees();

    EmployeeDetailsDTO getEmployeeDetails(Long employeeId);

    Employee updateEmployee(UpdateEmployeeRequest request);

    ResponseEntity<Object> deleteEmployee(Long id);

    public int getEmployeeContribution(String department, String employeeId);
}
