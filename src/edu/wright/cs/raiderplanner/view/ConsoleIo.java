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

package edu.wright.cs.raiderplanner.view;

import edu.wright.cs.raiderplanner.controller.DataController;
import edu.wright.cs.raiderplanner.controller.StudyPlannerController;
import edu.wright.cs.raiderplanner.model.HubFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by bendickson on 5/4/17.
 */
public class ConsoleIo {
	static ArrayList<String> logged = new ArrayList<>();

	/**
	 * Retrieve user input.
	 *
	 * @param message Message to be shown
	 * @return retrieved String
	 */
	public static String getDataString(String message) {
		try (Scanner scan = new Scanner(System.in)) {
			String str = "";
			while ("".equals(str)) {
				System.out.println(message);
				str = scan.nextLine();
			}
			return str;
		}
	}

	/**
	 * Get yes/no input.
	 *
	 * @param message Message to be shown.
	 * @return true for yes, false for no.
	 */
	public static boolean getDataBool(String message) {
		try (Scanner scan = new Scanner(System.in)) {
			String str = "";
			while (!("y".equals(str) || "n".equals(str))) {
				System.out.println(message);
				str = scan.next();
			}
			return "y".equals(str);
		}
	}

	/**
	 * How many lines have been written to the log.
	 *
	 * @return number of lines.
	 */
	public static int getLogSize() {
		return logged.size();
	}

	/**
	 * Save the full log to a file.
	 *
	 * @param filePath file path
	 */
	public static void saveLog(String filePath) {
		saveLog(filePath, 0, logged.size());
	}

	/**
	 * Save specific lines to a file.
	 *
	 * @param filePath file path
	 * @param startLine starting line
	 * @param endLine end line
	 */
	public static void saveLog(String filePath, int startLine, int endLine) {
		if (startLine < 0) {
			startLine = 0;
		}
		if (endLine > logged.size()) {
			endLine = logged.size();
		}
		int itr = startLine - 1;
		File logFile = new File(filePath);
		try (FileOutputStream fos = new FileOutputStream(logFile);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));) {

			while (++itr < endLine) {
				if (itr != startLine) {
					bw.newLine();
				}
				bw.write(logged.get(itr));
			}
		} catch (IOException e) {
			setConsoleMessage("File not written", true);
		}
	}

	/**
	 * Display a message on a console.
	 *
	 * @param message message to be displayed.
	 */
	public static void setConsoleMessage(String message) {
		System.out.println(message);
	}

	/**
	 * Display a message with an option of saving it to a log.
	 *
	 * @param message message to be displayed (or logged)
	 * @param logMessage whether to log it
	 */
	public static void setConsoleMessage(String message, boolean logMessage) {
		System.out.println(message);
		if (logMessage) {
			logged.add(message);
		}
	}

	/**
	 * Get user selection of a menu option.
	 *
	 * @param menuOptions available options
	 * @return user selection
	 */
	public static int getMenuOption(String[] menuOptions) {
		try (Scanner scan = new Scanner(System.in)) {
			int option = -1;
			while (option < 0 || option >= menuOptions.length) {
				System.out.println("Please select an option\n");
				option = -1;
				while (++option < menuOptions.length) {
					System.out.printf("%d - " + menuOptions[option] + "\n", option);
				}
				option = scan.nextInt();
			}
			return option;
		}
	}

	// Console view below
	/**
	 * Displays main menu.
	 *
	 * @return menu choice
	 */
	public static String view_main() {
		setConsoleMessage("MAIN MENU");
		// list of options
		String[] menuOptions = {
				"Create Study Profile",
				"View Study Profile",
				"View Notifications",
				"Quit Program"
		};
		int choice = getMenuOption(menuOptions);

		return menuOptions[choice];

	}

	/**
	 * Displays Study Profile creation.
	 *
	 * @return Returns menu choice
	 */
	public static String view_createStudyP() {
		setConsoleMessage("CREATE A STUDY PROFILE");
		// list of options
		String[] menuOptions = {
				"Load Study Profile File",
				"Return to Main Menu"
		};
		int choice = getMenuOption(menuOptions);

		return menuOptions[choice];

	}

	/**
	 * Shows a study profile.
	 *
	 * @param spController input StudyPlannerController
	 * @return menu option
	 */
	public static String view_viewStudyP(StudyPlannerController spController) {
		setConsoleMessage("VIEW A STUDY PROFILE");
		String[] studyProfiles = spController.getPlanner().getListOfStudyProfileNames();
		int i1 = -1;
		int i2 = studyProfiles.length;

		if (i2 < 1) {
			setConsoleMessage("No existing study profiles");
		}

		String[] menuOptions = new String[i2 + 1];
		while (++i1 < i2) {
			menuOptions[i1] = studyProfiles[i1];
		}

		menuOptions[i2] = "Return to Main Menu";

		int i3 = -1;
		while (i3 < i2) {
			i3 = getMenuOption(menuOptions);
		}

		return menuOptions[i2];
	}

	/**
	 * Loads a Study Profile.
	 *
	 * @param spController input StudyPlannerController
	 * @return a static string
	 */
	public static String view_loadStudyP(StudyPlannerController spController) {
		setConsoleMessage("LOAD A STUDY PROFILE");

		String filename = getDataString("Enter filepath:");
		File tempFile = new File(filename);
		HubFile fileData = DataController.loadHubFile(tempFile);
		while (!filename.equals("") && fileData == null) {
			filename = getDataString("File not valid, enter a different filepath:");
			tempFile = new File(filename);
			fileData = DataController.loadHubFile(tempFile);
		}

		System.out.println(fileData.toString(true));

		return "Return to Main Menu";
	}
}
