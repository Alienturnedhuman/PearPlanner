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

import edu.wright.cs.raiderplanner.model.Event;
import edu.wright.cs.raiderplanner.model.HubFile;
import edu.wright.cs.raiderplanner.model.Module;
import edu.wright.cs.raiderplanner.model.MultilineString;
import edu.wright.cs.raiderplanner.model.StudyProfile;
import edu.wright.cs.raiderplanner.model.VersionControlEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Žilvinas on 10/05/2017.
 */
public class StudyProfileController implements Initializable {
	private StudyProfile profile;
	private MenuController mc;

	// Labels:
	@FXML private Label title;
	@FXML private Label name;
	@FXML private Label details;
	@FXML private Label modules;
	@FXML private Label milestones;
	@FXML private Label extensions;

	// Buttons:
	@FXML private Button setCurrent;
	@FXML private Button deleteProfile;

	/**
	 * Set this StudyProfile as the current profile.
	 * @throws IOException if there is an error while loading the FXML GUI
	 */
	public void setCurrent() throws IOException {
		MainController.getSpc().getPlanner().setCurrentStudyProfile(this.profile);
		this.setCurrent.setDisable(true);
		mc.main();
		mc.loadStudyProfile(this.profile);
	}

	/**
	 * remove this StudyProfile from List.
	 */
	public void deleteProfile() {
		if (MainController.getSpc().containsStudyProfile(profile.getYear(),
				profile.getSemesterNo())) {
			if (profile.isCurrent()) {
				MainController.getSpc().getPlanner().getCurrentStudyProfile().clearProfile();
				MainController.getSpc().getPlanner().setCurrentStudyProfile(
						new StudyProfile(new HubFile(0,0,0,new ArrayList<Module>(),
						new ArrayList<VersionControlEntity>(),new ArrayList<Event>(),
						"No semester",new MultilineString("No details"),"No UId")));
			}
			MainController.getSpc().getPlanner().removeProfile(profile);
		}
		mc.main();
	}

	/**
	 * Constructor for the StudyProfileController.
	 */
	public StudyProfileController(StudyProfile profile) {
		this.profile = profile;
	}

	/**
	 * Constructor for the StudyProfileController.
	 */
	public StudyProfileController(StudyProfile profile,MenuController mc) {
		this.profile = profile;
		this.mc = mc;
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