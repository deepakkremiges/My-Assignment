package com.deepak.assignment.controller.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.deepak.assignment.dto.EmployeeDetailsDTO;
import com.deepak.assignment.dto.EmployeeDto;
import com.deepak.assignment.dto.UpdateEmployeeRequest;
import com.deepak.assignment.entity.Department;
import com.deepak.assignment.entity.Employee;
import com.deepak.assignment.entity.EmployeeShadow;
import com.deepak.assignment.entity.Rank;
import com.deepak.assignment.repository.DepartmentRepository;
import com.deepak.assignment.repository.EmployeeRepository;
import com.deepak.assignment.repository.EmployeeShadowRepostory;
import com.deepak.assignment.repository.RankRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeShadowRepostory employeeShadowRepostory;

    public void addEmployee(EmployeeDto employeeDto) {

        Employee employee = new Employee();
        employee.setEmpId(employeeDto.getEmpId());
        employee.setFname(employeeDto.getFname());
        employee.setFullname(employeeDto.getFullname());
        employee.setDob(employeeDto.getDob());
        employee.setDoj(employeeDto.getDoj());
        employee.setSalary(employeeDto.getSalary());
        employee.setReportsTo(employeeDto.getReportsTo());

        Department department = new Department();
        department.setId((employeeDto.getDeptId()));
        employee.setDepartment(department);

        Rank rank = new Rank();
        rank.setId(employeeDto.getRankId());
        employee.setRank(rank);

        employee.setClientReqId(employeeDto.getClientReqId());

        employeeRepository.save(employee);

    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    private EmployeeDto convertToDTO(Employee emp) {
        EmployeeDto dto = new EmployeeDto();

        dto.setEmpId(emp.getEmpId());
        dto.setFname(emp.getFname());

        return dto;
    }

    @Override
    public EmployeeDetailsDTO getEmployeeDetails(Long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            Rank rank = rankRepository.findById(employee.getRank().getId()).orElse(null);
            Department department = departmentRepository.findById(employee.getDepartment().getId()).orElse(null);

            EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
            employeeDetailsDTO.setId(employee.getId());
            employeeDetailsDTO.setEmpId(employee.getEmpId());
            employeeDetailsDTO.setFname(employee.getFname());
            employeeDetailsDTO.setFullname(employee.getFullname());
            if (rank != null) {
                employeeDetailsDTO.setRankDesc(rank.getRankDesc());
            }

            if (department != null) {
                employeeDetailsDTO.setDepartmentName(department.getDeptName());
            }
            return employeeDetailsDTO;
        } else {
            return null; // Or throw an exception if employee with given ID is not found
        }
    }

    @Override
    public Employee updateEmployee(UpdateEmployeeRequest updateRequest) {
        try {
            Employee employee = employeeRepository.findById(updateRequest.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            // Set the updatedBy to current user in employee_shadow Table
            EmployeeShadow empShadow = new EmployeeShadow(employee);
            employeeShadowRepostory.save(empShadow);

            // Employee employee = new Employee();
            // Update employee details
            employee.setId(updateRequest.getId());
            employee.setEmpId(updateRequest.getEmpId());
            employee.setFname(updateRequest.getFname());
            employee.setFullname(updateRequest.getFullname());
            employee.setDob(updateRequest.getDob());
            employee.setDoj(updateRequest.getDoj());
            employee.setSalary(updateRequest.getSalary());
            employee.setReportsTo(updateRequest.getReportsTo());

            // Save and return the updated employee
            return employeeRepository.save(employee);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseEntity<Object> deleteEmployee(Long id) {
        try {
            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
            if (!optionalEmployee.isPresent()) {
                // throw new EmployeeNotFoundException("Employee not found");
                throw new RuntimeException("Employee not found");
            }

            // setting employee in employee_shadow table before gets deleted
            Employee employee = optionalEmployee.get();
            EmployeeShadow shadowEmployee = new EmployeeShadow(employee);
            employeeShadowRepostory.save(shadowEmployee);

            // Delete the employee
            employeeRepository.deleteById(id);
        } catch (Exception e) {
            // throw new EmployeeServiceException("Failed to delete employee", e);
            new RuntimeException("Failed to add Employee ", e);
        }
        return null;
    }

    // Get data in xml format
    /*
     * @Override
     * public String getAllEmployeesAsXml() {
     * try {
     * List<Employee> employees = employeeRepository.findAll();
     * 
     * // Create a JAXB context for the Employee class
     * JAXBContext jaxbContext = JAXBContext.newInstance(ListWrapper.class);
     * 
     * // Create a marshaller
     * Marshaller marshaller = jaxbContext.createMarshaller();
     * marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
     * 
     * // Wrap the list of employees to include a root element
     * ListWrapper<Employee> listWrapper = new ListWrapper<>(employees);
     * 
     * // Marshal the wrapped list to XML
     * StringWriter writer = new StringWriter();
     * marshaller.marshal(listWrapper, writer);
     * 
     * // Return the XML string
     * return writer.toString();
     * } catch (JAXBException e) {
     * // Handle JAXB exception
     * e.printStackTrace(); // Consider logging the error instead
     * return null;
     * }
     * }
     */

}
