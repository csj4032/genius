package com.genius.backend.domain.model.facebook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

public class WebhookParseTest {

	@Test
	public void parseTest() throws IOException {
		String resource = "{\"object\":\"page\",\"entry\":[{\"id\":\"1983940201642915\",\"time\":1548834316430,\"messaging\":[{\"sender\":{\"id\":\"1921658594596034\"},\"recipient\":{\"id\":\"1983940201642915\"},\"timestamp\":1548777671070,\"message\":{\"mid\":\"WwttaB3-gmMiFtrdZytcUHOvr3ZepqM1vHIe9BI68OVs0Omw367quHbGb1g3-UrOVkBbtd9ICdvzp97S-gsE3w\",\"seq\":6583,\"sticker_id\":369239263222822,\"attachments\":[{\"type\":\"image\",\"payload\":{\"url\":\"https:\",\"sticker_id\":369239263222822}}]}}]}]}";
		var objectMapper = new ObjectMapper();
		var message = objectMapper.readValue(resource, ResponseMessage.class);
	}
}