/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar, Eric Sweet, Roberto C. Sánchez
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

import org.apache.commons.validator.routines.EmailValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * This class represents a person and associated details (names, salutation,
 * email, etc.).
 *
 * @author Team BRONZE on 4/27/17
 */
public class Person extends VersionControlEntity {

	private static Pattern salutationRegex = Pattern.compile("[a-zA-Z]*");
	private static Pattern nameRegex = Pattern.compile("[a-zA-z\\s]*");

	private ArrayList<String> givenNames;
	private String familyName;
	private String salutation;
	private String email;
	private boolean familyNameLast = true;

	/**
	 * Create a person from the provided parameters.  The <b>name</b> parameter
	 * is split to separate the family name from the given name(s).
	 *
	 * @param salutation The person's salutation, e.g., Mr., Mrs., Dr., etc.
	 * @param name The person's names, both given and family ("NAME1 NAME2 NAME3
	 * 				.... NAMEn")
	 * @param famNameLast true to indicate that family comes last in the
	 * 		<b>name</b> parameter; false to indicate it comes first
	 */
	public Person(String salutation, String name, Boolean famNameLast) {

		this(salutation, name, famNameLast, "");

	}

	/**
	 * Create a person from the provided parameters.
	 *
	 * @param salutation The person's salutation, e.g., Mr., Mrs., Dr., etc.
	 * @param givenNames The person's given name(s)
	 * @param famName The person's family name
	 * @param famNameLast true to indicate that family comes last in the
	 * 				<b>name</b> parameter; false to indicate it comes first
	 */
	public Person(String salutation, ArrayList<String> givenNames,
			String famName, Boolean famNameLast) {

		super(true);
		setFamilyName(famName);
		this.givenNames = new ArrayList<String>(givenNames);
		setSalutation(salutation);
		familyNameLast = famNameLast;
		email = "";

	}

	/**
	 * Create a person from the provided parameters.  The <b>name</b> parameter
	 * is split to separate the family name from the given name(s).
	 *
	 * @param salutation The person's salutation, e.g., Mr., Mrs., Dr., etc.
	 * @param name The person's given name(s)
	 * @param famNameLast true to indicate that family comes last in the
	 * 		<b>name</b> parameter; false to indicate it comes first
	 * @param newEmail The person's email address
	 */
	public Person(String salutation, String name, Boolean famNameLast, String newEmail) {

		setSalutation(salutation);
		setName(name, famNameLast);
		familyNameLast = famNameLast;
		email = newEmail;

	}

	/**
	 * Create a person from the provided parameters.  The <b>givenNames</b>
	 * parameter is split to separate multiple given names.
	 *
	 * @param salutation The person's salutation, e.g., Mr., Mrs., Dr., etc.
	 * @param givenNames The person's given name(s)
	 * @param famName The person's family name
	 * @param famNameLast true to indicate that family comes last; false to
	 * 				indicate it comes first
	 * @param newEmail The person's email address
	 */
	public Person(String salutation, String givenNames, String famName,
			Boolean famNameLast, String newEmail) {

		setSalutation(salutation);
		String personName;
		if (famNameLast) {
			personName = givenNames + " " + famName;
		} else {
			personName = famName + " " + givenNames;
		}
		setName(personName, famNameLast);
		email = newEmail;

	}

	/**
	 * Create a person from the provided parameters.
	 *
	 * @param salutation The person's salutation, e.g., Mr., Mrs., Dr., etc.
	 * @param givenNames The person's given name(s)
	 * @param famName The person's family name
	 * @param famNameLast true to indicate that family comes last; false to
	 * 				indicate it comes first
	 * @param newEmail The person's email address
	 */
	public Person(String salutation, ArrayList<String> givenNames, String famName,
			Boolean famNameLast, String newEmail) {

		setFamilyName(famName);
		this.givenNames = new ArrayList<String>(givenNames);
		setSalutation(salutation);
		familyNameLast = famNameLast;
		email = newEmail;

	}

	@Override
	protected void replace(VersionControlEntity receivedVce) {
		if (receivedVce instanceof Person) {
			Person castedVce = (Person) receivedVce;
			this.givenNames = castedVce.getGivenNames();
			this.familyName = castedVce.getFamilyName();
			this.salutation = castedVce.getSalutation();
			this.email = castedVce.getEmail();
			this.familyNameLast = castedVce.getFamilyNameLast();
		}
		super.replace(receivedVce);
	}

