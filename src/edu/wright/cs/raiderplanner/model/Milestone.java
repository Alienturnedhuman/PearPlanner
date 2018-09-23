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

import edu.wright.cs.raiderplanner.controller.MenuController.Window;

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
public class Milestone extends ModelEntity {

	private ArrayList<Task> tasks = new ArrayList<>();
	private Deadline deadline;

	// public methods

	// Constructors:
	/**
	 * Milestone class constructor.
	 * @param name name of deadline
	 * @param details details of deadline
	 * @param deadline time of deadline
	 */
	public Milestone(String name, String details, LocalDate deadline) {
		super(name, details);
		this.deadline = new Deadline(deadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
				+ "T00:00:01Z");
	}

	// Getters:

	/**
	 * Check whether this Milestone is complete.
	 *
	 * @return true if completed, false otherwise.
	 */
	public boolean isComplete() {
		for (Task t : this.tasks) {
			if (!t.isCheckedComplete()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Computes progress based on Task weighting and Tasks completed.
	 * Used for JavaFX
	 *
	 * @return String representation of progress in percentage.
	 */
	public String getProgressPercentage() {
		double completed = 0;
		for (Task t : this.tasks) {
			if (t.isCheckedComplete()) {
				completed += t.getWeighting();
			}
		}

		int result = (int) (completed / this.totalWeighting() * 100);

		return Integer.toString(result) + '%';
	}

	/**
	 * Returns an array of Tasks associated with this Milestone.
	 *
	 * @return an array of Tasks.
	 */
	public Task[] getTasks() {
		return this.tasks.toArray(new Task[this.tasks.size()]);
	}

	/**
	 * Returns the number of Tasks completed.
	 *
	 * @return integer representation of completed Tasks.
	 */
	public int tasksCompleted() {
		int completed = 0;
		for (Task t : this.tasks) {
			if (t.isCheckedComplete()) {
				completed++;
			}
		}
		return completed;
	}

	/**
	 * Returns the number of Tasks associated with this Milestone.
	 *
	 * @return number of Tasks.
	 */
	public int size() {
		return this.tasks.size();
	}

	/**
	 * Return the sum of all Tasks weighting.
	 *
	 * @return integer representation of weightings sum.
	 */
	public int totalWeighting() {
		int sum = 0;
		for (Task t : this.tasks) {
			sum += t.getWeighting();
		}
		return sum;
	}

	/**
	 * Returns a String representation of completed Tasks (e.g. 3/4).
	 * Used in JavaFX.
	 *
	 * @return String represenation of completed Tasks.
	 */
	public String getTaskCompletedAsString() {
		return this.tasksCompleted() + "/" + this.tasks.size();
	}

	/**
	 * Returns a String representation of the Deadline for this Milestone.
	 * Used in JavaFX.
	 *
	 * @return String representation of a Deadline.
	 */
	public String getDeadline() {
		return new SimpleDateFormat("dd/MM/yyyy").format(this.deadline.getDate());
	}

	/**
	 * Returns a Date object representing the Deadline of this Milestone.
	 *
	 * @return Date object.
	 */
	public Date getDeadlineDate() {
		return this.deadline.getDate();
	}

	// Setters:

	/**
	 * Add the given Task to this Milestone.
	 *
	 * @param task Task to be added.
	 * @return Whether the Task was successfully added.
	 */
	public boolean addTask(Task task) {
		if (this.tasks.contains(task)) {
			return false;
		}
		this.tasks.add(task);
		return true;
	}

	/**
	 * Checks if the Milestone contains the requested task.
	 *
	 * @param task Task to be checked for
	 * @return Whether the task is contained.
	 */
	public boolean containsTask(Task task) {
		return this.tasks.contains(task);
	}

	/**
	 * Add all given Tasks to this Milestone.
	 *
	 * @param tasks
	 * a Collection of Tasks to be added.
	 * @return whether the provided Tasks were added successfully.
	 */
	public boolean addTasks(Collection<Task> tasks) {
		for (Task curr : tasks) {
			if (this.tasks.contains(curr)) {
				return false;
			}
		}
		this.tasks.addAll(tasks);
		return true;
	}

	/**
	 * Replace the current list of Tasks with the provided Tasks.
	 *
	 * @param tasks Collection of Tasks.
	 */
	public void replaceTasks(Collection<Task> tasks) {
		this.tasks.clear();
		this.tasks.addAll(tasks);
	}

	/**
	 * Remove a given Task from this Milestone.
	 *
	 * @param task Task to be removed.
	 */
	public void removeTask(Task task) {
		this.tasks.remove(task);
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

	/* (non-Javadoc)
	 * @see edu.wright.cs.raiderplanner.model.ModelEntity#open
	 * (edu.wright.cs.raiderplanner.controller.MenuController.Window)
	 */
	@Override
	public void open(Window current) {
		// TODO Auto-generated method stub
	}

}
