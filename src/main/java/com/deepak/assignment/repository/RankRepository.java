package com.deepak.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deepak.assignment.entity.Rank;

@Repository
public interface RankRepository extends JpaRepository<Rank, Long> {

}
