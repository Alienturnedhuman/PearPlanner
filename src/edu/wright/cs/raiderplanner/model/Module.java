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

import edu.wright.cs.raiderplanner.controller.MainController;
import edu.wright.cs.raiderplanner.controller.MenuController;

import java.util.ArrayList;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17 at 20:59
 */
public class Module extends VersionControlEntity {
	// private data
	private ArrayList<Assignment> assignments = new ArrayList<>();
	private Person organizer;
	private String moduleCode;
	private ArrayList<TimetableEvent> timetable = new ArrayList<>();

	@Override
	protected void replace(VersionControlEntity receivedVce) {
		if (receivedVce instanceof Module) {
			Module castedVce = (Module) receivedVce;
			if (castedVce.getOrganiser() != null) {
				this.organizer = castedVce.getOrganiser();
			}
			if (castedVce.getModuleCode() != null) {
				this.moduleCode = castedVce.getModuleCode();
			}
			if (castedVce.getAssignments() != null) {
				this.assignments = castedVce.getAssignments();
			}
			if (castedVce.getAssignments() != null) {
				this.timetable = castedVce.getTimetable();
			}
		}

		super.replace(receivedVce);
	}


	/**
	 * ToString() method with boolean parameter.
	 * @param verbose True returns formatted string with Organizer, total assignments,
	 * 				and assignment details. False returns standard toString().
	 * @return Formatted String.
	 */
	public String toString(boolean verbose) {
		if (verbose) {
			StringBuilder verboseString = new StringBuilder();
			verboseString.append(toString());
			verboseString.append("\n");
			verboseString.append("Organizer: " + organizer.toString());
			verboseString.append("\n");
			verboseString.append("Total Assignments: " + Integer.toString(assignments.size()));
			verboseString.append("\n");

			int num = -1;
			int ii = assignments.size();

			while (++num < ii) {
				verboseString.append("\n");
				verboseString.append(assignments.get(num).toString(true));
			}

			return verboseString.toString();

		} else {
			return toString();
		}
	}

	@Override
	public String toString() {
		return "Module: " + this.name + " ( " + this.moduleCode + " )";
	}

	/**
	 * Getter for the assignments variable.
	 * @return ArrayList for variable assignments.
	 */
	public ArrayList<Assignment> getAssignments() {
		return assignments;
	}

	/**
	 * Getter for the organizer variable.
	 * @return Person for variable organizer.
	 */
	public Person getOrganiser() {
		return organizer;
	}

	/**
	 * Getter for the moduleCode variable.
	 * @return String for variable moduleCode.
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * Getter for retrieving the Timetable.
	 * @return ArrayList for variable timetable.
	 */
	public ArrayList<TimetableEvent> getTimetable() {
		return timetable;
	}

	/**
	 * Getter for the number of assignments.
	 * @return Integer of the number of assignments.
	 */
	public int getNoOfAssignments() {
		return this.assignments.size();
	}

	/**
	 * Calculates how much of this Module has been completed in percentage.
	 *
	 * @return int (0-100)
	 */
	public int calculateProgress() {
		if (this.assignments.size() == 0) {
			return 0;
		}

		int sum = 0;
		for (Assignment assignment : this.assignments) {
			sum += assignment.calculateProgress();
		}
		return sum / this.assignments.size();
	}

	/**
	 * Method to add an assignment.
	 * @param newAssignment Assignment variable to be added.
	 */
	public void addAssignment(Assignment newAssignment) {
		// initial set up code below - check if this needs updating
		if (!assignments.contains(newAssignment)) {
			assignments.add(newAssignment);
		}
	}

	/**
	 * Method to remove an assignment.
	 * @param newAssignment Assignment variable to be removed.
	 */
	public void removeAssignment(Assignment newAssignment) {
		// initial set up code below - check if this needs updating
		if (assignments.contains(newAssignment)) {
			assignments.remove(newAssignment);
		}
	}

	/**
	 * Method to set the organizer variable.
	 * @param newOrganizer Person variable.
	 */
	public void setOrganiser(Person newOrganizer) {
		organizer = newOrganizer;
	}

	/**
	 * Method to set the moduleCode variable.
	 * @param newModuleCode String variable.
	 */
	public void setModuleCode(String newModuleCode) {
		moduleCode = newModuleCode;
	}

	/**
	 * Method to add a Timetable Event.
	 * @param newTimetableEvent TimetableEvent variable that will be added.
	 */
	public void addTimetableEvent(TimetableEvent newTimetableEvent) {
		if (!timetable.contains(newTimetableEvent)) {
			timetable.add(newTimetableEvent);
		}
	}

	/**
	 * Method to remove a Time table event.
	 * @param newTimetableEvent TimetableEvent variable that will be removed.
	 */
	public void removeTimetableEvent(TimetableEvent newTimetableEvent) {
		if (timetable.contains(newTimetableEvent)) {
			timetable.remove(newTimetableEvent);
		}
	}

	@Override
	public void open(MenuController.Window current) {
		MainController.ui.moduleDetails(this, current);
	}

	/**
	 * Constructor.
	 * @param corganizer Person variable used to set the organizer variable.
	 * @param cmoduleCode String variable used to set the moduleCode variable.
	 */
	public Module(Person corganizer, String cmoduleCode) {
		setOrganiser(corganizer);
		setModuleCode(cmoduleCode);
	}
}
