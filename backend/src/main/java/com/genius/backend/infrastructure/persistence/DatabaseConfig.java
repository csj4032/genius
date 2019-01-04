package com.genius.backend.infrastructure.persistence;

import com.genius.backend.infrastructure.persistence.annotations.MasterConnection;
import com.genius.backend.infrastructure.persistence.annotations.SlaveConnection;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DatabaseConfig {

	@Primary
	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("dataSource") DataSource masterDataSource) {
		var propertiesHashMap = new HashMap<String,String>();
		propertiesHashMap.put("hibernate.ejb.naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy");
		return builder.dataSource(masterDataSource)
				.persistenceUnit("masterPersistenceUnit")
				.packages("com.genius.backend.domain.model")
				.properties(propertiesHashMap)
				.build();
	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory masterEntityManagerFactory) {
		return new JpaTransactionManager(masterEntityManagerFactory);
	}

	@Primary
	@Configuration
	@EnableTransactionManagement
	@EnableJpaRepositories(basePackages = "com.genius.backend.domain.repository", includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = MasterConnection.class))
	static class MasterJpaRepositoriesConfig {
	}

	@Bean(name = "slaveDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.slave")
	public DataSource slaveDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "slaveEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean slaveEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("slaveDataSource") DataSource slaveDataSource) {
		var propertiesHashMap = new HashMap<String, String>();
		propertiesHashMap.put("hibernate.ejb.naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy");
		return builder.dataSource(slaveDataSource)
				.persistenceUnit("slavePersistenceUnit")
				.packages("com.genius.backend.domain.model")
				.properties(propertiesHashMap)
				.build();
	}

	@Bean(name = "slaveTransactionManager")
	public PlatformTransactionManager slaveTransactionManager(@Qualifier("slaveEntityManagerFactory") EntityManagerFactory slaveTransactionManager) {
		return new JpaTransactionManager(slaveTransactionManager);
	}

	@Configuration
	@EnableTransactionManagement
	@EnableJpaRepositories(basePackages = "com.genius.backend.domain.repository",
			includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = SlaveConnection.class),
			entityManagerFactoryRef = "slaveEntityManagerFactory",
			transactionManagerRef = "slaveTransactionManager")
	static class SlaveJpaRepositoriesConfig {
	}
}