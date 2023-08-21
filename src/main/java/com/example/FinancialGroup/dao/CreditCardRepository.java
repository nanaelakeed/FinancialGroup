package com.example.FinancialGroup.dao;

import com.example.FinancialGroup.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {


    CreditCard findCreditCardByCreditNumber(@Param(value = "creditNumber")String creditNumber);


    @Query(value = "select c from CreditCard c where c.user.id=:user_id")
    List<CreditCard> findByUserId(@Param(value = "user_id")Long user_id);
}