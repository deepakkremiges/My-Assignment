package com.deepak.assignment.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.deepak.assignment.entity.Employee;
import com.deepak.assignment.repository.EmployeeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Integer> redisTemplate;

    // private final EmployeeRepository employeeRepository;

    @Autowired
    public RedisService(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // @Value("${redis.cache.ttl}")
    // private long redisCacheTtl;

    // public RedisService(RedisTemplate<String, Integer> redisTemplate,
    // EmployeeRepository employeeRepository) {
    // this.redisTemplate = redisTemplate;
    // this.employeeRepository = employeeRepository;
    // }

    // Problem 20 Starts here

    // (i) Add a new key with default value as zero
    public void addEmployee(String empName) {
        redisTemplate.opsForValue().set("user." + empName, 0);
    }

    // (ii) View key value
    public Integer getEmployeeValue(String empName) {
        return redisTemplate.opsForValue().get("user." + empName);
    }

    // (iii) Increment value
    public void incrementEmployeeValue(String empName) {
        redisTemplate.opsForValue().increment("user." + empName);
    }

    // (iv) Decrement value
    public void decrementEmployeeValue(String empName) {
        redisTemplate.opsForValue().decrement("user." + empName);
    }

    // (v) Set TTL (Time-To-Live)
    public void setEmployeeTTL(String empName, long ttlInSeconds) {
        redisTemplate.expire("user." + empName, ttlInSeconds, TimeUnit.SECONDS);
    }

    // For Problem 21.
    public Map<String, Integer> updateEmployeeContribution(String department, String employeeId, int count) {
        String key = "user." + department + "." + employeeId;
        Integer currentValue = redisTemplate.opsForValue().get(key);
        if (currentValue == null) {
            currentValue = 0;
        }
        int newValue = currentValue + count;

        redisTemplate.opsForValue().set(key, newValue);

        // Update database contribution
        // Employee employee = employeeRepository.findByDepartmentAndEmpId(department,
        // employeeId);
        // if (employee != null) {
        // employee.setContribution(newValue);
        // employeeRepository.save(employee);
        // }

        Map<String, Integer> contribution = new HashMap<>();
        contribution.put(key, newValue);
        return contribution;
    }

    // Problem 22.

    public int getEmployeeContribution(String department) {
        // Form the Redis key pattern to search for all keys associated with the given
        // department
        String keyPattern = "user." + department + ".*";

        // Get all keys matching the pattern
        Set<String> keys = redisTemplate.keys(keyPattern);

        // Initialize total contribution count
        int totalContribution = 0;

        // Iterate through each key and fetch the contribution count
        for (String key : keys) {
            Integer count = redisTemplate.opsForValue().get(key);
            if (count != null) {
                totalContribution += count;
            }
        }

        return totalContribution;
    }

    /*
     * // Problem 23.
     * public int getTotalContributionByDepartment(String department) {
     * List<String> empIds =
     * employeeRepository.findEmployeeIdsByDepartment(department);
     * int totalContribution = 0;
     * for (String empId : empIds) {
     * String redisKey = "user." + department + "." + empId;
     * Integer contribution = redisTemplate.opsForValue().get(redisKey);
     * if (contribution != null) {
     * totalContribution += contribution;
     * } else {
     * int empContribution = fetchEmployeeContributionFromDB(department, empId);
     * totalContribution += empContribution;
     * redisTemplate.opsForValue().set(redisKey, empContribution, redisCacheTtl,
     * TimeUnit.MINUTES);
     * }
     * }
     * return totalContribution;
     * }
     * 
     * private int fetchEmployeeContributionFromDB(String department, String empId)
     * {
     * // Perform database operation to fetch employee contribution
     * // Assuming you have a method in EmployeeRepository to fetch contribution by
     * // department and employeeId
     * return employeeRepository.findContributionByDepartmentAndEmpId(department,
     * empId);
     * }
     * 
     */
}
