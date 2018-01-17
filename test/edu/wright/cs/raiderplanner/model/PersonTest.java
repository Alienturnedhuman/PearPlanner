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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wright.cs.raiderplanner.model.Person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Created by bijan on 04/05/2017.
 */

public class PersonTest {

	private ArrayList<String> personName = new ArrayList<>();
	private Person person1;
	private Person person2;

	/**
	 * set up for the test.
	 * Ran before the actual test.
	 * @throws Exception if person cannot be loaded properly
	 */
	@BeforeEach
	public void setUp() throws Exception {
		personName.add("Andrew");
		person1 = new Person("Mr", personName, "Odintsov", true, "Andrew.odi@apple.com");
		person2 = new Person("Mr", "Zilvinas Ceikauskas", true, "Zill.cei@apple.com");
	}

	@Test
	public void getFullName() throws Exception {
		// Testing with separate given names and family name passed to the constructor
		String expectedFullName = "Mr Andrew Odintsov";
		assertEquals(expectedFullName, person1.getFullName());

		person1 = new Person("Mr", personName, "Odintsov", false, "Andrew.odi@apple.com");
		expectedFullName = "Mr Odintsov Andrew";
		assertEquals(expectedFullName, person1.getFullName());

		personName.add("Ben");
		person1 = new Person("Mr", personName, "Odintsov", true, "Andrew.odi@apple.com");
		expectedFullName = "Mr Andrew Ben Odintsov";
		assertEquals(expectedFullName, person1.getFullName());

		person1 = new Person("Mr", personName, "Odintsov", false, "Andrew.odi@apple.com");
		expectedFullName = "Mr Odintsov Andrew Ben";
		assertEquals(expectedFullName, person1.getFullName());

		// Testing with full name passed to the constructor
		expectedFullName = "Mr Zilvinas Ceikauskas";
		assertEquals(expectedFullName, person2.getFullName());

		person2 = new Person("Mr", "Ceikauskas Zilvinas", false, "Zill.cei@apple.com");
		expectedFullName = "Mr Ceikauskas Zilvinas";
		assertEquals(expectedFullName, person2.getFullName());
	}

	@Test
	public void getFamilyName() throws Exception {
		// Testing with separate given names and family name passed to the constructor
		String expectedFamilyName = "Odintsov";
		assertEquals(expectedFamilyName, person1.getFamilyName());

		// Testing with full name passed to the constructor
		expectedFamilyName = "Ceikauskas";
		assertEquals(expectedFamilyName, person2.getFamilyName());

	}

	@Test
	public void getEmail() throws Exception {
		// Testing with separate given names and family name passed to the constructor
		String expectedFullName = "Andrew.odi@apple.com";
		assertEquals(expectedFullName, person1.getEmail());

		// Testing with full name passed to the constructor
		expectedFullName = "Zill.cei@apple.com";
		assertEquals(expectedFullName, person2.getEmail());
	}

	@Test
	public void getGivenNames() throws Exception {
		// Testing with separate given names and family name passed to the constructor
		assertEquals(personName, person1.getGivenNames());

		personName.add("Ben");
		person1 = new Person("Mr", personName, "Odintsov", true, "Andrew.odi@apple.com");
		assertEquals(personName, person1.getGivenNames());

		// Testing with full name passed to the constructor
		ArrayList<String> person2GivenNames = new ArrayList<>();
		person2GivenNames.add("Zilvinas");
		assertEquals(person2GivenNames, person2.getGivenNames());

		person2 = new Person("Mr", "Zilvinas Ben Ceikauskas", true, "Zill.cei@apple.com");
		person2GivenNames.add("Ben");
		assertEquals(person2GivenNames, person2.getGivenNames());
	}

	@Test
	public void getFamilyNameLast() throws Exception {
		// Testing with TRUE value for familyNameLast
		assertTrue(person1.getFamilyNameLast());

		// Testing with FALSE value for familyNameLast
		person2 = new Person("Mr", "Zilvinas Ceikauskas", false, "Zill.cei@apple.com");
		assertFalse(person2.getFamilyNameLast());
	}

	@Test
	public void getSalutation() throws Exception {
		String expectedSalutation = "Mr";
		assertEquals(expectedSalutation, person1.getSalutation());

		expectedSalutation = "Mr";
		assertEquals(expectedSalutation, person2.getSalutation());
	}

	@Test
	public void hasSalutation() throws Exception {
		// Testing with salutation
		assertEquals(true, person1.hasSalutation());

		// Testing without salutation
		person2 = new Person("", "Zilvinas Ceikauskas", true, "Zill.cei@apple.com");
		assertEquals(false, person2.hasSalutation());
	}

