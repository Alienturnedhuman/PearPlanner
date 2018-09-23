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

import edu.wright.cs.raiderplanner.controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * RaidersPlanner's main driver class.
 * This class launches and instantiates all necessary objects for this project to run successfully.
 * @author Benjamin Dickson
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		// Initialize the StudyPlanner:
		MainController.initialise();
		// If initialization passed, call the Main menu:
		MainController.main();
		// UiManager.areYouFeelingLucky();
	}

	/**
	 * Lunch is the raiderplanner application.
	 * @param args String.
	 */
	public static void main(String[] args) {
		launch(args);
		// Upon exit, save the StudyPlanner:
		MainController.save();
	}
}