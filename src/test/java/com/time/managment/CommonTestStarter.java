package com.time.managment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

@SpringBootTest(classes = ManagmentApplication.class)
@Testcontainers
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
public abstract class CommonTestStarter {
    @Autowired
    protected MockMvc mockMvc;
}
