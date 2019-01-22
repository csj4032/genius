package com.genius.backend.application.impl;

import com.genius.backend.domain.model.alimy.Alimy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.line.api.impl.LineTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LineSocailProvider extends AbstractSocialProvider {

	@Override
	public void sendAlimy(List<Alimy> alimies) {
		alimies.stream().filter(e -> e.getProviderId().equals("line")).forEach(e -> onSendAlimy(e));
	}

	@Override
	protected void onSendAlimy(Alimy alimy) {
		var lineTemplate = new LineTemplate(alimy.getUser().getAccessToken());
		lineTemplate.messagesOperations().sendPushMessage("");
	}
}