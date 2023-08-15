package com.example.FinancialGroup.AOP;


import org.springframework.stereotype.Component;

@Component
public class TestAOP {

    public void t(){
        System.out.println("The first");
    }

    public void anotherMethod(){
        System.out.println("Anything");
    }
}
