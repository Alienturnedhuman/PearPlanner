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
import edu.wright.cs.raiderplanner.view.UiManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public class Task extends ModelEntity {
	// private data
	private static final long serialVersionUID = 5768237547422537188L;
	private ArrayList<Task> dependencies = new ArrayList<>();
	private Deadline deadline;
	private ArrayList<Requirement> requirements = new ArrayList<>();
	private boolean checkedComplete;
	private int weighting;
	private TaskType type;
	private ArrayList<Assignment> assignments = new ArrayList<>();

	// public methods

	// Getters:
	public String getDeadline() {
		return new SimpleDateFormat("dd/MM/yyyy").format(this.deadline.getDate());
	}

	public Date getDeadlineDate() {
		return this.deadline.getDate();
	}

	public int getWeighting() {
		return this.weighting;
	}

	/**
	 * Wrapper for JavaFX TableView.
	 *
	 * @return.
	 */
	public boolean isCheckedComplete() {
		return canCheckComplete() && checkedComplete;
	}

	public TaskType getType() {
		return this.type;
	}

	public Task[] getDependencies() {
		return this.dependencies.toArray(new Task[this.dependencies.size()]);
	}

	public Requirement[] getRequirements() {
		return this.requirements.toArray(new Requirement[this.requirements.size()]);
	}

	public int requirementCount() {
		return requirements.size();
	}

	/**
	 * Returns the number of complete in Requirements in this Task.
	 *
	 * @return integer
	 */
	public int requirementsComplete() {
		int r1 = 0;
		int i1 = -1;
		int ii = requirements.size();
		while (++i1 < ii)	{
			if (requirements.get(i1).isComplete()) {
				r1++;
			}
		}
		return r1;
	}

	/**
	 * Calculates how much of this Task has been completed in percentage.
	 *
	 * @return int (0-100)
	 */
	public int calculateProgress() {
		if (this.requirementCount() == 0) {
			return 0;
		} else {
			return (this.requirementsComplete() * 100) / this.requirementCount();
		}
	}

	/**
	 * Returns an array of Assignments to which this Task relates.
	 *
	 * @return array of Assignments.
	 */
	public Assignment[] getAssignmentReferences() {
		return this.assignments.toArray(new Assignment[this.assignments.size()]);
	}

	/**
	 * Checks whether all dependencies of this Task are complete.
	 *
	 * @return true if complete, false otherwise.
	 */
	public boolean dependenciesComplete() {
		int indexValue = -1;
		int ii = dependencies.size();
		while (++indexValue < ii) {
			if (!dependencies.get(indexValue).isCheckedComplete()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether this Task has any dependencies.
	 *
	 * @return true if does, false otherwise.
	 */
	public boolean hasDependencies() {
		return dependencies.size() > 0;
	}

	/**
	 * Same as canCheckComplete(), wrapper for TableView.
	 *
	 * @return.
	 */
	public boolean isPossibleToComplete() {
		return canCheckComplete();
	}

	/**
	 * Checks whether this Task can be checked as complete. If it cannot, makes sure it is marked as
	 * incomplete.
	 *
	 * @return.
	 */
	public boolean canCheckComplete() {
		int i1 = -1;
		int ii = requirements.size();
		while (++i1 < ii) {
			if (!requirements.get(i1).isComplete()) {
				this.checkedComplete = false;
				return false;
			}
		}
		if (this.dependenciesComplete()) {
			return true;
		} else {
			this.checkedComplete = false;
			return false;
		}
	}

	/**
	 * Checks whether this Task already contains a given dependency.
	 *
	 * @param dep dependency to be checked for
	 * @return true or false
	 */
	public boolean containsDependency(Task dep) {
		return this.dependencies.contains(dep);
	}

	/**
	 * Checks whether this Task already contains a given Requirement.
	 *
	 * @param requirement requirement to be checked for
	 * @return true or false
	 */
	public boolean containsRequirement(Requirement requirement) {
		return this.requirements.contains(requirement);
	}

	/**
	 * Returns the Name of the Task (used for JavaFX).
	 *
	 * @return Name of the task
	 */
	@Override
	public String toString() {
		return this.name;
	}

	// Setters:

	/**
	 * Add a Requirement to the current Task.
	 *
	 * @param req requirement to be added
	 */
	public void addRequirement(Requirement req) {
		this.requirements.add(req);
		this.canCheckComplete();
	}

	/**
	 * Add a Task to the list of dependencies for the current Task.
	 *
	 * @param task Task to be added
	 */
	public void addDependency(Task task) {
		this.dependencies.add(task);
		this.canCheckComplete();
	}

	/**
	 * Replaces the current Requirements with the given ones.
	 *
	 * @param requirements list of requirements
	 */
	public void replaceRequirements(Collection<Requirement> requirements) {
		this.requirements.clear();
		this.requirements.addAll(requirements);
		this.canCheckComplete();
	}

	/**
	 * Replaces the current Dependencies with the given ones.
	 *
	 * @param dependencies list of Tasks
	 */
	public void replaceDependencies(Collection<Task> dependencies) {
		this.dependencies.clear();
		this.dependencies.addAll(dependencies);
		this.canCheckComplete();
	}

	/**
	 * Removes a given Task from the dependencies list.
	 *
	 * @param dependency Task to be removed
	 * @return whether the dependency has been removed successfully
	 */
	public boolean removeDependency(Task dependency) {
		return this.dependencies.remove(dependency);
	}

	/**
	 * Removes a given Requirement from the requirements list.
	 *
	 * @param requirement Requirement to be removed
	 * @return whether the Requirement has been removed successfully
	 */
	public boolean removeRequirement(Requirement requirement) {
		return this.requirements.remove(requirement);
	}

	/**
	 * Toggle complete.
	 */
	public void toggleComplete() {
		if (this.isCheckedComplete()) {
			this.checkedComplete = false;
		} else if (this.canCheckComplete()) {
			this.checkedComplete = true;
		}
	}

	/**
	 * Mark as complete/incomplete.
	 *
	 * @param c1 boolean value.
	 */
	public void setComplete(boolean c1) {
		this.checkedComplete = c1;
	}

	/**
	 * Set a new deadline.
	 *
	 * @param date date to be set as a new deadline
	 */
	public void setDeadline(LocalDate date) {
		this.deadline.setDate(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
				+ "T00:00:01Z");
	}

	/**
	 * Set a new weighting for this Task.
	 *
	 * @param weighting .
	 */
	public void setWeighting(int weighting) {
		this.weighting = weighting;
	}

	/**
	 * Set a new type for this Task.
	 *
	 * @param type String representation of a type
	 */
	public void setType(String type) {
		if (TaskType.exists(type)) {
			this.type = TaskType.get(type);
		}
	}

	/**
	 * Add a reference to an Assignment to this task. (Used for completing Assignment Requirements).
	 *
	 * @param assignment Assignment which should be linked with this Task.
	 */
	public void addAssignmentReference(Assignment assignment) {
		if (!this.assignments.contains(assignment)) {
			this.assignments.add(assignment);
		}
	}

	/**
	 * Removes a reference from the list of Assignments this Task relates to.
	 *
	 * @param assignment Assignment to be removed.
	 */
	public void removeAssignmentReference(Assignment assignment) {
		this.assignments.remove(assignment);
	}

	@Override
	public void open(MenuController.Window current) {
		try {
			MainController.ui.taskDetails(this);
		} catch (IOException e) {
			UiManager.reportError("Unable to open view file");
		}
	}

	/**
	 * Class Constructors.
	 * @param name : given value is String
	 * @param details : given value is String
	 * @param deadline : given value is LocalDate
	 * @param weighting : given value is int
	 * @param type : given value is String
	 */
	public Task(String name, String details, LocalDate deadline, int weighting, String type) {
		super(name, details);
		this.deadline = new Deadline(deadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
				+ "T00:00:01Z");
		this.weighting = weighting;
		this.type = TaskType.get(type);
	}
}
