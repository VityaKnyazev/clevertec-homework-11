package ru.clevertec.ecl.knyazev.testconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.clevertec.ecl.knyazev.dao.parser.YAMLParser;

public class TestContainersConfig {
	private static final String PROPERTY_FILE = "testApplication.yaml";
	
	private YAMLParser yamlParser;	

	public TestContainersConfig() {
		yamlParser = new YAMLParser(PROPERTY_FILE);
	}
	
	@Bean(initMethod = "start", destroyMethod = "stop")
	public PostgreSQLContainer<?> postgreSQLContainer() {
		return new PostgresContainer(yamlParser.getProperty("testcontainers", "postgresqlDockerImage"));
	}
	
	@Bean(initMethod = "update", destroyMethod = "close")
	public Liquibase liquibase(PostgresContainer postgresContainer) throws DatabaseException, SQLException {
		String url = postgresContainer.getJdbcUrl();
		String username = postgresContainer.getUsername();
		String password = postgresContainer.getPassword();	
		
		
		Connection connection = DriverManager.getConnection(url, username, password);
		Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
		Liquibase liquibase = new Liquibase("/liquibase/db-test-sales-changelog.xml", new ClassLoaderResourceAccessor(), database);
		return liquibase;
	}
	
	public class PostgresContainer extends PostgreSQLContainer<PostgresContainer> {	
		private String JDBC_URL;
		
		private String DB_USER;
		
		private String DB_PASSWORD;
		
		private PostgresContainer(String dockerImage) {
			super(dockerImage);
			
			JDBC_URL = yamlParser.getProperty("db", "jdbcUrl");
			DB_USER = yamlParser.getProperty("db", "username");
			DB_PASSWORD = yamlParser.getProperty("db", "password");
		}
		

		@Override
		public void start() {
			super.start();
			System.setProperty(JDBC_URL, getJdbcUrl());
			System.setProperty(DB_USER, getUsername());
			System.setProperty(DB_PASSWORD, getPassword());		
		}

		@Override
		public void stop() {
			super.stop();
		}

	}
}
