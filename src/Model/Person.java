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

package Model;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * PearPlanner. Created by Team BRONZE on 4/27/17
 */
public class Person extends VersionControlEntity {
	//private data
	private ArrayList<String> givenNames;
	private String familyName;
	private String salutation;
	private String email;
	private boolean familyNameLast = true;

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

	// public methods
	// getters
	/**
	 * Returns a full name of this Person.
	 *
	 * @return a String containing a full name.
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
	 * gets the family name of the user.

	 * @return familyName
	 */
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * gets the email address.
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns a list of given names for this Person.
	 * @return an ArrayList of String containing given names
	 */
	public ArrayList<String> getGivenNames() {
		return (ArrayList<String>) givenNames.clone();
	}

	/**
	 * Checker whether this Person specified his family name as last name.
	 * @return true if last, false otherwise.
	 */
	public boolean getFamilyNameLast() {
		return familyNameLast;
	}

	/**
	 * gets the salutation of the user.
	 * @return salutation
	 */
	public String getSalutation() {
		return salutation;
	}

	/**
	 * boolean if salutation exist.
	 * @return salutation.length() > 0
	 */
	public boolean hasSalutation() {
		return salutation.length() > 0;
	}

	/**
	 * Get the preferred name of this Person.
	 * @return a String containing the preferred name.
	 */
	public String getPreferredName() {
		return name.length() > 0 ? name : (givenNames.size() > 0 ? givenNames.get(0) : familyName);
	}

	// setters
	/**
	 * sets the family name of user.
	 * @param newFamilyName family name of user
	 */
	public void setFamilyName(String newFamilyName) {
		familyName = newFamilyName;
	}

	/**
	 * sets the preferred name of user.
	 * @param newPreferredName string of user preferred name
	 */
	public void setPreferredName(String newPreferredName) {
		name = newPreferredName;
	}

	/**
	 * Sets the given names from a string with space separated names.
	 * @param nameStr String containing names
	 */
	public void setGivenNames(String nameStr) {
		String[] nameSplit = nameStr.split(" ");
		givenNames = new ArrayList<>(Arrays.asList(nameSplit));
	}

	/**
	 * Sets the name from a string with space separated names Family name.
	 * position at start or end indicated by boolean value
	 * @param nameStr String containing names
	 * @param isFamilyNameLast is the family name at the end?
	 */
	public void setName(String nameStr, boolean isFamilyNameLast) {
		String[] nameSplit = nameStr.split(" ");
		familyNameLast = isFamilyNameLast;
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
	 * sets the email of the user.
	 * @param newEmail String containing email
	 */
	public void setEmail(String newEmail) {
		email = newEmail;
	}

	/**
	 * sets the salutation of the user.
	 * @param newSalutation string salutation of user
	 */
	public void setSalutation(String newSalutation) {
		salutation = newSalutation;
	}

	// static validator
	private static Pattern salutationRegex = Pattern.compile("[a-zA-Z]*");
	private static Pattern nameRegex = Pattern.compile("[a-zA-z\\s]*");

	/**
	 * Checks whether the given String is a valid email.
	 * @param email String to be checked.
	 * @return whether valid or not.
	 */
	public static boolean validEmail(String email) {
		EmailValidator validator = EmailValidator.getInstance();
		if (validator.isValid(email)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the given String is a valid name.
	 * @param name String to be checked.
	 * @return whether valid or not.
	 */
	public static boolean validName(String name) {
		return nameRegex.matcher(name).matches();
	}

	/**
	 * Checks whether the given String is a valid salutation.
	 * @param salutation String to be checked.
	 * @return whether valid or not.
	 */
	public static boolean validSalutation(String salutation) {
		// salutation is empty OR if it
		return salutationRegex.matcher(salutation).matches();
	}

	// constructors
	/**
	 * @param salutation String for salutation
	 * @param name "NAME1 NAME2 NAME3 .... NAMEn"
	 * @param famNameLast if true, last name is family name, if not, first is
	 */
	public Person(String salutation, String name, Boolean famNameLast) {
		setSalutation(salutation);
		setName(name, famNameLast);
		familyNameLast = famNameLast;
		email = "";
	}

	/**
	 * @param salutation String for salutation.
	 * @param givenNames Array list of strings for given names
	 * @param famName String for family name
	 * @param famNameLast true if family name is at the end
	 */
	public Person(String salutation, ArrayList<String> givenNames,
		String famName, Boolean famNameLast) {
		super(true);
		setFamilyName(famName);
		givenNames = (ArrayList<String>) givenNames.clone();
		setSalutation(salutation);
		familyNameLast = famNameLast;
		email = "";
	}

	/**
	 * @param salutation String for salutation
	 * @param name "NAME1 NAME2 NAME3 .... NAMEn"
	 * @param famNameLast if true, last name is family name, if not, first is
	 */
	public Person(String salutation, String name, Boolean famNameLast, String newEmail) {
		setSalutation(salutation);
		setName(name, famNameLast);
		familyNameLast = famNameLast;
		email = newEmail;
	}

	/**
	 * Pure String Constructor.
	 * @param salutation String for salutation
	 * @param givenNames String for given name
	 * @param famName String of the fam name
	 * @param famNameLast Boolean if fam name is last
	 * @param newEmail new email address
	 */
	public Person(String salutation, String givenNames, String famName,
		Boolean famNameLast, String newEmail) {
		setSalutation(salutation);
		String name;
		if (famNameLast) {
			name = givenNames + " " + famName;
		} else {
			name = famName + " " + givenNames;
		}
		setName(name, famNameLast);
		email = newEmail;
	}

	/**
	 * @param salutation String for salutation.
	 * @param givenNames Array list of strings for given names
	 * @param famName String for family name
	 * @param famNameLast true if family name is at the end
	 */
	public Person(String salutation, ArrayList<String> givenNames, String famName,
		Boolean famNameLast, String newEmail) {
		setFamilyName(famName);
		givenNames = (ArrayList<String>) givenNames.clone();
		setSalutation(salutation);
		familyNameLast = famNameLast;
		email = newEmail;
	}

	@Override
	public String toString() {
		return getFullName() + " ( " + getEmail() + " )";
	}
}
