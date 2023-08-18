package com.example.FinancialGroup.dao;

import com.example.FinancialGroup.entity.CreditCard;
import com.example.FinancialGroup.entity.Group;
import com.example.FinancialGroup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(value = "select u.groups from User u where u.id=:userId")
    List<Group> getGroups(@Param(value = "userId") Long userId);


    @Query(value = "select c from CreditCard c where c.user.id=:userId")
    List<CreditCard> getUserCards(@Param(value = "userId")Long userId);


}