package com.genius.backend.interfaces.alimy;

import com.genius.backend.domain.model.alimy.AlimyDto;
import com.genius.backend.domain.model.alimy.AlimyStatus;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ActiveProfiles(value = "real")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlimyControllerTest {

	@Autowired
	private WebTestClient webClient;

	@Test
	@Ignore
	public void alimyTest() {
		this.webClient.get().uri("/alimy").exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(AlimyDto.Response.class);
	}

	@Test
	public void alimySave() {
		var body = AlimyDto.RequestForSave.RequestForSaveBuilder()
				.subject("subject")
				.message("message")
				.userId(1)
				.status(AlimyStatus.START)
				.unitType(new AlimyDto.UnitType().builder()
						.seconds("0")
						.minutes("0,10,20,30,40,50")
						.hours("*")
						.dayOfMonth("??")
						.month("*")
						.dayOfWeek("1-5")
						.year("2018-2060")
						.build())
				.build();
		this.webClient
				.post()
				.uri("/alimy/save")
				.body(Mono.just(body), AlimyDto.RequestForSave.class)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isBadRequest();
	}
}