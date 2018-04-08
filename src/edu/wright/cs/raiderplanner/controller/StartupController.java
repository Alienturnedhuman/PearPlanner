/*
 * Copyright (C) 2017 - Eric Levine
 *
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
import edu.wright.cs.raiderplanner.model.Notification;
import edu.wright.cs.raiderplanner.view.UiManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.util.GregorianCalendar;

/**
 * Controller for the first window that is presented when the application
 * launches (i.e., New/Open/Exit buttons).
 *
 * @author Eric Levine on 9/26/2017.
 */
public class StartupController {

	@FXML
	private Button newFileButton;

	@FXML
	private Button openFileButton;

	@FXML
	private Button exitButton;

	@FXML
	private ImageView banner;

	/**
	 * Handles Close button event. Shouldn't need to save as you only see this
	 * prompt before a file is loaded or started.
	 */
	@FXML
	void exitB(ActionEvent event) {
		System.exit(0);
	}

	/**
	 * Handles newFileButton's action event. Creates new profile and file.
	 */
	@FXML
	void newFileB(ActionEvent event) {
		// Generates the basic planner information
		File plannerFile = null;
		try {
			Account newAccount = MainController.ui.createAccount();
			StudyPlannerController study = new StudyPlannerController(newAccount);
			// Welcome notification:
			Notification not = new Notification("Welcome!", new GregorianCalendar(),
					"Thank you for using RaiderPlanner!");
			study.getPlanner().addNotification(not);
			MainController.setSpc(study);
			plannerFile = MainController.ui.savePlannerFileDialog();
		} catch (Exception e) {
			// Apparently createAccount() throws a general exception when the user hits quit
			// I know this isn't great coding practice, but if this fails the file should remain
			// null and all should be fine. I just want this to return to the UI without breaking.
		}

		// Attempts to save planner file
		if (plannerFile != null && plannerFile.getParentFile().exists()) {
			if (plannerFile.getParentFile().canRead()) {
				if (plannerFile.getParentFile().canWrite()) {
					MainController.setPlannerFile(plannerFile);
					MainController.save();
					Stage stage = (Stage) this.openFileButton.getScene().getWindow();
					stage.close();
				} else {
					UiManager.reportError("Directory cannot be written to.");
				}
			} else {
				UiManager.reportError("Directory cannot be read from.");
			}
		}
	}

	/**
	 * Handles openFileButton's action event. Opens an existing file.
	 */
	@FXML
	void openFileB(ActionEvent event) {
		// Opens an open file dialog.
		File plannerFile = MainController.ui.loadPlannerFileDialog();

		// Checks existence and permissions before setting the MainController file path.
		if (plannerFile != null && plannerFile.exists()) {
			if (plannerFile.canRead()) {
				if (plannerFile.canWrite()) {
					MainController.setPlannerFile(plannerFile);
					Stage stage = (Stage) this.openFileButton.getScene().getWindow();
					stage.close();
				} else {
					UiManager.reportError("Cannot write to file.");
				}
			} else {
				UiManager.reportError("Cannot read file.");
			}
		} else {
			UiManager.reportError("File does not exist.");
		}

	}

}
