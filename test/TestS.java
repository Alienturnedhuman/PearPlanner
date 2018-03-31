/*
 * Copyright (C) 2017 - Nathaniel Crossman
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


import java.io.File;
import java.util.Properties;

import org.apache.derby.jdbc.EmbeddedDriver;

import java.sql.*;
/**
 * This is my test to see if SQl.
 * @author Nathaniel Crossman
 *
 */
public class TestS {

	/**
	 * This runs the code.
	 * @param args
	 *            look for me
	 */
	public static void main(String[] args) {
		String protocol = "jdbc:derby:";
		String driverOne = "org.apache.derby.jdbc.EmbeddedDriver";
		System.out.println("Where I at : " + new File(".").getAbsolutePath());
		EmbeddedDriver driver = null;
		Connection conn = null;
		
		//Removes the derby.log file creations 
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			System.setProperty("derby.stream.error.file", "NUL");
		} else {
			System.setProperty("derby.stream.error.file", "/dev/null");
		}
		
		try {
			driver = new EmbeddedDriver();
			System.out.println("Loaded the appropriate driver.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			conn = driver.connect("jdbc:derby:memory:MyDbTest;create=true", new Properties());
			System.out.println("Connected to database MyDbTest");	
			Statement sm = conn.createStatement();
			sm.execute("CREATE TABLE strNames( string_id int, RandString varchar(255))");
			System.out.println("Created table strNames");
			sm.execute("INSERT INTO strNames values (1,'Webster St.')");
			sm.execute("INSERT INTO strNames values (1,'Webster St.')");
			sm.execute("INSERT INTO strNames values (2,'Union St.')");
			sm.execute("INSERT INTO strNames values (3,'Gold St.')");
			sm.execute("INSERT INTO strNames values (4,'Never St.')");
			System.out.println("Add Data to table strNames");
			ResultSet rs = sm.executeQuery("SELECT * FROM strNames");

			while (rs.next()) {
				int stringD = rs.getInt("string_id");
				String randString = rs.getString("RandString");
				System.out.printf("string_id: %s, RandString: %s\n", stringD, randString);
			}
			sm.execute("DROP TABLE strNames");
			System.out.println("Removes table strNames");
		} catch (SQLException sqle) {
			printErrors(sqle);
		} finally {
			try {
				if (conn != null) {
					conn.close();
					DriverManager.getConnection("jdbc:derby:;shutdown=true;deregister=false");
				}
			} catch (SQLException sqle) {
				printErrors(sqle);
			}
		}
	}
/**
 * 
 * @param e
 */
	public static void printErrors(SQLException e) {
		if (e.getSQLState().equals("XJ015"))  {
			return; // Ignore
		}

		// Unwraps the entire exception chain to unveil the real cause of the
		// Exception.
		while (e != null) {
			System.err.println("\n----- SQLException -----");
			System.err.println("  SQL State:  " + e.getSQLState());
			System.err.println("  Error Code: " + e.getErrorCode());
			System.err.println("  Message:    " + e.getMessage());
			// for stack traces, refer to derby.log or uncomment this:
			e.printStackTrace(System.err);
			e = e.getNextException();
		}
	}
}
