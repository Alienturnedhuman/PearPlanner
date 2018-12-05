/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar, Amila Dias
 *
 * Copyright (C) 2018 - Clayton D. Terrill, Ian Mahaffy
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
import edu.wright.cs.raiderplanner.model.Event;
import edu.wright.cs.raiderplanner.model.HubFile;
import edu.wright.cs.raiderplanner.model.ICalExport;
import edu.wright.cs.raiderplanner.model.Notification;
import edu.wright.cs.raiderplanner.model.Settings;
import edu.wright.cs.raiderplanner.model.StudyPlanner;
import edu.wright.cs.raiderplanner.util.RaiderException;
import edu.wright.cs.raiderplanner.view.UiManager;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
import java.util.GregorianCalendar;

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
	 * @throws Exception e if there is an issue registering a new account or importing a file
	 */
	public static void initialise() {
		if (settings.getAccountStartup() == true) {
			File file = new File(settings.getDefaultFilePath());
			if (file.exists() && !file.isDirectory()) {
				plannerFile = file;
			} else {
				boolean noAccount = false;
				File[] files = UiManager.getSavesFolder().listFiles();
				if (files != null) {
					if (files.length == 0) {
						noAccount = true;
					}
					if (files.length == 1
							&& files[0].getName().contains("SamplePlanner.dat")) {
						noAccount = true;
					}
				}
				if (noAccount) {
					try {
						Account newAccount = MainController.ui.createAccount(noAccount);
						StudyPlannerController study =
								new StudyPlannerController(newAccount);
						// Welcome notification:
						Notification not =
								new Notification("Welcome!", new GregorianCalendar(),
								"Thank you for using RaiderPlanner!");
						study.getPlanner().addNotification(not);
						MainController.setSpc(study);
						plannerFile = MainController.ui.savePlannerFileDialog();
						if (plannerFile != null) {
							if (plannerFile.getParentFile().exists()) {
								if (plannerFile.getParentFile().canRead()) {
									if (plannerFile.getParentFile().canWrite()) {
										MainController.save();
										loadFile(plannerFile);
									} else {
										UiManager.reportError("Directory can't be written to.");
									}
								} else {
									UiManager.reportError("Directory cannot be read from.");
								}
							} else {
								UiManager.reportError("Directory does not exist.");
							}
						}
						/*This is catching a general exception because the
						 * createAccount method throws a general exception*/
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					long modifiedTime = Long.MIN_VALUE;
					File modifiedFile = new File("");
					for (int i = 0; i < files.length; ++i) {
						if (files[i].lastModified() > modifiedTime) {
							modifiedFile = files[i];
							modifiedTime = files[i].lastModified();
						}
					}
					plannerFile = modifiedFile;
					// If a file is present:
					loadFile(plannerFile);
				}
			}
		} else {
			boolean noAccount = false;
			File[] files = MainController.ui.getSavesFolder().listFiles();
			if (files != null) {
				if (files.length == 0) {
					noAccount = true;
				}
				if (files.length == 1 && files[0].getName().contains("SamplePlanner.dat")) {
					noAccount = true;
				}
			}
			if (noAccount) {
				try {
					Account newAccount = MainController.ui.createAccount(noAccount);
					StudyPlannerController study = new StudyPlannerController(newAccount);
					// Welcome notification:
					Notification not = new Notification("Welcome!", new GregorianCalendar(),
							"Thank you for using RaiderPlanner!");
					study.getPlanner().addNotification(not);
					MainController.setSpc(study);
					plannerFile = MainController.ui.savePlannerFileDialog();
					if (plannerFile != null) {
						if (plannerFile.getParentFile().exists()) {
							if (plannerFile.getParentFile().canRead()) {
								if (plannerFile.getParentFile().canWrite()) {
									MainController.save();
									loadFile(plannerFile);
								} else {
									UiManager.reportError("Directory can not be written to.");
								}
							} else {
								UiManager.reportError("Directory cannot be read from.");
							}

						} else {
							UiManager.reportError("Directory does not exist.");
						}
					}
					/*This is catching a general exception because the
					 * createAccount method throws a general exception*/
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				long modifiedTime = Long.MIN_VALUE;
				File modifiedFile = new File("");
				for (int i = 0; i < files.length; ++i) {
					if (files[i].lastModified() > modifiedTime) {
						modifiedFile = files[i];
						modifiedTime = files[i].lastModified();
					}
				}
				plannerFile = modifiedFile;
				// If a file is present:
				loadFile(plannerFile);
			}
		}
	}

	/**
	 * Decrypts a file and loads it.
	 * @param plannerFile the file to be loaded
	 */
	public static void loadFile(File plannerFile) {
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
				UiManager.reportError("Cannot find this file.","file not found exception when "
						+ "trying loadfile, most likely due to invalid parameters");
				System.exit(1);
			} catch (ClassNotFoundException e) {
				UiManager.reportError("Error, Cannot find the study planner.", "class not found"
						+ " exception thrown when trying loadfile, most likely due to problems"
						+ " reading the object input stream as a sealed object");
				System.exit(1);
			} catch (BadPaddingException e) {
				UiManager.reportError("Error, Cannot decode the given file.", "bad padding"
						+ " exception thrown when trying loadfile, most likely due to "
						+ "problems with the cipher");
				System.exit(1);
			} catch (IOException e) {
				UiManager.reportError("Error, Invalid file.", "IO exception thrown when trying"
						+ " loadfile, most likely due to an invalide file or problems with "
						+ "one of the input streams");
				System.exit(1);
			} catch (IllegalBlockSizeException e) {
				UiManager.reportError("Error, Object too large, cannot decode the file.",
						"Illegal block size exception thrown when trying loadfile, most "
						+ "likely due to a problem with constructing a sealed object");
				System.exit(1);
			}  catch (InvalidKeyException e) {
				UiManager.reportError("Error, Invalid Key, Cannot decode the given file.",
						"invalid key exception thrown when trying loadfile, most likely due "
						+ "to using an invalid key while initialising the the cypher");
				System.exit(1);
			} catch (NoSuchAlgorithmException e) {
				UiManager.reportError("Error, Cannot decode the given file.", "no such "
						+ "algorithm exception thrown when trying loadfile, most likely "
						+ "due to the program not being able to find the indicated "
						+ "cypher while initialising the the cypher");
				System.exit(1);
			} catch (NoSuchPaddingException e) {
				UiManager.reportError("Error, Cannot decode the given file",  "no such"
						+ " padding exception thrown when trying loadfile, most likely"
						+ " due to problems with the parameters used in the get "
						+ "instance call made while initialising the the cypher");
				System.exit(1);
			}  catch (Exception e) {
				UiManager.reportError(e.getMessage() + "Unknown error.", e.getMessage()
						+ "unknown error occured while trying loadfile");
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
	 * @throws IOException e if the file doesnt exist. Any other exceptions gets error message.
	 */
	public static void main() throws Exception {
		try {
			ui.mainMenu();
		} catch (IOException e) {
			UiManager.reportError("File does not exist: " + e.getMessage());
		} catch (RaiderException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display the main menu.
	 * Stage is already present.
	 * @throws IOException e if the file doesnt exist. Any other exceptions gets error message.
	 */
	public static void showMain() throws Exception {
		try {
			ui.showMain();
		} catch (IOException e) {
			UiManager.reportError("File does not exist: " + e.getMessage());
		} catch (RaiderException e) {
			UiManager.reportError(e.getMessage());
		}
	}

	/**
	 * Display the settings menu.
	 * Stage is already present.
	 * @throws IOException e if the file doesnt exist. Any other exceptions gets error message.
	 */
	public static void showSettings() throws Exception {
		try {
			ui.showSettings();
		} catch (IOException e) {
			UiManager.reportError("File does not exist: " + e.getMessage());
		} catch (RaiderException e) {
			UiManager.reportError(e.getMessage());
		}
	}

	/**
	 * Handles importing a new file and if the file is imported it lets the user know.
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
	 * @return true for a successful save, false otherwise
	 * @throws Exception e if there was an issue with saving information
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
	 * Checks if a string can be converted to an Integer.
	 *
	 * @param value String to be tested
	 * @return True if string can be converted, false otherwise
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}
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
	public static void openHelpPage() {
		final Button site = new Button("Website");
		final Button pdf = new Button("user-manual");
		Label tab1 = new Label("RaiderPlanner is an application based off of the Pear Planner "
				+ "to help students keep"
				+ " track of assignments and exams, allowing them to achieve their full academic"
				+ " potential. "
				+ "Current features include a calendar, an alarm, and a Gantt diagram generator"
				+ " to keep track of progress");
		Label tab2 = new Label("How to use RaiderPlanner in 3 easy steps:"
				+ "\n" + "1: Enter valid information for all the fields on the startup page. "
						+ "All other information is optional." + "\n"
				+ "2: Choose the name of the file you want to save to." + "\n"
				+ "3: To unlock all the other features RaiderPlanner has to offer, click the"
				+ " import hub file button from the menu on the left"
				+  "\nNeed more help? Open up the user manual or RaiderPlanner Website here");
		Label tab3 = new Label("If you want, you can contribute to RaiderPlanner on github at"
				+ " the this address: https://github.com/rsanchez-wsu/RaiderPlanner"
				+ "\n" + "Planned features include a graduation planner, Pilot integration, and a "
				+ "schedule sharing feature");
		tab1.setWrapText(true);
		tab2.setWrapText(true);
		tab3.setWrapText(true);
		VBox splitter = new VBox();
		splitter.getChildren().add(tab2);
		splitter.getChildren().add(pdf);
		splitter.getChildren().add(site);
		TitledPane t1 = new TitledPane("What is RaiderPlanner?",tab1);
		TitledPane t2 = new TitledPane("Getting Started",splitter);
		TitledPane t3 = new TitledPane("Whats Next?",tab3);
		Accordion root = new Accordion();
		root.getPanes().addAll(t1, t2, t3);
		Stage newStage = new Stage();
		newStage.setTitle("Raider Helper");
		Scene scene = new Scene(root,400,300);
		newStage.setScene(scene);
		newStage.show();

		pdf.setOnAction((event) -> {
			if (Desktop.isDesktopSupported()) {
				try {
					File myFile = new
							File("Final Documents/"
									+ "User Manual.pdf");
					Desktop.getDesktop().open(myFile);
				} catch (IOException ex) {
					System.out.println("Error: user-manual not found");
				}
			}
		});
		site.setOnAction((event) -> {
			if (Desktop.isDesktopSupported()) {
				try {
					File myFile = new
							File("docs/index.html");
					Desktop.getDesktop().open(myFile);
				} catch (IOException ex) {
					System.out.println("Error: Website not found");
				}
			}
		});
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
