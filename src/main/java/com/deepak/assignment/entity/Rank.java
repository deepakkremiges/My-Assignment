package com.deepak.assignment.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ranks")
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rankdesc", nullable = false)
    private String rankDesc;

    @Column(name = "parentrankid")
    private Long parentRankId;

    @OneToMany(mappedBy = "rank")
    private List<Employee> employees;

    // Getters and setters omitted for brevity
}
