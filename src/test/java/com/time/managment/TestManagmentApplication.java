package com.time.managment;

import org.springframework.boot.SpringApplication;

public class TestManagmentApplication {

	public static void main(String[] args) {
		SpringApplication.from(ManagmentApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
