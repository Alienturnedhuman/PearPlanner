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
 * Created by Team BRONZE on 4/27/17
 */
public abstract class Assignment extends VersionControlEntity {
	protected ArrayList<Task> tasks = new ArrayList<>();
	protected ArrayList<Requirement> requirements = new ArrayList<>();
	protected int weighting;
	protected Person setBy = null;
	protected Person markedBy = null;
	protected Person reviewedBy = null;
	protected int marks;
	protected StateType state;  // this may not be needed as we can work it out

	@Override
	protected void replace(VersionControlEntity receivedVce) {
		if (receivedVce instanceof Assignment) {
			Assignment castedVce = (Assignment) receivedVce;
			// this.tasks = castedVce.getTasks();
			// this.requirements = castedVce.getRequirements();
			this.weighting = castedVce.getWeighting();
			if (castedVce.getSetBy() != null) {
				this.setBy = castedVce.getSetBy();
			}
			if (castedVce.getMarkedBy() != null) {
				this.markedBy = castedVce.getMarkedBy();
			}
			if (castedVce.getReviewedBy() != null) {
				this.reviewedBy = castedVce.getReviewedBy();
			}
			this.marks = castedVce.getMarks();
			// this.state = castedVce.getState();
		}
		super.replace(receivedVce);
	}

	/**
	 * outputs State Type.
	 *
	 */
	public enum StateType {
		IN_PROGRESS, DEADLINE_PASSED, NOT_STARTED
	}

	// public methods
	// getters
	@Override
	public String toString() {
		return "Assignment '" + name + "'";
	}

	/**
	 * @param verbose : given value is a boolean type
	 * @return verboseString : returns StringBuilder object
	 */
	public String toString(boolean verbose) {
		if (verbose) {
			StringBuilder verboseString = new StringBuilder();
			verboseString.append(toString());
			verboseString.append("\n");
			verboseString.append("Total marks: " + Integer.toString(marks));
			verboseString.append("\n");
			verboseString.append("Total weighting: " + Integer.toString(weighting));

			verboseString.append("\n");
			verboseString.append("Set By: " + setBy.toString());
			verboseString.append("\n");
			verboseString.append("Marked By: " + markedBy.toString());
			verboseString.append("\n");
			verboseString.append("Reviewed By: " + reviewedBy.toString());

			return verboseString.toString();
		} else {
			return toString();
		}
	}

	/**
	 * gets tasks.
	 * @return tasks.
	 */
	public ArrayList<Task> getTasks() {
		return tasks;
	}

	/**
	 * gets Requirements.
	 * @return requirements.
	 */
	public ArrayList<Requirement> getRequirements() {
		return requirements;
	}

	/**
	 * gets Weight.
	 * @return weights.
	 */
	public int getWeighting() {
		return weighting;
	}

	/**
	 * gets SetBy.
	 * @return setBy.
	 */
	public Person getSetBy() {
		return setBy;
	}

	/**
	 * gets MarkedBy.
	 * @return markedBy.
	 */
	public Person getMarkedBy() {
		return markedBy;
	}

	/**
	 * gets ReviewedBy.
	 * @return reviewedBy.
	 */
	public Person getReviewedBy() {
		return reviewedBy;
	}

	/**
	 * gets Marks.
	 * @return marks.
	 */
	public int getMarks() {
		return marks;
	}

	/**
	 * gets State String.
	 * @return state.
	 */
	public StateType getState() {
		return state;
	}

	// Setters:

	/**
	 * Add a Task to this Assignment.
	 *
	 * @param task Task to be added
	 */
	public void addTask(Task task) {
		this.tasks.add(task);
		task.addAssignmentReference(this);
	}

	/**
	 * Removes the given Task from this assignment.
	 *
	 * @param task Task to be removed
	 * @return true if found and deleted, false otherwise
	 */
	public boolean removeTask(Task task) {
		task.removeAssignmentReference(this);
		return this.tasks.remove(task);
	}

	/**
	 * Add a Requirement to this Assignment.
	 *
	 * @param requirement Task to be added
	 */
	public void addRequirement(Requirement requirement) {
		this.requirements.add(requirement);
	}

	/**
	 * Removes the given Requirement from this Assignment.
	 *
	 * @param requirement Requirement to be removed
	 * @return true if found and deleted, false otherwise
	 */
	public boolean removeRequirement(Requirement requirement) {
		return this.requirements.remove(requirement);
	}

	/**
	 * Calculates how much of this Assignment has been completed in percentage.
	 *
	 * @return int (0-100)
	 */
	public int calculateProgress() {
		if (this.requirements.size() == 0 && this.tasks.size() == 0) {
			return 0;
		}

		int sum = 0;
		int num = 0;
		for (Requirement req : this.requirements) {
			sum += req.requirementProgress() * 100;
			num++;
		}

		for (Task task : this.tasks) {
			if (task.getRequirements().length > 0) {
				sum += task.calculateProgress();
				num++;
			}
		}

		// TODO Revisit #92, investigate and determine the proper course of action
		try {
			return sum / num;
		} catch (ArithmeticException e) {
			return 0;
		}
	}

	@Override
	public void open(MenuController.Window current) {
		MainController.ui.assignmentDetails(this, current);
	}

	/**
	 * Class Constructor.
	 * @param cweighting : given value is int
	 * @param csetBy : given value is Person
	 * @param cmarkedBy : given value is Person
	 * @param creviewedBy : given value is Person
	 * @param cmarks : given value is int
	 */
	public Assignment(int cweighting, Person csetBy, Person cmarkedBy,
			Person creviewedBy, int cmarks) {
		weighting = cweighting;
		setBy = csetBy;
		markedBy = cmarkedBy;
		reviewedBy = creviewedBy;
		marks = cmarks;
		//MainController.getSPC().getPlanner().getDeadlineNotifications().put(this, new boolean[2]);
	}
}
