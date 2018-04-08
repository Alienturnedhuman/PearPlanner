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

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public class Exam extends Assignment {
	// private data
	private Exam resit = null;
	private ExamEvent timeSlot = null;

	@Override
	protected void replace(VersionControlEntity receivedVce) {
		if (receivedVce instanceof Exam) {
			Exam castedVce = (Exam) receivedVce;
			if (castedVce.getResit() != null) {
				this.resit = castedVce.getResit();
			}
			if (castedVce.getTimeSlot() != null) {
				this.timeSlot = castedVce.getTimeSlot();
			}
		}

		super.replace(receivedVce);
	}

	/**
	 * This Getter method gets the Resit value.
	 * @return
	 */
	public Exam getResit() {
		return resit;
	}

	/**
	 * This Getter method gets the Exam event time slot.
	 * @return
	 */
	public ExamEvent getTimeSlot() {
		return timeSlot;
	}


	/**
	 * Class Constructors.
	 * @param cweighting : given value of type int
	 * @param csetBy : given value of type Person
	 * @param cmarkedBy : given value of type Person
	 * @param creviewedBy : given value of type Person
	 * @param cmarks : given value of type int
	 * @param ctimeSlot : given value of type ExamEvent
	 * @param cresit : given value of type Exam
	 */
	public Exam(int cweighting, Person csetBy,
			Person cmarkedBy, Person creviewedBy, int cmarks, ExamEvent ctimeSlot, Exam cresit) {
		super(cweighting, csetBy, cmarkedBy, creviewedBy, cmarks);
		timeSlot = ctimeSlot;
		resit = cresit;
	}

	/**
	 * Class Constructors.
	 * @param cweighting : given value of type int
	 * @param csetBy : given value of type Person
	 * @param cmarkedBy : given value of type Person
	 * @param creviewedBy : given value of type Person
	 * @param cmarks : given value of type int
	 * @param ctimeSlot : given value of type ExamEvent
	 */
	public Exam(int cweighting, Person csetBy, Person cmarkedBy,
			Person creviewedBy, int cmarks, ExamEvent ctimeSlot) {
		super(cweighting, csetBy, cmarkedBy, creviewedBy, cmarks);
		timeSlot = ctimeSlot;
		resit = null;
	}
}
