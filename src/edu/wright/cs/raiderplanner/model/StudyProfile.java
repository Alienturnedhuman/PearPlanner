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
import java.util.ArrayList;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public class StudyProfile extends VersionControlEntity {
	private ArrayList<Module> modules;
	private ArrayList<Milestone> milestones;
	private ArrayList<ExtensionApplication> extensions;
	private ArrayList<Event> calendar = new ArrayList<>();
	private int year;
	private int semesterNo;
	private boolean current;

	/**
	 * This method gets the Modules.
	 * @return returns an array of type Module
	 */
	public Module[] getModules() {
		Module[] module = new Module[this.modules.size()];
		module = this.modules.toArray(module);
		return module;
	}

	/**
	 * This method gets a list of Milestones.
	 * @return returns an array of type Milestones
	 */
	public Milestone[] getMilestones() {
		Milestone[] milestone = new Milestone[this.milestones.size()];
		milestone = this.milestones.toArray(milestone);
		return milestone;
	}

	/**
	 * This method gets a list of  Application Extension.
	 * @return returns an array of type Application Extension
	 */
	public ExtensionApplication[] getExtensions() {
		ExtensionApplication[] extension = new ExtensionApplication[this.extensions.size()];
		extension = this.extensions.toArray(extension);
		return extension;
	}

	/**
	 * Returns a calendar containing all the Events of this Study Profile.
	 *
	 * @return ArrayList of Events
	 */
	public ArrayList<Event> getCalendar() {
		return calendar;
	}

	/**
	 * This method gets a ArrayList of Tasks.
	 * These Task themselves can contain a list of their own Task.
	 * @return  an ArrayList of tasks
	 */
	public ArrayList<Task> getTasks() {
		ArrayList<Task> tasks = new ArrayList<>();
		this.modules.forEach(e -> e.getAssignments().forEach(ee -> tasks.addAll(ee.getTasks())));
		return tasks;
	}

	/**
	 * Whether this StudyProfile is set as current.
	 *
	 * @return true if current, else otherwise.
	 */
	public boolean isCurrent() {
		return current;
	}

	/**
	 * Set/unset this StudyProfile as the current profile of the StudyPlanner.
	 *
	 * @param current value to be set
	 */
	public void setCurrent(boolean current) {
		this.current = current;
	}

	/**
	 * Add an Event to the calendar of this Study Profile.
	 *
	 * @param event Event to be added.
	 */
	public void addEventToCalendar(Event event) {
		if (!calendar.contains(event)) {
			calendar.add(event);
		}
	}

	/**
	 * Get Name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get Year.
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Get Semester Number.
	 * @return
	 */
	public int getSemesterNo() {
		return semesterNo;
	}

	/**
	 * Whether this StudyProfile matches the given details.
	 *
	 * @param myear	   year
	 * @param msemesterNo semester number
	 * @return true if matches, false otherwise.
	 */
	public boolean matches(int myear, int msemesterNo) {
		return myear == year && msemesterNo == semesterNo;
	}

	// Setters:

	/**
	 * Adds a Milestone to this StudyProfile.
	 *
	 * @param milestone Milestone to be added.
	 */
	public void addMilestone(Milestone milestone) {
		this.milestones.add(milestone);
	}

	/**
	 * Removes a Milestone from this StudyProfile.
	 *
	 * @param milestone Milestone to be removed.
	 * @return whether the Milestone was removed successfully.
	 */
	public boolean removeMilestone(Milestone milestone) {
		return this.milestones.remove(milestone);
	}

	@Override
	public void open(MenuController.Window current) {
		try {
			MainController.ui.studyProfileDetails(this);
		} catch (IOException e) {
			UiManager.reportError("Unable to open view file");
		}
	}

	/**
	 * Class Constructors.
	 * @param initialHubFile Initial file
	 */
	public StudyProfile(HubFile initialHubFile) {
		this.milestones = new ArrayList<>();

		this.modules = initialHubFile.getModules();
		this.extensions = initialHubFile.getExtensions();

		this.year = initialHubFile.getYear();
		this.semesterNo = initialHubFile.getSemester();
		this.version = initialHubFile.getVersion();
		this.name = initialHubFile.getSemesterName();
		this.details = initialHubFile.getSemesterDetails();

		this.current = false;
	}
}
