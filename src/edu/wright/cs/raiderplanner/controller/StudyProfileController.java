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

import edu.wright.cs.raiderplanner.model.StudyProfile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Žilvinas on 10/05/2017.
 */
public class StudyProfileController implements Initializable {
	private StudyProfile profile;

	// Labels:
	@FXML private Label title;
	@FXML private Label name;
	@FXML private Label details;
	@FXML private Label modules;
	@FXML private Label milestones;
	@FXML private Label extensions;

	// Buttons:
	@FXML private Button setCurrent;

	/**
	 * Set this StudyProfile as the current profile.
	 */
	public void setCurrent() {
		MainController.getSpc().getPlanner().setCurrentStudyProfile(this.profile);
		this.setCurrent.setDisable(true);
	}

	/**
	 * Close this window.
	 */
	public void handleClose() {
		Stage stage = (Stage) this.title.getScene().getWindow();
		stage.close();
	}

	/**
	 * Constructor for the StudyProfileController.
	 */
	public StudyProfileController(StudyProfile profile) {
		this.profile = profile;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.title.setText("Year " + this.profile.getYear()
			+ ", Semester " + this.profile.getSemesterNo());
		this.name.setText(this.profile.getName());
		this.details.setText(this.profile.getDetails().getAsString());
		this.details.setWrapText(true);

		this.modules.setText(this.profile.getModules().length + " module(s).");
		this.milestones.setText(this.profile.getMilestones().length + " milestone(s).");
		this.extensions.setText(this.profile.getExtensions().length + " extension application(s).");

		if (MainController.getSpc().getPlanner().getCurrentStudyProfile().equals(this.profile)) {
			this.setCurrent.setDisable(true);
		}
	}
}