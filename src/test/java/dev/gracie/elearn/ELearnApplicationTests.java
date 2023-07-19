package dev.gracie.elearn;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ELearnApplicationTests {
	@Test
	void contextLoads() {
	}
	@Test
	void testDatabaseConnection() {
		DriverManagerDataSource dataSource =
				new DriverManagerDataSource("jdbc:mysql://localhost:3306");
		try {
			Connection connection = dataSource.getConnection("root", "Semicolon");
			System.out.println(connection);
			assertThat(connection).isNotNull();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
