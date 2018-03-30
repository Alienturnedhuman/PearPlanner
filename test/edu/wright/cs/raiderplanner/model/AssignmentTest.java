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

import edu.wright.cs.raiderplanner.model.Assignment;
import edu.wright.cs.raiderplanner.model.Person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by bijan on 12/05/2017.
 */
public class AssignmentTest {
	Assignment assignment;
	Person person1;
	Person person2;
	ExamEvent examEvent1;

	/**
	 * This test case creates two Person objects and an Assignment object for use in
	 * 			this test suite.
	 * @throws Exception to handle when new Person or Assignment objects cannot be created.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		person1 = new Person("Dr.", "Mark Fisher", true);
		person2 = new Person("Dr.", "Steven Laycock", true);
		assignment = new Exam(30, person1, person1, person2, 100, examEvent1);
	}

	/**
	 * This test case removes all data from the Person and Assignment objects after each run
	 * 			of the test suite to avoid any orphan data interfering with further tests.
	 * @throws Exception to handle when the Person or Assignment objects cannot be accessed.
	 */
	@AfterEach
	public void tearDown() throws Exception {
		person1 = null;
		person2 = null;
		assignment = null;
	}

	@Test
	public void toStringTest() throws Exception {
		String expected = "Assignment '" + assignment.getName() + "'";
		assertEquals(expected, assignment.toString());
	}

	@Test
	public void toStringTest2() throws Exception {
		// Testing with a true verbose
		StringBuilder testString2 = new StringBuilder();
		testString2.append(assignment);
		testString2.append("\n");
		testString2.append("Total marks: " + 100);
		testString2.append("\n");
		testString2.append("Total weighting: " + 30);

		testString2.append("\n");
		testString2.append("Set By: " + person1.toString());
		testString2.append("\n");
		testString2.append("Marked By: " + person1.toString());
		testString2.append("\n");
		testString2.append("Reviewed By: " + person2.toString());

		assertEquals(testString2.toString(), assignment.toString(true));

		// Testing with a false verbose
		assertEquals(assignment.toString(), assignment.toString(false));
	}

}
