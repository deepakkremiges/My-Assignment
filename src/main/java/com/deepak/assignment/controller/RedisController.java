package com.deepak.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deepak.assignment.controller.service.EmployeeService;
import com.deepak.assignment.controller.service.RedisService;
import com.deepak.assignment.utility.JsonRequest;
import com.deepak.assignment.utility.ResponseHandler;
import com.deepak.assignment.utility.UtilGenerator;
import java.util.Map;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    @Autowired
    private EmployeeService employeeService;

    // @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    // Problem 20. (i)
    @PostMapping("/addEmployee")
    public ResponseEntity<Object> addEmployee(@RequestBody JsonRequest request) {
        Map<String, Object> data = request.getData();
        String empName = (String) data.get("empName");
        redisService.addEmployee(empName);
        return ResponseHandler.generateResponse("success", "", "", empName, UtilGenerator.generateUniqueString(12));
    }

    // Problem 20. (ii)
    @GetMapping("/getEmployeeValue")
    public ResponseEntity<Object> getEmployeeValue(@RequestParam String empName, @RequestBody JsonRequest request) {
        Integer value = redisService.getEmployeeValue(empName);
        return ResponseHandler.generateResponse("success", "", "", value, request.get_reqid());
    }

    // Problem 20. (iii)
    @PostMapping("/incrementEmployeeValue")
    public ResponseEntity<Object> incrementEmployeeValue(@RequestParam String empName,
            @RequestBody JsonRequest request) {
        redisService.incrementEmployeeValue(empName);
        return ResponseHandler.generateResponse("success", "", "", request.getData(),
                UtilGenerator.generateUniqueString(12));
    }

    // Problem 20. (iv)
    @PostMapping("/decrementEmployeeValue")
    public ResponseEntity<Object> decrementEmployeeValue(@RequestParam String empName,
            @RequestBody JsonRequest request) {
        redisService.decrementEmployeeValue(empName);
        return ResponseHandler.generateResponse("success", "", "", request.getData(),
                UtilGenerator.generateUniqueString(12));
    }

    // Problem 20. (v)
    @PostMapping("/setEmployeeTTL")
    public ResponseEntity<Object> setEmployeeTTL(@RequestParam String empName, @RequestParam long ttlInSeconds,
            @RequestBody JsonRequest request) {
        redisService.setEmployeeTTL(empName, ttlInSeconds);
        return ResponseHandler.generateResponse("success", "", "", request.getData(),
                UtilGenerator.generateUniqueString(12));
    }

    // Problem 21.

    @PostMapping("/updateEmployeeContribution")
    public ResponseEntity<Object> updateEmployeeContribution(@RequestBody JsonRequest request) {
        try {
            String department = (String) request.getData().get("department");
            String employeeId = (String) request.getData().get("employeeId");
            int count = request.getData().containsKey("count") ? (int) request.getData().get("count") : 1;

            Map<String, Integer> contribution = redisService.updateEmployeeContribution(department, employeeId,
                    count);

            return ResponseHandler.generateResponse("success", "", "", contribution, request.get_reqid());
        } catch (Exception e) {
            return ResponseHandler.generateResponse("error", "500", e.getMessage(), null,
                    UtilGenerator.generateUniqueString(12));
        }
    }

    // Problem 22.

    @GetMapping("/myhr/employee/getContribution")
    public ResponseEntity<Object> getEmployeeContribution(@RequestParam String department) {

        try {

            // Call service method to get the latest count for the employee
            int latestCount = redisService.getEmployeeContribution(department);

            return ResponseHandler.generateResponse("success", "", "", latestCount,
                    UtilGenerator.generateUniqueString(12));
        } catch (Exception e) {
            return ResponseHandler.generateResponse("error", "500", e.getMessage(), null,
                    UtilGenerator.generateUniqueString(12));
        }

    }

    // Problem 23.

    @GetMapping("/{department}/{employeeId}/contribution")
    public ResponseEntity<Object> getEmployeeContribution(@PathVariable String department,
            @PathVariable String employeeId) {

        try {

            int contribution = employeeService.getEmployeeContribution(department, employeeId);

            return ResponseHandler.generateResponse("success", "", "", contribution,
                    UtilGenerator.generateUniqueString(12));
        } catch (Exception e) {
            return ResponseHandler.generateResponse("error", "500", e.getMessage(), null,
                    UtilGenerator.generateUniqueString(12));
        }
    }

}
