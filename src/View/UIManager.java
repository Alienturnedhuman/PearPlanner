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

package View;

import Controller.AccountController;
import Controller.ActivityController;
import Controller.MenuController;
import Controller.MilestoneController;
import Controller.RequirementController;
import Controller.StartupController;
import Controller.StudyProfileController;
import Controller.TaskController;
import Model.Account;
import Model.Activity;
import Model.Assignment;
import Model.Milestone;
import Model.ModelEntity;
import Model.Module;
import Model.Requirement;
import Model.StudyProfile;
import Model.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.net.URL;

/**
 * Created by Zilvinas on 04/05/2017.
 */

public class UIManager {
	private static Stage mainStage = new Stage();
	private static MenuController mc = new MenuController();
	private static File savesFolder = new File("./saves");
	private static Image icon = new Image("file:icon.png");
	private static FileChooser.ExtensionFilter datExtension =
			new FileChooser.ExtensionFilter("dat file", "*.dat");
	private static FileChooser.ExtensionFilter xmlExtension =
			new FileChooser.ExtensionFilter("XML file", "*.xml");
	private static FileChooser.ExtensionFilter pngExtension =
			new FileChooser.ExtensionFilter("PNG file", "*.png");
	private URL activityFxml = getClass().getResource("/View/Activity.fxml");
	private URL milestoneFxml = getClass().getResource("/View/Milestone.fxml");
	private URL taskFxml = getClass().getResource("/View/Task.fxml");
	private URL requirementFxml = getClass().getResource("/View/Requirement.fxml");
	private URL createAccountFxml = getClass().getResource("/View/CreateAccount.fxml");
	private URL mainMenuFxml = getClass().getResource("/View/MainMenu.fxml");
	private URL studyProfileFxml = getClass().getResource("/View/StudyProfile.fxml");
	private URL startupFxml = getClass().getResource("/View/Startup.fxml");

	/**
	 * Displays a 'Create Account' window and handles the creation of a new Account object.
	 *
	 * @return newly created Account
	 * @throws Exception
	 *             if user quits
	 */
	public Account createAccount() throws Exception {
		AccountController accountControl = new AccountController();
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Hello World!");
		// Load in the .fxml file:
		FXMLLoader loader = new FXMLLoader(createAccountFxml);
		loader.setController(accountControl);
		Parent root = loader.load();
		// Set the scene:
		Stage stage = new Stage();
		stage.setScene(new Scene(root, 550, 232));
		stage.setTitle("Create Account");
		stage.resizableProperty().setValue(false);
		stage.getIcons().add(icon);
		stage.showAndWait();
		// Handle creation of the Account object:
		if (accountControl.isSuccess()) {
			Account newAccount = accountControl.getAccount();
			return newAccount;
		} else {
			throw new Exception("User quit.");
		}
	}

	/**
	 * Displays the main menu.
	 *
	 * @throws Exception when the FXMLLoader is unable to load
	 */
	public void mainMenu() throws Exception {
		// Load in the .fxml file:
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Hello World!");
		FXMLLoader loader = new FXMLLoader(mainMenuFxml);
		loader.setController(UIManager.mc);
		Parent root = loader.load();

		// Set the scene:
		mainStage.setScene(new Scene(root, 1000, 750, true, SceneAntialiasing.BALANCED));
		mainStage.setTitle("RaiderPlanner");

		// Set minimum resolution to around 360p in 3:5 aspect ratio for small phones
		mainStage.setMinHeight(555);
		mainStage.setMinWidth(333);
		mainStage.getIcons().add(icon);
		mainStage.showAndWait();
	}

