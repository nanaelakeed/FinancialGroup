package com.example.FinancialGroup.dao;

import com.example.FinancialGroup.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {


    CreditCard findCreditCardByCreditNumber(@Param(value = "creditNumber")String creditNumber);
}