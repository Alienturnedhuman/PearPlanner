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
import edu.wright.cs.raiderplanner.controller.MenuController.Window;

import java.util.ArrayList;

/**
 * PearPlanner/RaiderPlanner Created by Team BRONZE on 4/27/17.
 */
public class TaskType extends ModelEntity {
	private static ArrayList<TaskType> taskDatabase = new ArrayList<>();

	/**
	 * @return Returns a array of names of the tasks in the taskDatabase ArrayList.
	 */
	public static String[] listOfNames() {
		String[] str = new String[taskDatabase.size()];
		int i1 = -1;
		int i2 = taskDatabase.size();
		while (++i1 < i2) {
			str[i1] = taskDatabase.get(i1).getName();
		}
		return str;
	}

	/**
	 * @return Returns a array of task types of the tasks in the taskDatabase ArrayList.
	 */
	public static TaskType[] listOfTaskTypes() {
		TaskType[] taskTypes = new TaskType[taskDatabase.size()];
		int i1 = -1;
		int i2 = taskDatabase.size();
		while (++i1 < i2) {
			taskTypes[i1] = taskDatabase.get(i1);
		}
		return taskTypes;
	}

	/**
	 * @param tt Name of the task you want the type of.
	 * @return Returns a TaskType if it finds a task of that name, or DEFAULT otherwise.
	 */
	public static TaskType get(String tt) {
		int i1 = -1;
		int i2 = taskDatabase.size();
		while (++i1 < i2) {
			if (taskDatabase.get(i1).equals(tt)) {
				return taskDatabase.get(i1);
			}
		}
		return DEFAULT;
	}

	/**
	 * @param tt TaskType being checked.
	 * @return true if the TaskType exists in taskDatabase.
	 */
	public static boolean exists(TaskType tt) {
		int i1 = -1;
		int i2 = taskDatabase.size();
		while (++i1 < i2) {
			if (taskDatabase.get(i1).equals(tt)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param tt string name of TaskType being checked.
	 * @return true if the TaskType with name tt exists in taskDatabase.
	 */
	public static boolean exists(String tt) {
		int i1 = -1;
		int i2 = taskDatabase.size();
		while (++i1 < i2) {
			if (taskDatabase.get(i1).equals(tt)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Create a new TaskType.
	 *
	 * @param name
	 *            Name of the TaskType.
	 * @param details
	 *            Details of the TaskType.
	 * @return Returns new TaskType.
	 */
	public static TaskType create(String name, String details) {
		TaskType tt = new TaskType(name, details);
		if (MainController.getSpc() != null) {
			MainController.getSpc().addTaskType(tt);
		}
		return tt;
	}

	/**
	 * Create a new TaskType.
	 * @param name
	 *            Name of the TaskType.
	 * @return Returns created TaskType.
	 */
	public static TaskType create(String name) {
		TaskType tt = new TaskType(name);
		if (MainController.getSpc() != null) {
			MainController.getSpc().addTaskType(tt);
		}
		return tt;
	}

	/**
	 * Create a new TaskType from an existing one.
	 * @param type
	 *            TaskType object
	 */
	public static void create(TaskType type) {
		if (!TaskType.taskDatabase.contains(type)) {
			TaskType.taskDatabase.add(type);
			if (MainController.getSpc() != null) {
				MainController.getSpc().addTaskType(type);
			}
		}
	}

	// this is a temporary way to populate the array until we later replace from reading a set up
	// file
	static {
		/**
		 * PearPlanner/RaiderPlanner Created by Team BRONZE on 4/27/17.
		 */
		class Pair {
			public String str1;
			public String str2;

			/**
			 * Pair Constructor.
			 * @param name Name of TaskType.
			 * @param details Details of TaskType.
			 */
			Pair(String name, String details) {
				str1 = name;
				str2 = details;
			}
		}

		Pair[] staticTypes = { new Pair("Other", "Other type of task"),
				new Pair("Reading", "Read some required text"),
				new Pair("Exercises", "Did some assigned exercises"),
				new Pair("Listening", "Listened to a podcast"),
				new Pair("Coursework", "Worked towards coursework"),
				new Pair("Revision", "Revised towards exam"),
				new Pair("Meeting", "Meet with other course members")

		};
		int i1 = -1;
		int i2 = staticTypes.length;
		while (++i1 < i2) {
			TaskType tt = new TaskType(staticTypes[i1].str1, staticTypes[i1].str2);
		}
	}

	/**
	 * Constructor for TaskType.
	 * @param name Name of TaskType
	 */
	private TaskType(String name) {
		super(name);
		if (!exists(this)) {
			taskDatabase.add(this);
		}
	}

	/**
	 * Constructor for TaskType.
	 * @param name Name of TaskType.
	 * @param details Details of TaskType.
	 */
	private TaskType(String name, String details) {
		super(name, details);
		if (!exists(this)) {
			taskDatabase.add(this);
		}
	}

	@Override
	public boolean equals(Object obj) {
		TaskType that = (TaskType) obj;
		if (getName() != null && that.getName() != null) {
			return getName().equals(that.getName());
		} else {
			return false;
		}
	}

	/**
	 * Equals method that takes a String instead of an object. Only checks against name
	 * @param name Name of TaskType to check against.
	 * @return Returns true if names are equal.
	 */
	public boolean equals(String name) {
		return getName().equals(name);
	}

	/**
	 * Only used to Override has code.
	 */
	@Override
	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do
	}



	public static TaskType DEFAULT = taskDatabase.get(0);

	/* (non-Javadoc)
	 * @see edu.wright.cs.raiderplanner.model.ModelEntity#open
	 * (edu.wright.cs.raiderplanner.controller.MenuController.Window)
	 */
	@Override
	public void open(Window current) {
		// TODO Auto-generated method stub
	}
}
