package com.tutsnnd.reggie_program;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableCaching
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class ReggieProgramApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReggieProgramApplication.class, args);
	}

}
