/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar, Alena Brand
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
import edu.wright.cs.raiderplanner.model.Person;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Handle actions associated with the GUI window for creating new accounts.
 * This includes validating the data contained in the various text fields,
 * retrieving the validated data, and storing the submitted data to the proper
 * objects.
 *
 * @author Zilvinas Ceikauskas
 */
public class AccountController implements Initializable {
	@FXML private TextField accountNo;
	@FXML private ComboBox<String> salutation;
	@FXML private TextField fullName;
	@FXML private TextField email;
	@FXML private CheckBox famLast;
	@FXML private Button submit;
	@FXML private GridPane pane;
	@FXML private Alert invalidInputAlert = new Alert(AlertType.ERROR);
	@FXML private Alert emptyNameAlert = new Alert(AlertType.CONFIRMATION);

	private Account account;
	private boolean success = false;

	/**
	 * Getter for Account.
	 *
	 * @return the Account object being managed by this controller.
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * Getter for Success.
	 *
	 * @return true if the last submit operation succeeded, false otherwise.
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Determines if the user has entered a valid salutation by calling the
	 * validateSalutation() from the Person Class in Model, which checks that
	 * the entered Salutation only contains a combination of upper/lower case
	 * letters and returns a boolean value. Then sets the style so it is cohesive.
	 * @return true if the user entered a valid salutation.
	 */
	public boolean validateSalutation() {
		if (!Person.validSalutation(this.salutation.getSelectionModel().getSelectedItem().trim())) {
			return false;
		} else {
			this.salutation.setStyle("");
			return true;
		}
	}

	/**
	 * Determines if the user has entered a valid name by calling the validateName()
	 * from the Person Class in Model, which checks that the entered Name only
	 * contains a combination of spaces and upper/lower case letters and returns
	 * a boolean value. Then sets the style so it is cohesive.
	 * @return True if the user entered a valid name.
	 */
	public boolean validateName() {
		if (!Person.validName(this.fullName.getText().trim())) {
			return false;
		} else {
			this.fullName.setStyle("");
			return true;
		}
	}

	/**
	 * Determines if the user has entered a valid email checking if the textfield
	 * is empty and by calling the validateEmail() from the Person Class in
	 * Model, which uses the EmailValidator (Apache Commons Validator 1.6 API)
	 * to check that the email is valid and returns a boolean value. Then sets
	 * the style so it is cohesive.
	 * @return True if the user entered a valid email.
	 */
	public boolean validateEmail() {
		if (this.email.getText().trim().isEmpty()
				|| Person.validEmail(this.email.getText().trim())) {
			this.email.setStyle("");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determines if the user has entered a valid account number by checking
	 * that the length of the text is 7, that the first character is a 'w',
	 * that the next 3 characters are digits, and that the last 3
	 * characters are letters and lower case. Then sets the style so it is cohesive.
	 * @return True if the user entered a valid account number.
	 */
	public boolean validateNumber() {
		if (accountNo.getText().trim().length() == 7) {
			if (accountNo.getText().trim().charAt(0) != 'w') {
				return false;
			} else {
				for (int i = 1; i < 4; ++i) {
					if (!Character.isDigit(accountNo.getText().trim().charAt(i))) {
						return false;
					}
				}
				for (int i = 4; i < 7; ++i) {
					if (!Character.isLetter(accountNo.getText().trim().charAt(i))) {
						return false;
					} else if (!Character.isLowerCase(accountNo.getText().trim().charAt(i))) {
						return false;
					}
				}
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Handles the actions taken when the user tries to submit a new account.
	 * The appropriate warnings and errors are displayed if the user enters incorrect information.
	 * If a user enters an invalid input, they will be taken back to the page, to change fields.
	 */
	public void handleSubmit() {
		String invalidMessage = "";
		boolean validSuccess = true;
		boolean validName = true;
		if (!validateNumber()) {
			invalidMessage += "Please enter a valid W Number\n";
			validSuccess = false;
		}
		if (!validateName()) {
			invalidMessage += "Please enter a valid name\n";
			validSuccess = false;
		}
		if (!validateEmail()) {
			invalidMessage += "Please enter a valid email\n";
			validSuccess = false;
		}
		if (!validateSalutation()) {
			invalidMessage += "Please enter a valid salutation\n";
			validSuccess = false;
		}
		if (this.fullName.getText().trim().isEmpty()) {
			if (!this.handleEmptyName()) {
				validName = false;
			}
		}
		if (validSuccess && validName) {
			Person pers = new Person(this.salutation.getSelectionModel().getSelectedItem().trim(),
					this.fullName.getText().trim(), this.famLast.isSelected());
			this.account = new Account(pers, this.accountNo.getText().trim());
			this.success = true;
			Stage stage = (Stage) this.submit.getScene().getWindow();
			stage.close();
		} else if (!validSuccess) {
			invalidInputAlert.setHeaderText("Invalid Entries");
			invalidInputAlert.setContentText(invalidMessage);
			invalidInputAlert.showAndWait();
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
	 * Displays dialog and handles appropriate user choices if the full name field is empty.
	 * @return True if the user selects Okay
	 */
	public boolean handleEmptyName() {
		emptyNameAlert.setContentText("Are you sure you don't want to use your name?");
		Optional<ButtonType> result = emptyNameAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
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
