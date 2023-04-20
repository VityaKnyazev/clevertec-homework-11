package ru.clevertec.ecl.knyazev.connection;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ru.clevertec.ecl.knyazev.parser.YAMLParser;

@Configuration
@ComponentScan(basePackages = "ru.clevertec.ecl.knyazev.dao")
public class AppConnectionConfig {
	private YAMLParser yamlParser;
	
	private static final String PROPERTY_FILE = "application.yaml";
	
	private AppConnectionConfig() {
		yamlParser = new YAMLParser(PROPERTY_FILE);
	}		
	
	@Bean
	private DataSource hikariDataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(yamlParser.getProperty("db", "driverClassName"));
		hikariConfig.setJdbcUrl(yamlParser.getProperty("db", "jdbcUrl"));
		hikariConfig.setUsername(yamlParser.getProperty("db", "username"));
		hikariConfig.setPassword(yamlParser.getProperty("db", "password"));
		hikariConfig.setMaximumPoolSize(Integer.valueOf(yamlParser.getProperty("db", "maxPoolSize")));
		hikariConfig.setConnectionTimeout(Long.valueOf(yamlParser.getProperty("db", "connectionTimeout")));
		
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		return hikariDataSource;
	}
	
	@Bean
	private JdbcTemplate jdbcTemplate(DataSource hikariDataSource) {
		return new JdbcTemplate(hikariDataSource);
	}
	
	@Bean
	private JdbcTransactionManager jdbcTransactionManager(DataSource hikariDataSource) {
		return new JdbcTransactionManager(hikariDataSource);
	}
	
	@Bean
	private TransactionTemplate transactionTemplate(JdbcTransactionManager jdbcTransactionManager) {
		TransactionTemplate transactionTemplate = new TransactionTemplate(jdbcTransactionManager);
		transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		
		return transactionTemplate;
	}
	 
}
