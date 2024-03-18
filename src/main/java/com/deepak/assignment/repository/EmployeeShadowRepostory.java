package com.deepak.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deepak.assignment.entity.EmployeeShadow;

@Repository
public interface EmployeeShadowRepostory extends JpaRepository<EmployeeShadow, Long> {

}
