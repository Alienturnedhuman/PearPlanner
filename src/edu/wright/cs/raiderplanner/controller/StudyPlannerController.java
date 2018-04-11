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

package edu.wright.cs.raiderplanner.controller;

import edu.wright.cs.raiderplanner.model.Account;
import edu.wright.cs.raiderplanner.model.Activity;
import edu.wright.cs.raiderplanner.model.Event;
import edu.wright.cs.raiderplanner.model.HubFile;
import edu.wright.cs.raiderplanner.model.Milestone;
import edu.wright.cs.raiderplanner.model.Notification;
import edu.wright.cs.raiderplanner.model.QuantityType;
import edu.wright.cs.raiderplanner.model.StudyPlanner;
import edu.wright.cs.raiderplanner.model.StudyProfile;
import edu.wright.cs.raiderplanner.model.Task;
import edu.wright.cs.raiderplanner.model.TaskType;
import edu.wright.cs.raiderplanner.view.UiManager;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;


/**
 * Created by bendickson on 5/4/17.
 */

public class StudyPlannerController {
	private StudyPlanner planner;

	/**
	 *
	 * @return SPC's StudyPlanner file, planner.
	 */
	public StudyPlanner getPlanner() {
		return planner;
	}

	/**
     * Save the current StudyPlanner into a serialized file.
     *
     * @param key64    SecretKey used for encoding.
     * @param fileName name of the file.
     * @return whether saved successfully.
     */
	public boolean save(SecretKey key64, String fileName) {
		try {
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, key64);
			SealedObject sealedObject = new SealedObject(this.planner, cipher);
			try (CipherOutputStream cipherOutputStream = new CipherOutputStream(
					new BufferedOutputStream(new FileOutputStream(fileName)), cipher);
					ObjectOutputStream outputStream = new ObjectOutputStream(cipherOutputStream)) {
				outputStream.writeObject(sealedObject);
				return true;
			}
		} catch (IOException e) {
			UiManager.reportError("File does not exist: " + e.getMessage());
			return false;
		} catch (Exception e) {
			UiManager.reportError(e.getMessage());
			return false;
		}
	}

	/**
	 * ' Checks whether a StudyProfile for this year and semester is loaded in.
	 *
	 * @param year
	 *            year to be checked
	 * @param semester
	 *            semester number to be checked
	 * @return whether this StudyProfile exists in this StudyPlanner
	 */
	public boolean containsStudyProfile(int year, int semester) {
		return planner.containsStudyProfile(year, semester);
	}

	/**
	 * if valid, this method creates a new StudyProfile and returns true if invalid, it returns
	 * false.
	 *
	 * @param hubFile
	 *            HubFile containing the newly loaded in profile
	 * @return whether created successfully.
	 */
	public boolean createStudyProfile(HubFile hubFile) {
		if (!this.planner.containsStudyProfile(hubFile.getYear(), hubFile.getSemester())) {
			// Create a profile:
			StudyProfile profile = new StudyProfile(hubFile);
			this.planner.addStudyProfile(profile);
			if (this.planner.getCurrentStudyProfile() == null) {
				this.planner.setCurrentStudyProfile(profile);
				profile.setCurrent(true);
			}
			// =================

			// Fill the global calendar with newly imported events:
			ArrayList<Event> cal = hubFile.getCalendarList();
			int i1 = -1;
			int i2 = cal.size();
			while (++i1 < i2) {
				// ConsoleIo.setConsoleMessage("Adding " + cal.get(i).toString() + " to calendar",
				// true);
				this.planner.addEventToCalendar(cal.get(i1));
				profile.addEventToCalendar(cal.get(i1));
			}
			// =================

			// Notify user:
			Notification not = new Notification("New study profile created!",
					new GregorianCalendar(), "\"" + profile.getName() + "\"", profile);
			this.planner.addNotification(not);
			// =================

			return true;
		}
		return false;
	}

	/**
	 * Returns a list of tasks in the current StudyProfile if it exists or an empty list if it.
	 * doesn't
	 */
	public ArrayList<Task> getCurrentTasks() {
		if (this.getPlanner().getCurrentStudyProfile() != null) {
			return this.getPlanner().getCurrentStudyProfile().getTasks();
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * Checker whether the user needs to be notified about something. (Deadlines etc.)
	 */
	public void checkForNotifications() {
		// TODO notifications
		/*
		 * int hours1 = 168, hours2 = 48; // temporary values until a Settings page is present
		 * for (Map.Entry<ModelEntity, boolean[]> entry :
		 * this.planner.getDeadlineNotifications().entrySet()) { if (entry.getKey() instanceof
		 * Assignment) { if (!entry.getValue()[0]) { GregorianCalendar temp = new
		 * GregorianCalendar(); temp.add(CALENDAR.HOUR, -hours1); Date date = temp.getTime();
		 * if (entry.getKey() instanceof Coursework) { if (date.after((((Coursework)
		 * entry.getKey()).getDeadline().getDate()))) { Notification not = new
		 * Notification("Assignment due in a week!", new GregorianCalendar(),
		 * entry.getKey().getName(), entry.getKey());
		 * MainController.getSPC().getPlanner().addNotification(not); entry.getValue()[0] = true; }
		 * } if (entry.getKey() instanceof Exam) { if (date.after((((Exam)
		 * entry.getKey()).getTimeSlot().getDate()))) { Notification not = new
		 * Notification("You have an exam in a week!", new GregorianCalendar(),
		 * entry.getKey().getName(), entry.getKey());
		 * MainController.getSPC().getPlanner().addNotification(not); entry.getValue()[0] = true; }
		 * } } else if (!entry.getValue()[1]) { GregorianCalendar temp = new GregorianCalendar();
		 * temp.add(CALENDAR.HOUR, -hours2); Date date = temp.getTime();
		 * if (entry.getKey() instanceof Coursework) { if (date.after((((Coursework)
		 * entry.getKey()).getDeadline().getDate()))) { Notification not = new
		 * Notification("Assignment due in a two days!", new GregorianCalendar(),
		 * entry.getKey().getName(), entry.getKey());
		 * MainController.getSPC().getPlanner().addNotification(not); entry.getValue()[1] = true; }
		 * } if (entry.getKey() instanceof Exam) { if (date.after((((Exam)
		 * entry.getKey()).getTimeSlot().getDate()))) { Notification not = new
		 * Notification("You have an exam in two days!", new GregorianCalendar(),
		 * entry.getKey().getName(), entry.getKey());
		 * MainController.getSPC().getPlanner().addNotification(not); entry.getValue()[1] = true; }
		 * } } else this.planner.getDeadlineNotifications().remove(entry); } }
		 */
	}

	/**
	 * Adds a new Activity to this StudyPlanner.
	 *
	 * @param activity
	 *            Activity to be added.
	 */
	public void addActivity(Activity activity) {
		this.planner.addActivity(activity);
	}

	/**
	 * Adds a new Milestone to this StudyPlanner.
	 *
	 * @param milestone
	 *            Milestone to be added.
	 */
	public void addMilestone(Milestone milestone) {
		this.planner.getCurrentStudyProfile().addMilestone(milestone);
	}

	/**
	 * Removes the given Milestone from this StudyPlanner.
	 *
	 * @param milestone
	 *            Milestone to be removed.
	 * @return Whether the Milestone was removed successfully.
	 */
	public boolean removeMilestone(Milestone milestone) {
		return this.planner.getCurrentStudyProfile().removeMilestone(milestone);
	}

	/**
	 * Add a new QuantityType to this StudyPlanner.
	 *
	 * @param quantity
	 *            QuantityType to be added
	 * @return whether added successfully.
	 */
	public boolean addQuantityType(QuantityType quantity) {
		if (!this.planner.getQuantityTypes().contains(quantity)) {
			this.planner.getQuantityTypes().add(quantity);
			return true;
		}
		return false;
	}

	/**
	 * Add a new TaskType to this StudyPlanner.
	 *
	 * @param taskType
	 *            TaskType to be added
	 * @return whether added successfully.
	 */
	public boolean addTaskType(TaskType taskType) {
		if (!this.planner.getTaskTypes().contains(taskType)) {
			this.planner.getTaskTypes().add(taskType);
			return true;
		}
		return false;
	}

	// Constructors:
	/**
	 * Empty Constructor.
	 */
	public StudyPlannerController() {
	}

	/**
	 * Constructor for testing UI.
	 *
	 * @param newAccount New account for planner
	 */
	public StudyPlannerController(Account newAccount) {
		planner = new StudyPlanner(newAccount);
	}

	/**
	 * Used when loading from a file.
	 *
	 * @param planner
	 *            StudyPlanner to be loaded.
	 */
	public StudyPlannerController(StudyPlanner planner) {
		this.planner = planner;

		// Process Quantity and Task types.
		if (!this.planner.getQuantityTypes().isEmpty()) {
			this.planner.getQuantityTypes().forEach(e -> QuantityType.create(e));
		}

		if (!this.planner.getTaskTypes().isEmpty()) {
			this.planner.getTaskTypes().forEach(e -> TaskType.create(e));
		}

		if (!this.planner.emptyVersionControlLibrary()) {
			this.planner.rebuildVersionControlLibrary();
		}
	}
}
