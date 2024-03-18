package com.deepak.assignment.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequest {

    private Long id;
    private String empId;
    private String fname;
    private String fullname;
    private Date dob;
    private Date doj;
    private int salary;
    private Long reportsTo;

}