	@Test
	public void getPreferredName() throws Exception {
		// Testing with one name
		assertEquals("Andrew", person1.getPreferredName());
		assertEquals("Zilvinas", person2.getPreferredName());

		// Testing with multiple names
		personName.add("Ben");
		person1 = new Person("Mr", personName, "Odintsov", true, "Andrew.odi@apple.com");
		assertEquals(personName.get(0), person1.getPreferredName());

		person2 = new Person("Mr", "Zilvinas Ben Ceikauskas", true, "Zill.cei@apple.com");
		assertEquals("Zilvinas", person2.getPreferredName());

		// Testing with no names
		personName.clear();
		person1 = new Person("Mr", personName, "Odintsov", true, "Andrew.odi@apple.com");
		assertEquals("Odintsov", person1.getPreferredName());

		person2 = new Person("Mr", "Ceikauskas", true, "Zill.cei@apple.com");
		assertEquals("Ceikauskas", person2.getPreferredName());

		// Testing with Preferred Name set
		person1.setPreferredName("Andy");
		assertEquals("Andy", person1.getPreferredName());

		person2.setPreferredName("Zil");
		assertEquals("Zil", person2.getPreferredName());
	}

	@Test
	public void setFamilyName() throws Exception {
		person1.setFamilyName("Williams");
		assertEquals("Williams", person1.getFamilyName());

		person2.setFamilyName("Dickson");
		assertEquals("Dickson", person2.getFamilyName());
	}

	@Test
	public void setGivenNames() throws Exception {
		person1.setGivenNames("Zil Ceikauskas");
		ArrayList<String> names = new ArrayList<>();
		names.add("Zil");
		names.add("Ceikauskas");
		for (int i = 0; i < 2; i++) {
			assertEquals(names.get(i), person1.getGivenNames().get(i));
		}

		person2.setGivenNames("Andrew Odintsov");
		names.clear();
		names.add("Andrew");
		names.add("Odintsov");
		for (int i = 0; i < 2; i++) {
			assertEquals(names.get(i), person2.getGivenNames().get(i));
		}
	}

	@Test
	public void setName() throws Exception {
		person1.setName("Zil Ceikauskas", true);
		assertEquals("Zil", person1.getPreferredName());
		assertEquals("Ceikauskas", person1.getFamilyName());

		person1.setName("Ceikauskas Zil", false);
		assertEquals("Zil", person1.getPreferredName());
		assertEquals("Ceikauskas", person1.getFamilyName());
	}

	@Test
	public void setSalutation() throws Exception {
		person1.setSalutation("Dr");
		assertEquals("Dr", person1.getSalutation());

		person2.setSalutation("Ms");
		assertEquals("Ms", person2.getSalutation());
	}

	@Test
	public void validSalutation() throws Exception {
		// Testing a valid salutation
		boolean result = Person.validSalutation("Mr");
		assertEquals(true, result);

		// Testing an invalid salutation
		result = Person.validSalutation("7483mfd");
		assertEquals(false, result);

		result = Person.validSalutation("Mr mr");
		assertEquals(false, result);
	}

	@Test
	public void validName() throws Exception {
		// Testing a valid name
		boolean result = Person.validName("Andrew Odintsov");
		assertEquals(true, result);

		// Testing an invalid name
		result = Person.validName("Andrew35 Odintsov");
		assertEquals(false, result);
	}

	@Test
	public void validEmail() throws Exception {
		// Testing valid emails
		// Valid emails from: https://blogs.msdn.microsoft.com/testing123/2009/02/06/email-address-test-cases/
		String[] validEmails = {"email@domain.com", "firstname.lastname@domain.com",
				"email@subdomain.domain.com", "firstname+lastname@domain.com",
				"email@[123.123.123.123]", "“email”@domain.com", "1234567890@domain.com",
				"email@domain-one.com", "_______@domain.com", "email@domain.name",
				"email@domain.co.jp", "firstname-lastname@domain.com"};

		for (String validEmail : validEmails) {
			assertTrue(Person.validEmail(validEmail));
		}

		// Testing invalid emails
		// Invalid emails from: https://blogs.msdn.microsoft.com/testing123/2009/02/06/email-address-test-cases/
		String[] invalidEmails = {"plainaddress", "#@%^%#$@#$@#.com", "@domain.com",
				"Joe Smith </email@domain.com>/", "email.domain.com", "email@domain@domain.com",
				".email@domain.com", "email.@domain.com", "email..email@domain.com",
				"email@domain.com (Joe Smith)", "email@domain..com"};

		for (String invalidEmail : invalidEmails) {
			assertFalse(Person.validEmail(invalidEmail));
		}

	}

	/**
	 * Completed after the test.
	 * @throws Exception exception if person cannot be nullified
	 */
	@AfterEach
	public void tearDown() throws Exception {
		personName = null;
		person1 = null;
		person2 = null;
	}
}
