package com.example.FinancialGroup;

import com.example.FinancialGroup.AOP.TestAOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FinancialGroupApplication implements CommandLineRunner {

	@Autowired
	private TestAOP testAOP;

	public static void main(String[] args) {
		SpringApplication.run(FinancialGroupApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		testAOP.t();
		testAOP.anotherMethod();
	}
}
