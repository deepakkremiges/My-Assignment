package com.deepak.assignment.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.deepak.assignment.controller.service.EmployeeService;
import com.deepak.assignment.dto.EmployeeDetailsDTO;
import com.deepak.assignment.dto.EmployeeDto;
import com.deepak.assignment.dto.UpdateEmployeeRequest;
import com.deepak.assignment.entity.Employee;
import com.deepak.assignment.utility.JsonRequest;
import com.deepak.assignment.utility.ResponseHandler;
import com.deepak.assignment.utility.UtilGenerator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/myhr/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${excel.file.directory}") // Get directory path from property file
    private String excelFileDirectory;

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping("/add")
    public ResponseEntity<Object> addEmployee(@RequestBody JsonRequest request) {

        try {
            // Setting data in EmployeeDto from here
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setEmpId(request.getData().get("empId").toString());
            employeeDto.setFname(request.getData().get("fname").toString());
            employeeDto.setFullname(request.getData().get("fullname").toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = dateFormat.parse(request.getData().get("dob").toString());
            Date doj = dateFormat.parse(request.getData().get("doj").toString());

            employeeDto.setDob(dob);
            employeeDto.setDoj(doj);

            employeeDto.setSalary(Integer.parseInt(request.getData().get("salary").toString()));
            employeeDto.setReportsTo(Long.parseLong(request.getData().get("reportsTo").toString()));

            employeeDto.setDeptId(Long.parseLong(request.getData().get("departmentId").toString()));
            employeeDto.setRankId(Long.parseLong(request.getData().get("rankId").toString()));

            String reqId = UtilGenerator.generateUniqueString(12);
            employeeDto.setClientReqId(reqId);

            logger.debug("Adding Employee Successfully");
            employeeService.addEmployee(employeeDto);

            // setting logging debug

            return ResponseHandler.generateResponse("success", "", "", employeeDto, reqId);
        } catch (Exception e) {

            logger.debug("Adding Employee Failed");
            return ResponseHandler.generateResponse("error", "500", e.getMessage(), null, request.get_reqid());

        }
    }

    @GetMapping(path = "/list", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" })
    public ResponseEntity<Object> listEmployees(@RequestParam(name = "type", required = false) String type) {
        try {
            List<EmployeeDto> employees = employeeService.getAllEmployees();

            if ("xlsx".equals(type)) {
                String fileName = excelFileDirectory + "employees.xlsx";
                UtilGenerator.createExcelFile(employees, fileName);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.xlsx");
                return new ResponseEntity<>(headers, HttpStatus.OK);
            } else {
                List<List<Object>> employeeList = new ArrayList<>();

                // Map employee details to array of arrays
                for (EmployeeDto employee : employees) {
                    List<Object> employeeInfo = new ArrayList<>();
                    employeeInfo.add(employee.getEmpId());
                    employeeInfo.add(employee.getFname());
                    employeeList.add(employeeInfo);
                }
                String reqId = UtilGenerator.generateUniqueString(12);

                logger.debug("List of Employee Successfuly");

                // Return the array of arrays in the JSON response
                return ResponseHandler.generateResponse("success", "", "", employeeList, reqId);
            }
        } catch (Exception e) {

            logger.debug("List of Employee error");

            return ResponseHandler.generateResponse("error", "500", e.getMessage(), null,
                    UtilGenerator.generateUniqueString(12));
        }
    }

    // Above code is related with Excel file

    @GetMapping("/lists")
    public List<Employee> getFilteredEmployees(@RequestParam(required = false) String filter) {
        String sql = "SELECT * FROM employee";

        // If filter is provided, add a WHERE clause to filter records based on the
        // filter parameter
        if (filter != null && !filter.isEmpty()) {
            // Use a case-insensitive match for filtering
            sql += " WHERE LOWER(fname) LIKE '%" + filter.toLowerCase() + "%'";
        }

        // Execute the SQL query to fetch the filtered records
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Employee employee = new Employee();
            employee.setId(rs.getLong("id"));
            employee.setEmpId(rs.getString("empId"));
            employee.setFname(rs.getString("fname"));
            employee.setFullname(rs.getString("fullname"));
            employee.setDob(rs.getDate("dob"));
            employee.setDoj(rs.getDate("doj"));
            employee.setSalary(rs.getInt("salary"));
            employee.setReportsTo(rs.getLong("reportsTo"));

            employee.setCreatedAt(rs.getDate("createdAt")); // Set createdAt
            employee.setUpdatedAt(rs.getDate("updatedAt")); // Set updatedAt
            employee.setClientReqId(UtilGenerator.generateUniqueString(12));

            logger.debug("Getting Employee as list Successfully");
            return employee;
        });
    }

    @GetMapping("/get/{employeeId}")
    public ResponseEntity<Object> getEmployeeDetails(@PathVariable Long employeeId) {
        try {
            EmployeeDetailsDTO employeeDetailsDTO = employeeService.getEmployeeDetails(employeeId);
            if (employeeDetailsDTO != null) {

                logger.debug("Getting Employee Successfully");

                return ResponseHandler.generateResponse("success", "", "", employeeDetailsDTO,
                        UtilGenerator.generateUniqueString(12));
            } else {
                return ResponseHandler.generateResponse("error", "404", "Employee not found", null,
                        UtilGenerator.generateUniqueString(12));
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse("error", "500", e.getMessage(), null,
                    UtilGenerator.generateUniqueString(12));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateEmployee(@RequestBody JsonRequest request) {
        try {
            UpdateEmployeeRequest updateRequest = UtilGenerator.mapJsonRequestToUpdateEmployeeRequest(request);

            logger.debug("Updating Employee Successfully");
            employeeService.updateEmployee(updateRequest);

            // Generate response with updated employee details
            return ResponseHandler.generateResponse("success", "", "", updateRequest,
                    UtilGenerator.generateUniqueString(12));
        } catch (Exception e) {
            // Handle errors and return error response
            return ResponseHandler.generateResponse("error", "500", e.getMessage(), null,
                    UtilGenerator.generateUniqueString(10));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        try {

            EmployeeDetailsDTO employeeDetailsDTO = employeeService.getEmployeeDetails(id);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("rankdesc", employeeDetailsDTO.getRankDesc());
            dataMap.put("departmentName", employeeDetailsDTO.getDepartmentName());

            String reqId = UtilGenerator.generateUniqueString(12);

            logger.debug("Deleted Employee Successfully");

            ResponseEntity<Object> response = employeeService.deleteEmployee(id);
            return ResponseHandler.generateResponse("success", "200", "Employee deleted successfully",
                    dataMap, reqId);
        } catch (Exception e) {
            String reqId = UtilGenerator.generateUniqueString(12);

            logger.debug("Employee not getting deleted");
            return ResponseHandler.generateResponse("error", "500", e.getMessage(), null, reqId);
        }
    }

}