	/**
	 * Returns this person's full name.
	 *
	 * @return this person's full name
	 */
	public String getFullName() {
		if (familyNameLast) {
			return (salutation.length() > 0 ? salutation + " " : "")
					+ String.join(" ", givenNames) + " " + familyName;
		} else {
			return (salutation.length() > 0 ? salutation + " " : "") + familyName + " "
					+ String.join(" ", givenNames);
		}
	}

	/**
	 * Returns this person's family name.
	 *
	 * @return this person's family name
	 */
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * Sets this person's family name.
	 *
	 * @param newFamilyName this person's family name
	 */
	public void setFamilyName(String newFamilyName) {
		familyName = newFamilyName;
	}

	/**
	 * Returns this person's email address.
	 *
	 * @return this person's email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets this person's email address.
	 *
	 * @param newEmail this person's email address
	 */
	public void setEmail(String newEmail) {
		email = newEmail;
	}

	/**
	 * Returns a list of given names for this person.
	 *
	 * @return a list of given names for this person
	 */
	public ArrayList<String> getGivenNames() {
		return new ArrayList<String>(givenNames);
	}

	/**
	 * Sets the given names from a string with space separated names.
	 *
	 * @param nameStr a space separated String containing names
	 */
	public void setGivenNames(String nameStr) {
		String[] nameSplit = nameStr.split(" ");
		givenNames = new ArrayList<>(Arrays.asList(nameSplit));
	}

	/**
	 * Checker whether this Person specified his family name as last name.
	 *
	 * @return true if family name comes last, false otherwise
	 */
	public boolean getFamilyNameLast() {
		return familyNameLast;
	}

	/**
	 * Returns this person's salutation.
	 *
	 * @return this person's salutation
	 */
	public String getSalutation() {
		return salutation;
	}

	/**
	 * Sets this person's salutation.
	 *
	 * @param newSalutation this person's salutation
	 */
	public void setSalutation(String newSalutation) {
		salutation = newSalutation;
	}

	/**
	 * Returns true if this person has a salutation, false otherwise.
	 *
	 * @return true if this person has a salutation, false otherwise
	 */
	public boolean hasSalutation() {
		return salutation.length() > 0;
	}

	/**
	 * Returns this person's preferred name.
	 *
	 * @return this person's preferred name
	 */
	public String getPreferredName() {
		return name.length() > 0 ? name : (givenNames.size() > 0 ? givenNames.get(0) : familyName);
	}

	/**
	 * Sets this person's preferred name.
	 *
	 * @param newPreferredName this person's preferred name
	 */
	public void setPreferredName(String newPreferredName) {
		name = newPreferredName;
	}

	/**
	 * Sets this person's name from a string with space separated names. The
	 * family name position at the start or end of the name is indicated by
	 * boolean value.
	 *
	 * @param name The person's names, both given and family ("NAME1 NAME2 NAME3
	 * 				.... NAMEn")
	 * @param famNameLast true to indicate that family comes last in the
	 * 		<b>name</b> parameter; false to indicate it comes first
	 */
	public void setName(String name, boolean famNameLast) {
		String[] nameSplit = name.split(" ");
		familyNameLast = famNameLast;
		givenNames = new ArrayList<>();
		int number = -1;
		int ii = nameSplit.length;
		if (familyNameLast) {
			familyName = nameSplit[--ii];
		} else {
			familyName = nameSplit[++number];
		}
		while (++number < ii) {
			givenNames.add(nameSplit[number]);
		}
	}

	/**
	 * Checks whether the given String is a valid email.
	 *
	 * @param email The email address to validate
	 * @return true if the email is valid, false otherwise
	 */
	public static boolean validEmail(String email) {
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(email);
	}

	/**
	 * Checks whether the given String is a valid name.
	 *
	 * @param name The name to validate
	 * @return true if the name is valid, false otherwise
	 */
	public static boolean validName(String name) {
		return nameRegex.matcher(name).matches();
	}

	/**
	 * Checks whether the given String is a valid salutation.
	 *
	 * @param salutation The salutation to validate
	 * @return true if the salutation is valid, false otherwise
	 */
	public static boolean validSalutation(String salutation) {
		return salutationRegex.matcher(salutation).matches();
	}

	@Override
	public String toString() {
		return getFullName() + " ( " + getEmail() + " )";
	}

}
