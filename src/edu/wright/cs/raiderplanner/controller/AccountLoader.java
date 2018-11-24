/*
 * Copyright (C) 2018 - Gabe Dodds, Joe Yancey
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

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handle actions associated with the GUI window for loading existing accounts.
 *
 * @author Gabe Dodds, Joe Yancey
 */
public class AccountLoader implements Initializable {
	@FXML
	private ToggleGroup myToggleGroup;
	@FXML
	private RadioButton f1;
	@FXML
	private RadioButton f2;
	@FXML
	private Button submit;
	@FXML
	private GridPane pane;
	@FXML
	private Alert invalidInputAlert = new Alert(AlertType.ERROR);

	private boolean selection = true;

	/**
	 * check the selection from the user.
	 */
	public void radioSelect() {
		if (f1.isSelected()) {
			selection = false;
		} else {
			selection = true;
		}
	}

	/**
	 * Handle Quit button.
	 */
	public void handleQuit() {
		Stage stage = (Stage) this.submit.getScene().getWindow();
		stage.close();
	}

	/**
	 * Handle submit button.
	 *
	 * @return
	 */
	public boolean handleSubmit() {
		Stage stage = (Stage) this.submit.getScene().getWindow();
		stage.close();
		return selection;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> this.pane.requestFocus());
		submit.defaultButtonProperty().bind(submit.focusedProperty());
		submit.setOnAction(e -> {
			if (submit.isFocused()) {
				handleSubmit();
			}
		});
	}
}