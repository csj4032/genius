package com.genius.backend.infrastructure.persistence;

import com.genius.backend.infrastructure.persistence.annotations.MasterConnection;
import com.genius.backend.infrastructure.persistence.annotations.SlaveConnection;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

public abstract class MyBatisConfig {

	public static final String BASE_PACKAGE = "com.genius.backend.domain.mapper";
	public static final String MAPPER_LOCATIONS_PATH = "classpath:/com/genius/backend/**/*.xml";
	public static final String TYPE_ALIASES_PACKAGE = "com.genius.backend";

	protected void configureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws IOException {
		var pathResolver = new PathMatchingResourcePatternResolver();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
		sessionFactoryBean.setMapperLocations(pathResolver.getResources(MAPPER_LOCATIONS_PATH));
		sessionFactoryBean.setPlugins(new Interceptor[]{new GeniusMapperInterceptor()});
	}

	@Configuration
	@MapperScan(basePackages = MyBatisConfig.BASE_PACKAGE, annotationClass = MasterConnection.class, sqlSessionFactoryRef = "masterSqlSessionFactory")
	static class MasterMyBatisConfig extends MyBatisConfig {

		@Bean(name = "masterSqlSessionFactory")
		public SqlSessionFactory masterSqlSessionFactory(@Qualifier("dataSource") DataSource masterDataSource) throws Exception {
			var sessionFactoryBean = new SqlSessionFactoryBean();
			configureSqlSessionFactory(sessionFactoryBean, masterDataSource);
			return sessionFactoryBean.getObject();
		}
	}

	@Configuration
	@MapperScan(basePackages = MyBatisConfig.BASE_PACKAGE, annotationClass = SlaveConnection.class, sqlSessionFactoryRef = "slaveSqlSessionFactory")
	static class SlaveMyBatisConfig extends MyBatisConfig {

		@Bean(name = "slaveSqlSessionFactory")
		public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource slaveDataSource) throws Exception {
			var sessionFactoryBean = new SqlSessionFactoryBean();
			configureSqlSessionFactory(sessionFactoryBean, slaveDataSource);
			return sessionFactoryBean.getObject();
		}
	}
}