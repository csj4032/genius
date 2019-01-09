package com.genius.backend.domain.model.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.backend.domain.model.alimy.Alimy;
import com.genius.backend.domain.repository.LogRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mobile.device.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@ActiveProfiles(value = "office")
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
public class LogTest {

	@Autowired
	private LogRepository repository;

	@Test
	@Ignore
	public void logTest() throws IOException {
		var objectMapper = new ObjectMapper();
		var httpRequest = HttpRequestLog.builder().path("/alimy").build();
		var log = Log.builder().id(1).type(LogType.HTTP_REQUEST).value(objectMapper.writeValueAsString(httpRequest)).build();
		var desHttpRequest = objectMapper.readValue(log.getValue(), log.getType().getClazz());
		System.out.println(desHttpRequest);
	}

	@Test
	public void saveTest() throws Exception {
		var objectMapper = new ObjectMapper();
		var req1 = HttpRequestLog.builder().deviceType(DeviceType.MOBILE.name()).platformType(DevicePlatform.ANDROID.name()).remoteIp("10.10.101.1").path("alimy").build();
		var log1 = Log.builder().type(LogType.HTTP_REQUEST).value(objectMapper.writeValueAsString(req1)).build();
		//var log2 = Log.builder().type(LogType.HTTP_RESPONSE).value(objectMapper.writeValueAsString(HttpResponseLog.builder().data(new String("1")).build())).build();
		this.repository.save(log1);
		//this.repository.save(log2);
	}

	@Test
	public void selectTest() throws Exception {
		var objectMapper = new ObjectMapper();
		var log1 = repository.findById(1l).get();
		var req = objectMapper.readValue(log1.getValue(), log1.getType().getClazz());
		System.out.println(req);
	}
}