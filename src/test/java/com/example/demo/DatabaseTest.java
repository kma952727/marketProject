package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class DatabaseTest {
	
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/ex_db?useUnicode=true&serverTimezone=Asia/Seoul";
	private static final String USER = "root";
	private static final String PASS = "";
	
	@Autowired private DataSource dataSource;
	@Autowired private JdbcTemplate jdbcTemplate;
	
	@DisplayName("db연결이 잘되는가?")
	@Test
	public void isConnected() throws SQLException, ClassNotFoundException {
		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(URL, USER, PASS);
		assertTrue(!conn.isClosed());
	}
	
	@DisplayName("db연결이 잘되는가?(with properties)")
	@Test
	public void isConnected_properteis() throws SQLException {
		Connection conn = dataSource.getConnection();
		boolean isconnected = conn.isClosed();
		assertTrue(!isconnected);
	}
	
	@DisplayName("db에 삽입테스트")
	@Test
	public void isInserted() throws SQLException{
		Connection conn = dataSource.getConnection();
		jdbcTemplate.execute("insert into ex_table (name)"
				+ "values ('minju')");
		Integer count = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM ex_table WHERE name = 'minju'", Integer.class);
		assertEquals(count, 1);
	}
}
