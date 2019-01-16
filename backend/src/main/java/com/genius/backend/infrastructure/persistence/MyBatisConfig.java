package com.genius.backend.infrastructure.persistence;

import com.genius.backend.infrastructure.persistence.annotations.MasterConnection;
import com.genius.backend.infrastructure.persistence.annotations.SlaveConnection;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.inject.Named;
import javax.sql.DataSource;
import java.io.IOException;

public abstract class MyBatisConfig {

	public static final String BASE_PACKAGE = "com.genius.backend.domain.mapper";

	private MybatisProperties mybatisProperties;

	public MyBatisConfig(MybatisProperties mybatisProperties) {
		this.mybatisProperties = mybatisProperties;
	}

	protected void configureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws IOException {
	}

	MybatisProperties getMybatisProperties() {
		return this.mybatisProperties;
	}

	@Configuration
	@EnableConfigurationProperties({MybatisProperties.class})
	@MapperScan(basePackages = MyBatisConfig.BASE_PACKAGE, annotationClass = MasterConnection.class, sqlSessionFactoryRef = "masterSqlSessionFactory")
	static class MasterMyBatisConfig extends MyBatisConfig {

		public MasterMyBatisConfig(MybatisProperties mybatisProperties) {
			super(mybatisProperties);
		}

		protected void configureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws IOException {
			sessionFactoryBean.setDataSource(dataSource);
			sessionFactoryBean.setTypeAliasesPackage(getMybatisProperties().getTypeAliasesPackage());
			sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(getMybatisProperties().getMapperLocations()[0]));
			sessionFactoryBean.setPlugins(new Interceptor[]{new GeniusMapperInterceptor()});
		}

		@Bean(name = "masterSqlSessionFactory")
		public SqlSessionFactory masterSqlSessionFactory(@Named("dataSource") DataSource masterDataSource) throws Exception {
			var sessionFactoryBean = new SqlSessionFactoryBean();
			configureSqlSessionFactory(sessionFactoryBean, masterDataSource);
			return sessionFactoryBean.getObject();
		}
	}

	@Configuration
	@EnableConfigurationProperties({MybatisProperties.class})
	@MapperScan(basePackages = MyBatisConfig.BASE_PACKAGE, annotationClass = SlaveConnection.class, sqlSessionFactoryRef = "slaveSqlSessionFactory")
	static class SlaveMyBatisConfig extends MyBatisConfig {

		public SlaveMyBatisConfig(MybatisProperties mybatisProperties) {
			super(mybatisProperties);
		}

		protected void configureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws IOException {
			sessionFactoryBean.setDataSource(dataSource);
			sessionFactoryBean.setTypeAliasesPackage(getMybatisProperties().getTypeAliasesPackage());
			sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(getMybatisProperties().getMapperLocations()[0]));
			sessionFactoryBean.setPlugins(new Interceptor[]{new GeniusMapperInterceptor()});
		}

		@Bean(name = "slaveSqlSessionFactory")
		public SqlSessionFactory slaveSqlSessionFactory(@Named("slaveDataSource") DataSource slaveDataSource) throws Exception {
			var sessionFactoryBean = new SqlSessionFactoryBean();
			configureSqlSessionFactory(sessionFactoryBean, slaveDataSource);
			return sessionFactoryBean.getObject();
		}
	}
}