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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;

/**
 * An Activity with a duration and an associated list of tasks.
 *
 * @author Zilvinas Ceikauskas
 */
public class Activity extends Event {

	private static final long serialVersionUID = -486727251420646703L;

	private ArrayList<Task> taskList = new ArrayList<>();
	private int activityQuantity;
	private QuantityType type;

	/**
	 * Create a new activity from the given parameters.
	 *
	 * @param name name of activity
	 * @param details details of the activity
	 * @param date date the activity will take place
	 * @param duration duration of the activity
	 * @param activityQuantity how much of the activity is there to be completed
	 * @param type type of activity
	 */
	public Activity(String name, String details, LocalDate date, int duration,
			int activityQuantity, String type) {
		super(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "T00:00:01Z");
		this.setName(name);
		this.setDetails(details);
		this.duration = duration;
		this.activityQuantity = activityQuantity;
		this.type = QuantityType.get(type);
	}

	/**
	 * Create a copy of the given Activity.
	 *
	 * @param activity Activity object to be copied.
	 */
	public Activity(Activity activity) {
		super();
		this.name = activity.name;
		this.details = activity.details;
		this.date = activity.date;
		this.duration = activity.duration;
		this.activityQuantity = activity.activityQuantity;
		this.type = activity.type;
		this.taskList = activity.taskList;
	}

	/**
	 * @return an array of Tasks related to this Activity.
	 */
	public Task[] getTasks() {
		// TODO: Change this to return a List<Task> and change users to conform
		return this.taskList.toArray(new Task[this.taskList.size()]);
	}

	/**
	 * @return the Quantity of this Activity.
	 */
	public int getActivityQuantity() {
		return activityQuantity;
	}

	/**
	 * @return the Quantity Type of this Activity.
	 */
	public QuantityType getType() {
		return type;
	}

	/**
	 * @return a formatted String representation of this Activity's date.
	 */
	public String getDateString() {
		// TODO: fix this to use SimpleDateFormat
		return this.date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
				+ " " + this.date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
				+ " " + this.date.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Add a single Task to this Activity.
	 *
	 * @param task Task to be added.
	 *
	 * @return true if the task was added, false if the task was already in the list.
	 */
	public boolean addTask(Task task) {
		if (this.taskList.contains(task)) {
			return false;
		}

		this.taskList.add(task);
		return true;
	}

	/**
	 * Add all given Tasks to this Activity.
	 *
	 * @param tasks a Collection of Tasks to be added.
	 *
	 * @return true if the collection of taskList was added (even if only one is
	 * 				new), false if all of the taskList were already in the list.
	 */
	public boolean addTasks(Collection<Task> tasks) {
		// TODO: Returning a boolean from this seems odd; rethink the objective
		if (this.taskList.containsAll(tasks)) {
			return false;
		}

		this.taskList.addAll(tasks);
		return true;
	}

	/**
	 * Replace the current list of Tasks with the provided Tasks.
	 *
	 * @param tasks Collection of Tasks.
	 */
	public void replaceTasks(Collection<Task> tasks) {
		this.taskList.clear();
		this.taskList.addAll(tasks);
	}

	/**
	 * Remove a given Task from this Activity.
	 *
	 * @param task Task to be removed.
	 */
	public void removeTask(Task task) {
		this.taskList.remove(task);
	}

	/**
	 * Set the Duration of this Activity.
	 *
	 * @param duration integer value of the Duration.
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Set the Quantity of this Activity.
	 *
	 * @param activityQuantity integer value of the Quantity.
	 */
	public void setActivityQuantity(int activityQuantity) {
		this.activityQuantity = activityQuantity;
	}

	/**
	 * Set the QuantityType of this Activity.
	 *
	 * @param type String representation of the QuantityType.
	 */
	public void setType(String type) {
		if (QuantityType.exists(type)) {
			this.type = QuantityType.get(type);
		}
	}

	@Override
	public void open(MenuController.Window current) {
		try {
			MainController.ui.activityDetails(this);
		} catch (IOException e) {
			UiManager.reportError("Unable to open view file");
		}
	}

}