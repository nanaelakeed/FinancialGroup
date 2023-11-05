package com.example.FinancialGroup.AOP;


import com.example.FinancialGroup.service.MailService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


@Aspect
@Configuration
public class Config {

    private final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    private final MailService mailService;

    public Config(MailService mailService) {
        this.mailService = mailService;
    }

    @Before("execution(* com.example.FinancialGroup.AOP.*.*(..))")
    public void beforeAdvice(JoinPoint joinPoint){
        LOGGER.info("Before method -> {}",joinPoint.getSignature().getName());
    }

    @After("execution(* com.example.FinancialGroup.AOP.*.*(..))")
    public void afterAdvice(JoinPoint joinPoint){
        LOGGER.info("After method -> {}",joinPoint.getSignature().getName());
    }

    @AfterReturning("execution(* com.example.FinancialGroup.AOP.TestAOP.t (..))")
    public void aroundAdvice(JoinPoint joinPoint){
        System.out.println("arounddddddddd");
        LOGGER.info(joinPoint.getSignature().getName());
    }


    @After("execution(* com.example.FinancialGroup.controller.UserController.newUser (..))")
    public void afterSaveUser(JoinPoint joinPoint){
        this.mailService.sendEmail("afnanelakeed3@gmail.com","User saved","User is saved successfully");
        LOGGER.info(joinPoint.getSignature().getName());
    }





}
