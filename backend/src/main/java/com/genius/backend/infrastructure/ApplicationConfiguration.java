package com.genius.backend.infrastructure;

import com.genius.backend.domain.model.alimy.Alimy;
import com.genius.backend.domain.model.alimy.AlimyDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		resolver.setOneIndexedParameters(true);
		argumentResolvers.add(resolver);
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
		modelMapper.createTypeMap(Alimy.class, AlimyDto.Response.class).addMappings(mapper -> mapper.map(src -> src.getUsername(), AlimyDto.Response::setUsername));
		modelMapper.createTypeMap(AlimyDto.RequestForSave.class, Alimy.class).addMappings(mapper -> {
			mapper.skip(Alimy::setId);
		});
		modelMapper.createTypeMap(AlimyDto.RequestForUpdate.class, Alimy.class).addMappings(mapper -> {
			mapper.skip(Alimy::setAlimyUnit);
		});
		return modelMapper;
	}
}