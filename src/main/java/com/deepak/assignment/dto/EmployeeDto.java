package com.deepak.assignment.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private String empId;
    private String fname;
    private String fullname;
    private Date dob;
    private Date doj;
    private int salary;
    private Long reportsTo;
    private Long deptId;
    private Long rankId;
    private String clientReqId;

}
