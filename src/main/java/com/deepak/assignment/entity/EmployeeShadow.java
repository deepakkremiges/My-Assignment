package com.deepak.assignment.entity;

import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee_shadow")
public class EmployeeShadow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "empid", nullable = false)
    private String empId;

    @Column(name = "fname", nullable = false)
    private String fname;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "dob", nullable = false)
    private Date dob;

    @Column(name = "doj", nullable = false)
    private Date doj;

    @Column(name = "salary", nullable = false)
    private int salary;

    @Column(name = "reportsto")
    private Long reportsTo;

    @ManyToOne
    @JoinColumn(name = "deptid")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "rankid")
    private Rank rank;

    @CreationTimestamp
    @Column(name = "createdat", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updatedat")
    private Date updatedAt;

    @Column(name = "client_reqid", nullable = false)
    private String clientReqId;

    public EmployeeShadow(Employee employee) {
        this.empId = employee.getEmpId();
        this.fname = employee.getFname();
        this.fullname = employee.getFullname();
        this.dob = employee.getDob();
        this.doj = employee.getDoj();
        this.salary = employee.getSalary();
        this.reportsTo = employee.getReportsTo();
        this.department = employee.getDepartment();
        this.rank = employee.getRank();
        this.clientReqId = employee.getClientReqId();
    }
}
