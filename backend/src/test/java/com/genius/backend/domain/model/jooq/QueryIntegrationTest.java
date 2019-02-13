package com.genius.backend.domain.model.jooq;

import com.genius.backend.domain.model.log.LogType;
import org.jooq.DSLContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.genius.backend.domain.db.tables.Logs.LOGS;

@ActiveProfiles(value = "office")
@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryIntegrationTest {

	@Autowired
	private DSLContext dSLContext;

	@Test
	public void givenValidData_whenInserting_thenSucceed() {
		dSLContext.insertInto(LOGS)
				.set(LOGS.TYPE, LogType.HTTP_REQUEST.getCode())
				.set(LOGS.VALUE, "{}")
				.execute();
	}
}