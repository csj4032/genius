package com.genius.backend.application.impl;

import com.genius.backend.application.SocialProvider;
import com.genius.backend.domain.model.alimy.Alimy;

public abstract class AbstractSocialProvider implements SocialProvider {

	protected abstract void onSendAlimy(Alimy alimy);
}
