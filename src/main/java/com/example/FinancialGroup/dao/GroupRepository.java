package com.example.FinancialGroup.dao;

import com.example.FinancialGroup.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}