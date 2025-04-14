package com.time.managment.service;

import com.time.managment.CommonTestStarter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.NoSuchElementException;

@Sql(scripts = "/dataSource/fill-tables.sql",
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class PresenceServiceTest extends CommonTestStarter {
    @Autowired
    PresenceService service;

    @Test
    void getPresences_TimesheetNotExists() {
        Assertions.assertThrows(NoSuchElementException.class, () -> service.getPresences(-999919));
    }

    @Test
    void getPresences_TimesheetExists() {
        var answer = service.getPresences(123456);
        System.out.println(answer.toString());
    }

    @Test
    void savePresence() {

    }
}