/*
 * Copyright (C) 2018 - Logan Krause, Corbin McGuire
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

package edu.wright.cs.raiderplanner.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Establishes connection to server, then executes DDL.
 * @author LoganKrause
 */
public class DatabaseConfigurator {
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String JDBC_URL = "jdbc;derby;ConnectingCreatingJavaDB;create=true";

	/**
	 * Establishes the Server and creates the database.
	 */
	private DatabaseConfigurator() {}

	/**
	 * Executes the DDL to create the database.
	 */
	public static void createDatabase() {

		try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
			if (conn != null) {

				String ddl = "CREATE TABLE ExceptionDB ( "
						+ "	ExID INTEGER NOT NULL, "
						+ "	Message VARCHAR(2000) NOT NULL, "
						+ "	Type VARCHAR(200) NOT NULL, "
						+ "	Date DATE NOT NULL, "
						+ "	Time TIME NOT NULL, "
						+ "	PRIMARY KEY (ExID) "
						+ ");";
				Statement statement = null;
				statement = conn.createStatement();
				statement.executeQuery(ddl);
				System.out.println("Connection Successful");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection Failed. Exception: " + e.toString());
		}
	}
}