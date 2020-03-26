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

import java.io.Serializable;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public class Account implements Serializable {
	// private data
	private Person studentDetails;
	private String studentNumber;

	// public methods

	/**
	 * gets Student Details.
	 * @return studentDetails.
	 */
	public Person getStudentDetails() {
		return studentDetails;
	}

	/**
	 * gets Student Number.
	 * @return studentNumber.
	 */
	public String getStudentNumber() {
		return studentNumber;
	}

	/**
	 * sets Student Details.
	 * @param newStudentDetails : given value is Person type
	 */
	public void setStudentDetails(Person newStudentDetails) {
		studentDetails = newStudentDetails;
	}

	/**
	 * sets Student Number.
	 * @param newStudentNumber : given value is String
	 */
	public void setStudentNumber(String newStudentNumber) {
		studentNumber = newStudentNumber;
	}

	/**
	 * Constructor.
	 * @param studentDetails : given value is Person type
	 * @param studentNumber  given value is String type
	 */
	public Account(Person studentDetails, String studentNumber) {
		this.studentDetails = studentDetails;
		this.studentNumber = studentNumber;
	}
}
