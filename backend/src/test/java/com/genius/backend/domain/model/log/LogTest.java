package com.genius.backend.domain.model.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.backend.domain.model.alimy.Alimy;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class LogTest {

	AtomicInteger atomicInteger = new AtomicInteger();

	@Test
	public void logTest() throws IOException {
		var objectMapper = new ObjectMapper();
		var alimy = objectMapper.writeValueAsString(Alimy.builder().id(1).message("message").build());
		var log = Log.builder().id(atomicInteger.getAndIncrement()).type(LogType.HTTP_REQUEST).clazz(Alimy.class).value(alimy).build();
		var deAlimy = objectMapper.readValue(log.getValue(), log.getClazz());
		System.out.println(log);
		//System.out.println(deAlimy);
	}
}