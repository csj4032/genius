package com.genius.backend.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Configuration
public class MailConfiguration {

	private static String EMAIL_TEMPLATE_ENCODING = StandardCharsets.UTF_8.name();

	@Bean
	public ResourceBundleMessageSource emailMessageSource() {
		final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("/mail/MailMessages");
		return messageSource;
	}

//	@Bean
//	public TemplateEngine emailTemplateEngine() {
//		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//		templateEngine.addTemplateResolver(textTemplateResolver());
//		templateEngine.addTemplateResolver(htmlTemplateResolver());
//		templateEngine.addTemplateResolver(stringTemplateResolver());
//		templateEngine.setTemplateEngineMessageSource(emailMessageSource());
//		return templateEngine;
//	}

//	private ITemplateResolver textTemplateResolver() {
//		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//		templateResolver.setOrder(Integer.valueOf(1));
//		templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
//		templateResolver.setPrefix("/mail/");
//		templateResolver.setSuffix(".txt");
//		templateResolver.setTemplateMode(TemplateMode.TEXT);
//		templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
//		templateResolver.setCacheable(false);
//		return templateResolver;
//	}
//
//	private ITemplateResolver htmlTemplateResolver() {
//		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//		templateResolver.setOrder(Integer.valueOf(2));
//		templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
//		templateResolver.setPrefix("/mail/");
//		templateResolver.setSuffix(".html");
//		templateResolver.setTemplateMode(TemplateMode.HTML);
//		templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
//		templateResolver.setCacheable(false);
//		return templateResolver;
//	}
//
//	private ITemplateResolver stringTemplateResolver() {
//		final StringTemplateResolver templateResolver = new StringTemplateResolver();
//		templateResolver.setOrder(Integer.valueOf(3));
//		templateResolver.setTemplateMode("HTML5");
//		templateResolver.setCacheable(false);
//		return templateResolver;
//	}
}
