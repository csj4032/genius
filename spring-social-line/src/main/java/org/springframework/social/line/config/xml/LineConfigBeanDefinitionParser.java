package org.springframework.social.line.config.xml;

import org.springframework.social.config.xml.AbstractProviderConfigBeanDefinitionParser;
import org.springframework.social.line.config.support.LineApiHelper;
import org.springframework.social.line.connect.LineConnectionFactory;
import org.springframework.social.line.security.LineAuthenticationService;
import org.springframework.social.security.provider.SocialAuthenticationService;

public class LineConfigBeanDefinitionParser extends AbstractProviderConfigBeanDefinitionParser {

	public LineConfigBeanDefinitionParser() {
		super(LineConnectionFactory.class, LineApiHelper.class);
	}

	@Override
	protected Class<? extends SocialAuthenticationService<?>> getAuthenticationServiceClass() {
		return LineAuthenticationService.class;
	}
}