package com.genius.backend.infrastructure.security.social;

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
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.kakao.connect.KakaoConnectionFactory;
import org.springframework.social.line.connect.LineConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

@Configuration
@EnableSocial
@EnableConfigurationProperties({KakaoProperties.class, LineProperties.class})
public class SocialConfigurer extends SocialConfigurerAdapter {

	protected KakaoProperties kakaoProperties;
	private LineProperties lineProperties;

	protected SocialConfigurer(KakaoProperties kakaoProperties, LineProperties lineProperties) {
		this.kakaoProperties = kakaoProperties;
		this.lineProperties = lineProperties;
	}

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
		KakaoConnectionFactory kakaoConnectionFactory = new KakaoConnectionFactory(kakaoProperties.getAppId(), kakaoProperties.getAppSecret());
		LineConnectionFactory lineConnectionFactory = new LineConnectionFactory(lineProperties.getAppId(), lineProperties.getAppSecret());
		connectionFactoryConfigurer.addConnectionFactory(kakaoConnectionFactory);
	}

	@Bean
	public SignInAdapter signInAdapter() {
		return new KakaoSignInAdapter();
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
		repository.setConnectionSignUp(new KakaoConnectionSignUp());
		return repository;
	}

	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
		return new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);
	}
}