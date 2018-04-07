/*
 * Copyright (C) 2018 - Nathaniel Crossman
 *
 *
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.wright.cs.raiderplanner.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.derby.jdbc.EmbeddedDriver;

import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


/**
 * This is my test to see if SQl.
 * @author Nathaniel Crossman
 *
 */
public class ApacheDerbyDatabaseTest {

	private Connection conn = null;
	private EmbeddedDriver driver = null;
	private Statement sm = null;

	/**
	 * This method prints out the entire contents of the relational table.
	 * @throws SQLException get up
	 */
	public void checksData() throws SQLException {
		ResultSet rs = this.sm.executeQuery("SELECT * FROM strNames");
		while (rs.next()) {
			int stringD = rs.getInt("string_id");
			String randString = rs.getString("RandString");
			assertNotNull(stringD);
			assertNotNull(randString);
		}
	}

	/**
	 * Creates a table the database and inserts for values.
	 * @throws SQLException get up
	 */
	public void addData() throws SQLException {
		sm.execute("CREATE TABLE strNames( string_id int, RandString varchar(255))");
		sm.execute("INSERT INTO strNames values (1,'Webster St.')");
		sm.execute("INSERT INTO strNames values (1,'Webster St.')");
		sm.execute("INSERT INTO strNames values (2,'Union St.')");
		sm.execute("INSERT INTO strNames values (3,'Gold St.')");
		sm.execute("INSERT INTO strNames values (4,'Never St.')");
	}

	/**
	 * Setup the connection to the DB.
	 * @throws SQLException get up
	 */
	public void createConnection() throws SQLException {
		String protocol = "jdbc:derby:";
		String driverOne = "org.apache.derby.jdbc.EmbeddedDriver";
		// Removes the derby.log file creations
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			System.setProperty("derby.stream.error.file", "NUL");
		} else {
			System.setProperty("derby.stream.error.file", "/dev/null");
		}
		this.driver = new EmbeddedDriver();
		assertNotNull(driver); //Test Driver
		conn = driver.connect("jdbc:derby:memory:MyDbTest;create=true", new Properties());
		assertNotNull(conn); //Test Connection
		this.sm = conn.createStatement();
		assertNotNull(sm); //Test Statement
	}

	/**
	 * Main Setup Method.
	 */
	@Test
	public void before() {
		try {
			createConnection();
			addData();
			checksData();
		} catch (SQLException sqle) {
			printErrors(sqle);
		}
		try {
			this.sm.execute("DROP TABLE strNames");
		} catch (SQLException sqle) {
			printErrors(sqle);
		}

	}

	/**
	 * Main Setup Method.

	 */
	@After
	public void after() {
		try {
			if (conn != null) {
				conn.close();
				DriverManager.getConnection("jdbc:derby:;shutdown=true;deregister=false");
				assertTrue(true);
			}
		} catch (SQLException sqle) {
			printErrors(sqle);
		}
	}

	/**
	 * This method is used to print out error message.
	 * Additionally, if this method is ever executed it mean that the assert should fail.
	 * @param exce any SQL exception.
	 */
	public static void printErrors(SQLException exce) {
		if (exce.getSQLState().equals("XJ015")) {
			return; // Ignore
		}
		while (exce != null) {
			System.err.println("\n----- SQLException -----");
			System.err.println("  SQL State:  " + exce.getSQLState());
			System.err.println("  Error Code: " + exce.getErrorCode());
			System.err.println("  Message:    " + exce.getMessage());
			exce.printStackTrace(System.err);
			exce = exce.getNextException();
		}
		assertTrue(false);
	}
}



