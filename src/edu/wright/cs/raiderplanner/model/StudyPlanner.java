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

import edu.wright.cs.raiderplanner.view.UiManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public class StudyPlanner implements Serializable {
	// private data
	private static final long serialVersionUID = 101L;

	private int version = -1;
	private Account account;
	private ArrayList<QuantityType> quantityTypes = new ArrayList<>();
	private ArrayList<TaskType> taskTypes = new ArrayList<>();
	private ArrayList<StudyProfile> studyProfiles = new ArrayList<>();
	private ArrayList<Activity> activityList = new ArrayList<>();
	private ArrayList<Event> calendar = new ArrayList<>();
	private ArrayList<Notification> notifications = new ArrayList<>();
	private HashMap<ModelEntity, boolean[]> deadlineNotifications = new HashMap<>();
	private ArrayList<VersionControlEntity> versionControlLibrary = new ArrayList<>();

	private StudyProfile currentStudyProfile;

	// public methods

	// getters

	public ArrayList<Event> getCalendar() {
		return calendar;
	}

	/**
	 * @return a String array of studyProfile names.
	 */
	public String[] getListOfStudyProfileNames() {
		int num = -1;
		String[] studyProfileSize = new String[studyProfiles.size()];
		while (++num < studyProfiles.size()) {
			studyProfileSize[num] = studyProfiles.get(num).getName();
		}
		return studyProfileSize;
	}

	/**
	 * @return an array of study profiles.
	 */
	public StudyProfile[] getStudyProfiles() {
		StudyProfile[] sp = new StudyProfile[this.studyProfiles.size()];
		sp = this.studyProfiles.toArray(sp);
		return sp;
	}

	/**
	 * This was added at the last minute before releasing and should be in the Module class, however
	 * if we had done that our Java Serialized file, which took 3 hours prepare,
	 * would no longer have worked.This is temporary solution so you can still use
	 * our prepared Study Planner save file, but in the next iteration
	 * it will be moved to the correct place and would not consist of 4 nested loops.
	 * Also, for public release, we would create a file format for saving in so
	 * Java Serialization issues would not
	 * occur for the end user.
	 *
	 * @param module : given value is a Module type
	 * @return.
	 */
	public int getTimeSpent(Module module) {
		int time = 0;
		for (Assignment assignment : module.getAssignments()) {
			for (Task task : assignment.getTasks()) {
				for (Requirement requirement : task.getRequirements()) {
					for (Activity activity : requirement.getActivityLog()) {
						time += activity.getDuration();
					}
				}
			}
		}
		return time;
	}

	/**
	 * Check whether this StudyPlanner contains a StudyProfile with the given parameters.
	 *
	 * @param sYear year
	 * @param sSem  semester number
	 * @return whether this StudyProfile exists
	 */
	public boolean containsStudyProfile(int syear, int ssem) {
		int num = -1;
		int ii = studyProfiles.size();
		while (++num < ii) {
			if (studyProfiles.get(num).matches(syear, ssem)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Add a given event to the global calendar.
	 *
	 * @param event Event to be added to the calendar.
	 */
	public void addEventToCalendar(Event event) {
		if (!calendar.contains(event)) {
			calendar.add(event);
		}
	}

	/**
	 * Returns the current StudyProfile.
	 *
	 * @return current StudyProfile
	 */
	public StudyProfile getCurrentStudyProfile() {
		return this.currentStudyProfile;
	}

	/**
	 * Get the preferred name of the user using this StudyPlanner.
	 *
	 * @return String containing the users name.
	 */
	public String getUserName() {
		return this.account.getStudentDetails().getPreferredName();
	}

	/**
	 * Get all notifications in this StudyPlanner.
	 *
	 * @return an array of notifications.
	 */
	public Notification[] getNotifications() {
		Notification[] notification = new Notification[this.notifications.size()];
		notification = this.notifications.toArray(notification);
		return notification;
	}

	/**
	 * Get all unread notifications in this StudyPlanner.
	 *
	 * @return an array of unread notifications.
	 */
	public Notification[] getUnreadNotifications() {
		Notification[] notification = this.notifications.stream().filter(e ->
		!e.isRead()).toArray(Notification[]::new);
		return notification;
	}

	/**
	 * Returns a HashMap that contains information about Deadline notifications.
	 *
	 * @return.
	 */
	public HashMap<ModelEntity, boolean[]> getDeadlineNotifications() {
		return deadlineNotifications;
	}

	/**
	 * Returns an ArrayList of QuantityTypes.
	 * @return : returns a list of QuantityType
	 */
	public ArrayList<QuantityType> getQuantityTypes() {
		return this.quantityTypes;
	}

	/**
	 * Returns an ArrayList of TaskTypes.
	 *
	 * @return returns a list of TaskType
	 */
	public ArrayList<TaskType> getTaskTypes() {
		return this.taskTypes;
	}

	// setters

	/**
	 * Change the current Study Profile to a given one.
	 *
	 * @param profile a StudyProfile to be marked as current.
	 * @return whether changed successfully.
	 */
	public boolean setCurrentStudyProfile(StudyProfile profile) {
		if (this.studyProfiles.contains(profile)) {
			if (this.currentStudyProfile != null) {
				this.currentStudyProfile.setCurrent(false);
			}
			this.currentStudyProfile = profile;
			profile.setCurrent(true);
			return true;
		}
		return false;
	}

	/**
	 * Change the current Study Profile to a Study Profile with the given ID.
	 * @param profileId : ID of a Study Profile.
	 * @return whether changed successfully.
	 */
	public boolean setCurrentStudyProfile(String profileId) {
		this.studyProfiles.forEach(e -> {
			if (e.getUid().equals(profileId)) {
				this.setCurrentStudyProfile(e);
			}
		});

		return this.currentStudyProfile.getUid().equals(profileId);
	}

	/**
	 * Adds a new StudyProfile to the StudyPlanner.
	 *
	 * @param profile StudyProfile to be added.
	 */
	public void addStudyProfile(StudyProfile profile) {
		this.studyProfiles.add(profile);
	}

	/**
	 * Add a new notification to this StudyPlanner.
	 *
	 * @param notification Notification to be added.
	 */
	public void addNotification(Notification notification) {
		this.notifications.add(notification);
	}

	/**
	 * Add an Activity to this Study Planner and update appropriate fields.
	 *
	 * @param activity Activity to be added.
	 */
	public void addActivity(Activity activity) {
		this.activityList.add(activity);
		ArrayList<Assignment> assignments = new ArrayList<>();
		// Loop through all Tasks:
		for (Task t : activity.getTasks()) {
			// Distribute Activity Quantity to available Requirements of a Task:
			int quantity = activity.getActivityQuantity();
			for (Requirement r : t.getRequirements()) {
				if (r.getQuantityType().equals(activity.getType()) && !r.checkedCompleted) {
					quantity -= r.getRemainingQuantity();
					Activity extracted = new Activity(activity);

					if (quantity > 0) {
						extracted.setActivityQuantity(r.getRemainingQuantity());
						r.addActivity(extracted);
					} else {
						extracted.setActivityQuantity(quantity + r.getRemainingQuantity());
						r.addActivity(extracted);
						break;
					}
				}
			}
			// =================
			for (Assignment assignment : t.getAssignmentReferences()) {
				if (!assignments.contains(assignment)) {
					assignments.add(assignment);
				}
			}
		}
		// =================

		// Distribute quantity to Assignment requirements:
		for (Assignment a : assignments) {
			int quantity = activity.getActivityQuantity();
			for (Requirement r : a.getRequirements()) {
				if (r.getQuantityType().equals(activity.getType()) && !r.checkedCompleted) {
					quantity -= r.getRemainingQuantity();
					Activity extracted = new Activity(activity);

					if (quantity > 0) {
						extracted.setActivityQuantity(r.getRemainingQuantity());
						r.addActivity(extracted);
					} else {
						extracted.setActivityQuantity(quantity + r.getRemainingQuantity());
						r.addActivity(extracted);
						break;
					}
				}
			}
		}
		// =================
	}

	/**
	 * Add a VersionControlEntity to the library.
	 *
	 * @param vce VersionControlEntity to be added.
	 * @return whether added successfully.
	 */
	public boolean addToVersionControlLibrary(VersionControlEntity vce) {
		if (versionControlLibrary.contains(vce)) {
			return false;
		} else {
			versionControlLibrary.add(vce);
			return true;
		}
	}

	/**
	 * Check whether the current VCE library is empty.
	 *
	 * @return true if empty, false otherwise.
	 */
	public boolean emptyVersionControlLibrary() {
		return versionControlLibrary.isEmpty();
	}

	/**
	 * Rebuild the VCE library (used when the app is reloaded).
	 */
	public void rebuildVersionControlLibrary() {
		versionControlLibrary.forEach(e -> e.reload());
	}

	/**
	 * Update the version of this StudyPlanner.
	 *
	 * @param newVersion new version number
	 * @return whether updated successfully.
	 */
	public boolean setVersion(int newVersion) {
		if (newVersion > version) {
			version = newVersion;
			return true;
		} else {
			return false;
		}
	}

	public int getVersion() {
		return version;
	}

	// constructors

	public StudyPlanner(Account newAccount) {
		this.account = newAccount;
		try {
			// Add Default Quantity types:
			Collections.addAll(this.quantityTypes, QuantityType.listOfQuantityTypes());
			// Add Default Task types:
			Collections.addAll(this.taskTypes, TaskType.listOfTaskTypes());
		} catch (UnsupportedOperationException e) {
			UiManager.reportError("Error, Unsupported Operation Exception.");
			System.exit(1);
		} catch (NullPointerException e) {
			UiManager.reportError("Error, Null Pointer Exception.");
			System.exit(1);
		} catch (IllegalArgumentException e) {
			UiManager.reportError("Error, Illegal Argument Exception.");
			System.exit(1);
		}
	}
}
