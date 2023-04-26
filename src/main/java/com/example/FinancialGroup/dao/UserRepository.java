package com.example.FinancialGroup.dao;

import com.example.FinancialGroup.entity.Group;
import com.example.FinancialGroup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(value = "select u.groups from User u where u.id=:userid")
    List<Group> getGroups(@Param("userid") Long userid);
}