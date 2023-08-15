package com.example.FinancialGroup.dao;

import com.example.FinancialGroup.entity.Group;
import com.example.FinancialGroup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "select g.users from Group g where g.groupId=:groupId")
    List<User> getAllByGroupId(@Param(value = "groupId")Long groupId);
}