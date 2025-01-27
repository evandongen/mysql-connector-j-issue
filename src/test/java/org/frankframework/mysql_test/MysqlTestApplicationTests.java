package org.frankframework.mysql_test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class MysqlTestApplicationTests {

	/**
	 * Should use the automatically configured {@link JdbcTemplate} to interact with the database.
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		// Make sure to start with an empty table before each test
		jdbcTemplate.execute("TRUNCATE IBISTEMP");

		// Insert some records
		IntStream.range(1, 11).forEach(i -> {
			jdbcTemplate.execute("INSERT INTO IBISTEMP (TCHAR, TCLOB) VALUES ('Y', 'hallo " + i + "')");
		});
	}

	@Test
	void testUpdateResultSet() {
		// This should not throw when updating a result set
		assertDoesNotThrow(() -> {
			try (Connection connection = jdbcTemplate.getDataSource().getConnection();
				 StringWriter fos = new StringWriter()) {
				// Make sure to set up a statement which results in an updatable ResultSet
				PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT TCLOB, TTIMESTAMP, TCHAR, TKEY FROM IBISTEMP WHERE TTIMESTAMP IS NULL ORDER BY TCHAR, TKEY",
						ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE
				);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						// Update the timestamp in the resulting record and update it in the database
						resultSet.updateTimestamp(2, new Timestamp((new Date()).getTime()));
						resultSet.updateRow();
					}
				}
			}
		});
	}
}
