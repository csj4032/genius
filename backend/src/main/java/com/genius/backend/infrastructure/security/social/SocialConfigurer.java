package com.genius.backend.infrastructure.security.social;

import com.genius.backend.infrastructure.security.social.property.FacebookProperties;
import com.genius.backend.infrastructure.security.social.property.KakaoProperties;
import com.genius.backend.infrastructure.security.social.property.LineProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.kakao.connect.KakaoConnectionFactory;
import org.springframework.social.line.connect.LineConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

@Configuration
@EnableSocial
@EnableConfigurationProperties({KakaoProperties.class, LineProperties.class, FacebookProperties.class})
public class SocialConfigurer extends SocialConfigurerAdapter {

	@Autowired
	private KakaoProperties kakaoProperties;

	@Autowired
	private LineProperties lineProperties;

	@Autowired
	private FacebookProperties facebookProperties;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
		connectionFactoryConfigurer.addConnectionFactory(getKakaoConnectionFactory());
		connectionFactoryConfigurer.addConnectionFactory(getLineConnectionFactory());
		connectionFactoryConfigurer.addConnectionFactory(getFacebookConnectionFactory());
	}

	@Bean
	public SignInAdapter signInAdapter() {
		return new GeniusSignInAdapter();
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
		repository.setConnectionSignUp(new GeniusConnectionSignUp());
		return repository;
	}

	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
		return new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);
	}

	@NotNull
	private OAuth2ConnectionFactory getKakaoConnectionFactory() {
		return new KakaoConnectionFactory(kakaoProperties.getAppId(), kakaoProperties.getAppSecret());
	}

	@NotNull
	private OAuth2ConnectionFactory getLineConnectionFactory() {
		var lineConnectionFactory = new LineConnectionFactory(lineProperties.getAppId(), lineProperties.getAppSecret(), lineProperties.getMessageAccessToken());
		lineConnectionFactory.setScope(lineProperties.getScope());
		return lineConnectionFactory;
	}

	@NotNull
	private OAuth2ConnectionFactory getFacebookConnectionFactory() {
		var facebookConnectionFactory = new FacebookConnectionFactory(facebookProperties.getAppId(), facebookProperties.getAppSecret(), facebookProperties.getAppNamespace());
		return facebookConnectionFactory;
	}
}