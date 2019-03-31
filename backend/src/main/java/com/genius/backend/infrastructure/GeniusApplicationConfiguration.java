package com.genius.backend.infrastructure;

import com.genius.backend.domain.model.alimy.Alimy;
import com.genius.backend.domain.model.alimy.AlimyDto;
import com.genius.backend.infrastructure.interceptor.LoggingInterceptor;
import com.genius.backend.infrastructure.properties.GeniusProperties;
import com.genius.backend.infrastructure.properties.RandomProperties;
import org.jooq.conf.Settings;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
@EnableConfigurationProperties({GeniusProperties.class, RandomProperties.class})
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class}, basePackages = {"com.genius.backend.domain"})
@EnableAutoConfiguration(exclude = {MybatisAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class GeniusApplicationConfiguration implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		resolver.setOneIndexedParameters(true);
		argumentResolvers.add(resolver);
		argumentResolvers.add(deviceHandlerMethodArgumentResolver());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(deviceResolverHandlerInterceptor());
		registry.addInterceptor(loggingInterceptor()).excludePathPatterns("/css/**", "/favicon.ico", "/js/**", "/log/**", "/three/**", "/webjars/**");
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/messages/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.KOREA);
		return resolver;
	}

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setSourceNameTokenizer(NameTokenizers.CAMEL_CASE)
				.setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE)
				.setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.createTypeMap(Alimy.class, AlimyDto.Response.class).addMappings(mapper -> mapper.map(Alimy::getUsername, AlimyDto.Response::setUsername));
		modelMapper.createTypeMap(Alimy.class, AlimyDto.ResponseForForm.class).addMappings(mapper -> mapper.map(Alimy::getId, AlimyDto.ResponseForForm::setAlimyId));
		modelMapper.createTypeMap(AlimyDto.RequestForSave.class, Alimy.class).addMappings(mapper -> mapper.skip(Alimy::setId));
		modelMapper.createTypeMap(AlimyDto.RequestForUpdate.class, Alimy.class).addMappings(mapper -> mapper.skip(Alimy::setAlimyUnit));
		return modelMapper;
	}

	@Bean
	public DeviceResolverHandlerInterceptor
	deviceResolverHandlerInterceptor() {
		return new DeviceResolverHandlerInterceptor();
	}

	@Bean
	public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() {
		return new DeviceHandlerMethodArgumentResolver();
	}

	@Bean
	public LoggingInterceptor loggingInterceptor() {
		return new LoggingInterceptor();
	}

	@Bean
	public Settings settings() {
		return new Settings().withRenderFormatted(true);
	}
}