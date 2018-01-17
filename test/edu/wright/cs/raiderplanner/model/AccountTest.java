/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar
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

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wright.cs.raiderplanner.model.Account;
import edu.wright.cs.raiderplanner.model.Person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by bijan on 04/05/2017.
 */
public class AccountTest {

	Person person;
	edu.wright.cs.raiderplanner.model.Account account;

	/**
	 * This test case creates Person and Account objects for use throughout this suite.
	 * @throws Exception to handle when a new Person or Account cannot be created successfully.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		person = new edu.wright.cs.raiderplanner.model.Person("Mr","Andrew Odintsov", true);
		account = new Account(person, "10012721-UG");
	}

	/**
	 * This test case verifies that the Account.getStudentDetails method returns correct info.
	 * @throws Exception to handle when the data returned does not match.
	 */
	@Test
	public void getStudentDetails() throws Exception {
		assertEquals(person, account.getStudentDetails());
	}

	/**
	 * This test case verifies that the Account.getStudentNumber method returns correct info.
	 * @throws Exception to handle when the returned number does not match what is expected.
	 */
	@Test
	public void getStudentNumber() throws Exception {
		assertEquals("10012721-UG", account.getStudentNumber());
	}

	/**
	 * This test case verifies that the Account details for a student can be set properly.
	 * @throws Exception to handle when a new Person cannot be created, when the student
	 * 				details cannot be set, or when the details returned are incorrect.
	 */
	@Test
	public void setStudentDetails() throws Exception {
		Person person2 = new Person("Dr","Zilvinas Ceikauskas", true, "zil.Cei@gmail.com");
		account.setStudentDetails(person2);
		assertEquals(person2, account.getStudentDetails());
	}

	/**
	 * This test case verifies that the student number can be successfully retrieved from
	 * 				the Account object.
	 * @throws Exception to handle when the student number cannot be set, or when the
	 * 				number returned is incorrect.
	 */
	@Test
	public void setStudentNumber() throws Exception {
		account.setStudentNumber("99222213-UG");
		assertEquals("99222213-UG", account.getStudentNumber());
	}

	/**
	 * This test case erases the data from the Person and Account objects to avoid any
	 * 				leftover data which could conflict with future tests.
	 * @throws Exception to handle when the Person or Account objects cannot be found.
	 */
	@AfterEach
	public void tearDown() throws Exception {
		person = null;
		account = null;
	}
}