	/**
	 * Display the 'Add Activity' window.
	 *
	 * @return newly created Activity
	 */
	public Activity addActivity() throws Exception {
		ActivityController ac = new ActivityController();
		// Load in the .fxml file:
		FXMLLoader loader = new FXMLLoader(activityFxml);
		loader.setController(ac);
		Parent root = loader.load();
		// Set the scene:
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(root, 550, 358));
		stage.setTitle("New Activity");
		stage.resizableProperty().setValue(false);
		stage.getIcons().add(icon);
		stage.showAndWait();
		// Add the Activity to the StudyPlanner
		if (ac.isSuccess()) {
			return ac.getActivity();
		}
		return null;
	}

	/**
	 * Displays the Activity details page
	 *
	 * @param activity for which the details should be displayed.
	 */
	public void activityDetails(Activity activity) throws IOException {
		ActivityController ac = new ActivityController(activity);
		// Load in the .fxml file:
		FXMLLoader loader = new FXMLLoader(activityFxml);
		loader.setController(ac);
		Parent root = loader.load();
		// Set the scene:
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(root, 550, 358));
		stage.setTitle("Activity");
		stage.resizableProperty().setValue(false);
		stage.getIcons().add(icon);
		stage.showAndWait();
	}

	/**
	 * Displays the 'Add Milestone' window.
	 *
	 * @return newly created Milestone object.
	 */
	public Milestone addMilestone() throws IOException {
		MilestoneController mc = new MilestoneController();
		// Load in the .fxml file:
		FXMLLoader loader = new FXMLLoader(milestoneFxml);
		loader.setController(mc);
		Parent root = loader.load();
		// Set the scene:
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(root, 550, 355));
		stage.setTitle("Milestone");
		stage.resizableProperty().setValue(false);
		stage.getIcons().add(icon);
		stage.showAndWait();
		// Add the Milestone to the StudyPlanner
		if (mc.isSuccess()) {
			return mc.getMilestone();
		}
		return null;
	}

	/**
	 * Displays the Milestone details page
	 *
	 * @param milestone for which the details should be shown.
	 */
	public void milestoneDetails(Milestone milestone) throws IOException {
		MilestoneController mc = new MilestoneController(milestone);
		// Load in the .fxml file:
		FXMLLoader loader = new FXMLLoader(milestoneFxml);
		loader.setController(mc);
		Parent root = loader.load();
		// Set the scene:
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(root, 550, 355));
		stage.setTitle("Milestone");
		stage.resizableProperty().setValue(false);
		stage.getIcons().add(icon);
		stage.showAndWait();
	}

	/**
	 * Displays the StudyProfile details page
	 *
	 * @param profile StudyProfile for which the details should be shown.
	 */
	public void studyProfileDetails(StudyProfile profile) throws IOException {
		StudyProfileController spc = new StudyProfileController(profile);
		// Load in the .fxml file:
		FXMLLoader loader = new FXMLLoader(studyProfileFxml);
		loader.setController(spc);
		Parent root = loader.load();
		// Set the scene:
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(root, 550, 232));
		stage.setTitle(profile.getName());
		stage.resizableProperty().setValue(false);
		stage.getIcons().add(icon);
		stage.showAndWait();
	}

	/**
	 * Displays the Module details page
	 *
	 * @param module for which the details should be shown.
	 * @param current Window from which this method is called.
	 * @throws IOException exception if IO error is triggered
	 */
	public void moduleDetails(Module module, MenuController.Window current) throws IOException {
		UIManager.mc.loadModule(module, current, null);
	}

	/**
	 * Displays the Module details page
	 *
	 * @param module for which the details should be shown.
	 * @param current Window from which this method is called.
	 * @throws IOException if IO error is triggered
	 */
	public void moduleDetails(Module module, ModelEntity current) throws IOException {
		UIManager.mc.loadModule(module, MenuController.Window.EMPTY, current);
	}

	/**
	 * Displays the Assignment details page
	 *
	 * @param assignment for which the details should be shown.
	 * @param current Window from which this method is called.
	 * @throws IOException if IO error is triggered
	 */
	public void assignmentDetails(Assignment assignment, MenuController.Window current)
			throws IOException {
		UIManager.mc.loadAssignment(assignment, current, null);
	}

	/**
	 * Displays the Assignment details page
	 *
	 * @param assignment for which the details should be shown.
	 * @param current Window from which this method is called.
	 * @throws IOException if IO error is triggered
	 */
	public void assignmentDetails(Assignment assignment, ModelEntity current) throws IOException {
		UIManager.mc.loadAssignment(assignment, MenuController.Window.EMPTY, current);
	}

	/**
	 * Creates a window for adding a new Task.
	 *
	 * @return newly created Task
	 */
	public Task addTask() throws Exception {
		TaskController tc = new TaskController();
		// Load in the .fxml file:
		FXMLLoader loader = new FXMLLoader(taskFxml);
		loader.setController(tc);
		Parent root = loader.load();
		// Set the scene:
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(root, 550, 558));
		stage.setTitle("New Task");
		stage.resizableProperty().setValue(false);
		stage.getIcons().add(icon);
		stage.showAndWait();
		// Handle creation of the Account object:
		if (tc.isSuccess()) {
			return tc.getTask();
		}
		return null;
	}

	/**
	 * Displays the Task details page
	 *
	 * @param task for which the details should be displayed.
	 * @throws IOException if IO error is triggered from loading .fxml
	 */
	public void taskDetails(Task task) throws IOException {
		TaskController tc = new TaskController(task);
		// Load in the .fxml file:
		FXMLLoader loader = new FXMLLoader(taskFxml);
		loader.setController(tc);
		Parent root = loader.load();
		// Set the scene:
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(root, 550, 558));
		stage.setTitle("Task");
		stage.resizableProperty().setValue(false);
		stage.getIcons().add(icon);
		stage.showAndWait();
	}

	/**
	 * Creates a window for adding a new Requirement.
	 *
	 * @return newly created Requirement
	 */
	public Requirement addRequirement() throws Exception {
		RequirementController rc = new RequirementController();
		// Load in the .fxml file:
		FXMLLoader loader = new FXMLLoader(requirementFxml);
		loader.setController(rc);
		Parent root = loader.load();
		// Set the scene:
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(root, 550, 260));
		stage.setTitle("New Requirement");
		stage.resizableProperty().setValue(false);
		stage.getIcons().add(icon);
		stage.showAndWait();
		// Handle creation of the Account object:
		if (rc.isSuccess()) {
			return rc.getRequirement();
		}
		return null;
	}

	/**
	 * Displays the Requirement details page.
	 *
	 * @param requirement for which the details should be displayed
	 */
	public void requirementDetails(Requirement requirement) throws IOException {
		RequirementController rc = new RequirementController(requirement);
		// Load in the .fxml file:
		FXMLLoader loader = new FXMLLoader(requirementFxml);
		loader.setController(rc);
		Parent root = loader.load();
		// Set the scene:
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(root, 550, 558));
		stage.setTitle("Requirement");
		stage.resizableProperty().setValue(false);
		stage.getIcons().add(icon);
		stage.showAndWait();
	}

	/**
	 * Display startup window.
	 */
	public void showStartup() throws IOException {
		StartupController sc = new StartupController();
		// Load in the .fxml file:
		FXMLLoader loader = new FXMLLoader(startupFxml);
		loader.setController(sc);
		Parent root = loader.load();
		// Set the scene:
		Stage stage = new Stage();
		stage.setScene(new Scene(root, 400, 500));
		stage.setTitle("RaiderPlanner");
		stage.resizableProperty().setValue(false);
		stage.getIcons().add(icon);
		// Replaces red x click with a System.exit(0);
		stage.setOnCloseRequest(event -> {
			event.consume();
			System.exit(0);
		});
		stage.showAndWait();
	}

	/**
	 * Displays a file dialog for importing .xml files
	 *
	 * @return a File object
	 */
	public File loadFileDialog() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a HUB file");
		fileChooser.getExtensionFilters().add(xmlExtension);
		fileChooser.setInitialDirectory(new File("StudyProfiles"));
		File file = fileChooser.showOpenDialog(mainStage);
		return file;
	}

	/**
	 * Displays a file dialog for saving a .png file
	 *
	 * @return String path
	 */
	public String saveFileDialog(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Ganttish Diagram");
		fileChooser.getExtensionFilters().add(pngExtension);
		File path = fileChooser.showSaveDialog(stage);
		return path.getAbsolutePath();
	}

	/**
	 * Displays a file dialog for importing .dat planner files
	 *
	 * @return a File object
	 */
	public File loadPlannerFileDialog() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a planner to load");
		fileChooser.getExtensionFilters().add(datExtension);
		if (!savesFolder.exists()) {
			savesFolder.mkdirs();
		}
		fileChooser.setInitialDirectory(savesFolder);
		File file = fileChooser.showOpenDialog(mainStage);
		return file;
	}

	/**
	 * Displays a file dialog for saving .dat planner files
	 *
	 * @return a File object
	 */
	public File savePlannerFileDialog() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save your planner file");
		fileChooser.getExtensionFilters().add(datExtension);
		if (!savesFolder.exists()) {
			savesFolder.mkdirs();
		}
		fileChooser.setInitialDirectory(savesFolder);
		File file = fileChooser.showSaveDialog(mainStage);
		return file;
	}

	/**
	 * reporting errors without logging.
	 * @param displayMessage message to display to user
	 */
	public static void reportError(String displayMessage) {
		displayError(displayMessage);
	}

	/**
	 * Error reporting with stack trace.
	 * @param displayMessage message to display to user.
	 * @param stackTrace StackTrace from thrown exception
	 */
	public static void reportError(String displayMessage,StackTraceElement[] stackTrace) {
		reportError(displayMessage,Arrays.toString(stackTrace));
	}

	/**
	 * Error reporting with string.
	 * @param displayMessage message to be displayed to user
	 * @param errorMessage error to log, can be string or stack trace
	 */
	public static void reportError(String displayMessage,String errorMessage) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("errorlog.txt", true));) {
			//Time stamp code from
			//https://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java
			String timeStamp = new SimpleDateFormat(
					"yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			bw.write(timeStamp + " " +  displayMessage + " " + errorMessage);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		displayError(displayMessage);
	}

	/**
	 * Displays an error to the user.
	 * @param message to be displayed to user
	 */
	public static void displayError(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR, message);
		alert.showAndWait();
	}

	/**
	 * Reports that an action was successful and displays a message.
	 *
	 * @param message to be displayed
	 */
	public static void reportSuccess(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
		alert.showAndWait();
	}

	/**
	 * Reports that an action was successful.
	 */
	public static void reportSuccess() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION, "Success!");
		alert.showAndWait();
	}

	/**
	 * Confirm box with 'Yes' or 'No' as available options
	 *
	 * @param message to be displayed
	 * @return true for yes, false for no.
	 */
	public static boolean confirm(String message) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES,
				ButtonType.NO);
		alert.showAndWait();
		return alert.getResult().equals(ButtonType.YES);
	}

	/**
	 * Please don't use.
	 */
	public static void areYouFeelingLucky() {
		while (UIManager.confirm("Are you feeling lucky?") == (Math.random() < 0.5)) {
		}
	}

}
