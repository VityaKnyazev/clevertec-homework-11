package ru.clevertec.ecl.knyazev.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ru.clevertec.ecl.knyazev.dao.parser.YAMLParser;
import ru.clevertec.ecl.knyazev.entity.GiftCertificate;
import ru.clevertec.ecl.knyazev.entity.Tag;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "ru.clevertec.ecl.knyazev.dao",  "ru.clevertec.ecl.knyazev.service", "ru.clevertec.ecl.knyazev.dto.mapper", "ru.clevertec.ecl.knyazev.controller" })
public class WebConfig {
	private YAMLParser yamlParser;

	private static final String PROPERTY_FILE = "application.yaml";

	public WebConfig() {
		yamlParser = new YAMLParser(PROPERTY_FILE);
	}

	@Bean
	public DataSource hikariDataSource() {
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
	public SessionFactory sessionFactory(DataSource hikariDataSource) {
		final HashMap<String, Object> properties = new HashMap<String, Object>();

		properties.put("hibernate.connection.datasource", hikariDataSource);
		properties.put("hibernate.current_session_context_class", yamlParser.getProperty("db", "hibernate", "sessionContext"));
		properties.put("hbm2ddl.auto", yamlParser.getProperty("db", "hibernate", "schema"));
		properties.put("hibernate.show.sql", yamlParser.getProperty("db", "hibernate", "showSql"));
		properties.put("hibernate.dialect", yamlParser.getProperty("db", "hibernate", "dialect"));
		properties.put("hibernate.connection.isolation", yamlParser.getProperty("db", "hibernate", "transactionIsolationValue"));

		ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();
		
		MetadataSources metadataSources = new MetadataSources(standardRegistry);		
		metadataSources.addAnnotatedClass(Tag.class);
		metadataSources.addAnnotatedClass(GiftCertificate.class);
		
		SessionFactory sessionFactory = metadataSources.getMetadataBuilder().build().buildSessionFactory();	
		
		return sessionFactory;
	}
}
