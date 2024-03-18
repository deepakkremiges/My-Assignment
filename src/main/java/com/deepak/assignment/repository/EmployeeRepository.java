package com.deepak.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deepak.assignment.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Employee findByDepartmentAndEmpId(String department, String empId);
    /*
     * @Query("SELECT e.empId, d.deptname FROM Employee e JOIN e.department d")
     * 
     * // @Query("SELECT e.empId FROM Employee e WHERE e.department = :department")
     * // @Query("SELECT e.empId, d.deptName FROM Employee e JOIN e.department d
     * WHERE
     * // e.department.id = d.id")
     * List<String> findEmployeeIdsByDepartment(String department);
     * 
     * @Query("SELECT SUM(e.contribution) FROM Employee e WHERE e.department.deptname = :deptName AND e.empId = :empId"
     * )
     * 
     * // @Query("SELECT SUM(e.contribution) FROM Employee e WHERE e.department =
     * // :department AND e.empId = :empId")
     * // @Query("SELECT SUM(e.contribution) FROM Employee e WHERE
     * // e.department.deptname = :department AND e.empId = :empId")
     * 
     * Integer findContributionByDepartmentAndEmpId(String department, String
     * empId);
     * 
     */
}
