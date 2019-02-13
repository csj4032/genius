package com.genius.backend;

import com.genius.backend.domain.model.alimy.AlimyStatusToEnumConverter;
import com.genius.backend.infrastructure.listener.GeniusEvent;
import com.genius.backend.infrastructure.properties.GeniusProperties;
import com.genius.backend.infrastructure.properties.RandomProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.FormatterRegistry;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@EnableRetry
@EnableCaching
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({GeniusProperties.class, RandomProperties.class})
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class}, basePackages = {"com.genius.backend.domain"})
@EnableAutoConfiguration(exclude = {MybatisAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class BackendApplication extends SpringBootServletInitializer implements ApplicationRunner {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BackendApplication.class);
	}

	public static void main(String[] args) {
		//SpringApplication.run(GeniusProjectApplication.class, args);
		new SpringApplicationBuilder(BackendApplication.class).listeners(e -> log.info(e.getClass().getCanonicalName())).run(args);
	}

	@Override
	public void run(ApplicationArguments args) {
		//if (!args.containsOption("server.port")) {
		//	Log.error("ERROR: --server.port argument is not found.");
		//System.exit(0);
		//}
	}

	@EventListener
	public void geniusEvent(GeniusEvent geniusEvent) {
		log.info("GeniusEvent Time : {}", geniusEvent.getEventTime());
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addFormatters(FormatterRegistry registry) {
				registry.addConverter(new AlimyStatusToEnumConverter());
			}
		};
	}
}