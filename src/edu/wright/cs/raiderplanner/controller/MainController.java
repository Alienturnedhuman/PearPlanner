/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar, Amila Dias
 *
 * Copyright (C) 2018 - Clayton D. Terrill
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

import edu.wright.cs.raiderplanner.model.Event;
import edu.wright.cs.raiderplanner.model.HubFile;
import edu.wright.cs.raiderplanner.model.ICalExport;
import edu.wright.cs.raiderplanner.model.Settings;
import edu.wright.cs.raiderplanner.model.StudyPlanner;
import edu.wright.cs.raiderplanner.view.UiManager;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * A helper class of static methods and fields which are used to handle the
 * loading and saving of application state data.
 *
 * @author Ben Dickson
 */
public class MainController {

	/**
	 * Private constructor to prevent object instantiation.
	 */
	private MainController() {
	}

	// TODO - Determine if this really should be public
	public static UiManager ui = new UiManager();
	public static Settings settings = new Settings();

	// TODO - StudyPlannerController is a public class; determine if managing an
	// instance in this way is best
	private static StudyPlannerController spc;

	// TODO - Use an approach where a key is generated and stored on the user's system
	// Used for serialization:
	private static SecretKey key64 = new SecretKeySpec(
			new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 }, "Blowfish");
	private static File plannerFile = null;

	/**
	 * Returns the StudyPlannerController managed by this MainController.
	 *
	 * @return the StudyPlannerController managed by this MainController.
	 */
	public static StudyPlannerController getSpc() {
		return spc;
	}

	/**
	 * Sets the StudyPlannerController managed by this MainController.
	 *
	 * @param newSpc the new StudyPlannerController.
	 */
	public static void setSpc(StudyPlannerController newSpc) {
		spc = newSpc;
	}

	/**
	 * Initializes the Study Planner by either registering a new account or
	 * importing an existing Study Planner file.
	 */
	public static void initialise() {
		try {
			if (settings.getAccountStartup() == true) {
				try {
					File file = new File(settings.getDefaultFilePath());
					if (file.exists() && !file.isDirectory()) {
						plannerFile = file;
					} else {
						ui.showStartup();
					}
				} catch (FileNotFoundException e) {
					UiManager.reportError("Error, File does not exist.");
					ui.showStartup();
				} catch (IOException e) {
					UiManager.reportError("Error, Invalid file.");
					ui.showStartup();
				}
			} else {
				ui.showStartup();
			}
		} catch (IOException e) {
			UiManager.reportError("Invalid file.");
			System.exit(1);
		}
		// If a file is present:
		if (plannerFile.exists()) {
			try {
				Cipher cipher = Cipher.getInstance("Blowfish");
				cipher.init(Cipher.DECRYPT_MODE, key64);
				try (CipherInputStream cis = new CipherInputStream(
						new BufferedInputStream(new FileInputStream(plannerFile)), cipher);
						ObjectInputStream ois = new ObjectInputStream(cis)) {
					SealedObject sealedObject = (SealedObject) ois.readObject();
					spc = new StudyPlannerController((StudyPlanner) sealedObject.getObject(cipher));
					// Sample note
					if (spc.getPlanner().getCurrentStudyProfile() != null && spc.getPlanner()
							.getCurrentStudyProfile().getName().equals("First year Gryffindor")) {
						UiManager.reportSuccess(
								"Note: This is a pre-loaded sample StudyPlanner, as used by Harry "
								+ "Potter. To make your own StudyPlanner, restart the application "
								+ "and choose \"New File\".");
					}
				}
			} catch (FileNotFoundException e) {
				UiManager.reportError("Error, File does not exist.");
				System.exit(1);
			} catch (ClassNotFoundException e) {
				UiManager.reportError("Error, Class NotFoundException.");
				System.exit(1);
			} catch (BadPaddingException e) {
				UiManager.reportError("Error, Invalid file, Bad Padding Exception.");
				System.exit(1);
			} catch (IOException e) {
				UiManager.reportError("Error, Invalid file.");
				System.exit(1);
			} catch (IllegalBlockSizeException e) {
				UiManager.reportError("Error, Invalid file, Illegal Block Size Exception.");
				System.exit(1);
			}  catch (InvalidKeyException e) {
				UiManager.reportError("Error, Invalid Key, Cannot decode the given file.");
				System.exit(1);
			} catch (NoSuchAlgorithmException e) {
				UiManager.reportError("Error, Cannot decode the given file.");
				System.exit(1);
			} catch (NoSuchPaddingException e) {
				UiManager.reportError("Error, Invalid file, No Such Padding.");
				System.exit(1);
			}  catch (Exception e) {
				UiManager.reportError(e.getMessage() + "Unknown error.");
				System.exit(1);
			}
		} else {
			// TODO - fix this, as it is clearly a race condition
			// This should never happen unless a file changes permissions
			// or existence in the milliseconds that it runs the above code
			// after checks in StartupController
			UiManager.reportError("Failed to load file.");
			System.exit(1);
		}
	}

	/**
	 * Display the main menu.
	 * Stage will be created.
	 */
	public static void main() {
		try {
			ui.mainMenu();
		} catch (IOException e) {
			UiManager.reportError("File does not exist: " + e.getMessage());
		} catch (Exception e) {
			UiManager.reportError(e.getMessage());
		}
	}

	/**
	 * Display the main menu.
	 * Stage is already present.
	 */
	public static void showMain() {
		try {
			ui.showMain();
		} catch (IOException e) {
			UiManager.reportError("File does not exist: " + e.getMessage());
		} catch (Exception e) {
			UiManager.reportError(e.getMessage());
		}
	}

	/**
	 * Display the settings menu.
	 * Stage is already present.
	 */
	public static void showSettings() {
		try {
			ui.showSettings();
		} catch (IOException e) {
			UiManager.reportError("File does not exist: " + e.getMessage());
		} catch (Exception e) {
			UiManager.reportError(e.getMessage());
		}
	}

	/**
	 * Handles importing a new file.
	 *
	 * @return whether imported successfully.
	 */
	public static boolean importFile() {
		// Call a dialog:
		File tempFile = ui.loadFileDialog();
		if (tempFile != null) {
			// If a file was selected, process the file:
			HubFile fileData = DataController.loadHubFile(tempFile);
			if (fileData != null) {
				if (!fileData.isUpdate()
						&& !MainController.spc.createStudyProfile(fileData)) {
					UiManager.reportError("This Study Profile is already created!");
				} else {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Save the current state of the program to file.
	 *
	 * @return true for a successful save, false otherwise
	 */
	public static boolean save() {
		try {
			spc.save(MainController.key64, MainController.plannerFile.getAbsolutePath());
			return true;
		} catch (Exception e) { // the save() method already catches an Exception. TODO maybe delete
			UiManager.reportError("FAILED TO SAVE YOUR DATA!");
			return false;
		}
	}

	/**
	 * Sets the planner file that is loaded/saved.
	 *
	 * @param file the File object from which the planner file will be loaded or
	 * 				to which it will be saved.
	 */
	public static void setPlannerFile(File file) {
		plannerFile = file;
	}

	/**
	 * Apparently (according to Stackoverflow) the Java Standard library doesn't have a
	 * standard check for testing if a string value is a number or not?!)
	 *
	 * <p>Therefore, we are using this proposed isNumeric method from:
	 *
	 * <p>http://stackoverflow.com/a/1102916
	 *
	 * @param str String to be tested
	 * @return true the given String is numeric (i.e., can be parsed into a
	 * 				Double), false otherwise.
	 */
	public static boolean isNumeric(String str) {
		try {
			// No need to assign the result; the exception or lack of is what matters
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * Launches the default browser to display a URI.
	 */
	public static void openBrowser() {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI("https://rsanchez-wsu.github.io/RaiderPlanner/"));
			} catch (IOException e) {
				UiManager.reportError("Default browser not found or failed to launch");
			} catch (URISyntaxException e) {
				UiManager.reportError("Invaild URI syntax");
			}
		}
	}

	/**
	 * Function exports calendar ICS file to user defined location.
	 */
	public static void exportCalendar() {
		ICalExport icalExport = new ICalExport();
		try {
			ArrayList<Event> exportCal =
					getSpc().getPlanner().getCurrentStudyProfile().getCalendar();
			for (Event e : exportCal) {
				icalExport.createExportEvent(e);
			}
			icalExport.exportToFile(ui.saveIcsFileDialog());
			UiManager.reportSuccess("File Exported");
		} catch (NullPointerException e) {
			UiManager.reportError("Calendar does not exist! Export failed");
		}
	}
}
