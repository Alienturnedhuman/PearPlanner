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

import edu.wright.cs.raiderplanner.model.Account;
import edu.wright.cs.raiderplanner.model.Person;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
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
	@FXML private TextField salutation;
	@FXML private TextField fullName;
	@FXML private TextField email;
	@FXML private CheckBox famLast;
	@FXML private Button submit;
	@FXML private GridPane pane;

	private Account account;
	private boolean success = false;

	/**
	 * @return the Account object being managed by this controller.
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @return true if the last submit operation succeeded, false otherwise.
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Handle changes to the text fields.
	 */
	public void handleChange() {
		if (Person.validSalutation(this.salutation.getText().trim())
				&& Person.validName(this.fullName.getText().trim())
				&& (this.email.getText().trim().isEmpty()
						|| Person.validEmail(this.email.getText().trim()))
				&& !accountNo.getText().trim().isEmpty()) {
			this.submit.setDisable(false);
		}
	}

	/**
	 * Validate data in the Salutation field.
	 */
	public void validateSalutation() {
		if (!Person.validSalutation(this.salutation.getText().trim())) {
			this.salutation.setStyle("-fx-text-box-border:red;");
			this.submit.setDisable(true);
		} else {
			this.salutation.setStyle("");
			this.handleChange();
		}
	}

	/**
	 * Validate data in the Name field.
	 */
	public void validateName() {
		if (!Person.validName(this.fullName.getText().trim())) {
			this.fullName.setStyle("-fx-text-box-border:red;");
			this.submit.setDisable(true);
		} else {
			this.fullName.setStyle("");
			this.handleChange();
		}
	}

	/**
	 * Validate data in the Email field.
	 */
	public void validateEmail() {
		if (this.email.getText().trim().isEmpty()
				|| Person.validEmail(this.email.getText().trim())) {
			this.email.setStyle("");
			this.handleChange();
		} else {
			this.email.setStyle("-fx-text-box-border:red;");
			this.submit.setDisable(true);
		}
	}

	/**
	 * Validate data in the Account Number field.
	 */
	public void validateNumber() {
		if (accountNo.getText().trim().isEmpty()) {
			this.submit.setDisable(true);
		} else {
			this.submit.setStyle("-fx-text-box-border:red;");
			this.handleChange();
		}
	}

	/**
	 * Submit the form and create a new Account.
	 */
	public void handleSubmit() {
		Person pers = new Person(this.salutation.getText().trim(),
				this.fullName.getText().trim(), this.famLast.isSelected());

		if (!this.email.getText().trim().isEmpty()) {
			pers.setEmail(this.email.getText().trim());
		}

		this.account = new Account(pers, this.accountNo.getText().trim());
		this.success = true;
		Stage stage = (Stage) this.submit.getScene().getWindow();
		stage.close();
	}

	/**
	 * Handle Quit button.
	 */
	public void handleQuit() {
		Stage stage = (Stage) this.submit.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> this.pane.requestFocus());
	}
}
