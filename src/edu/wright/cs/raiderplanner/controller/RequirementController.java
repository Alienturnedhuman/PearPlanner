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

import edu.wright.cs.raiderplanner.model.Activity;
import edu.wright.cs.raiderplanner.model.QuantityType;
import edu.wright.cs.raiderplanner.model.Requirement;
import edu.wright.cs.raiderplanner.view.UiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Zilvinas on 13/05/2017.
 */
public class RequirementController implements Initializable {
	private Requirement requirement;
	private boolean success = false;

	/**
	 * Returns the Requirement contained by this RequirementController.
	 *
	 * @return the Requirement contained by this RequirementController.
	 */
	public Requirement getRequirement() {
		return this.requirement;
	}

	/**
	 * Returns true if this RequirementController has successfully handled a
	 * Requirement submission at any point in its existence.
	 *
	 * @return true if this RequirementController has successfully handled a
	 * 				Requirement submission at any point in its existence.
	 */
	public boolean isSuccess() {
		return success;
	}

	// Panes:
	@FXML private GridPane pane;
	@FXML private TableView<Activity> activities;
	@FXML private TableColumn<Activity, String> nameColumn;
	@FXML private TableColumn<Activity, Integer> quantityColumn;
	@FXML private TableColumn<Activity, String> dateColumn;
	@FXML private ContextMenu context;

	// Buttons:
	@FXML private Button submit;
	@FXML private Button addQuantity;
	@FXML private MenuItem quantityMenu;

	// Text:
	@FXML private TextArea details;
	@FXML private ComboBox<String> quantityType;
	@FXML private TextField name;
	@FXML private TextField quantity;
	@FXML private TextField time;
	@FXML private TextField quantityName;

	// Labels:
	@FXML private Label title;
	@FXML private Label completed;

	/**
	 * Handle changes to the input fields.
	 */
	public void handleChange() {
		// Check the input fields:
		if (!this.name.getText().trim().isEmpty()
				&& !this.quantity.getText().trim().isEmpty()
				&& !this.time.getText().trim().isEmpty()
				&& this.quantityType.getSelectionModel().getSelectedIndex() != -1) {
			this.submit.setDisable(false);
		// =================
		}
	}

	/**
	 * Validate data in the Time field.
	 */
	public void validateTime() {
		if (!MainController.isNumeric(this.time.getText())
				|| Double.parseDouble(this.time.getText()) < 0) {
			this.time.setStyle("-fx-text-box-border:red;");
			this.submit.setDisable(true);
		} else {
			this.time.setStyle("");
			this.handleChange();
		}
	}

	/**
	 * Validate data in the Quantity field.
	 */
	public void validateQuantity() {
		if (!MainController.isNumeric(this.quantity.getText())
				|| Integer.parseInt(this.quantity.getText()) < 0) {
			this.quantity.setStyle("-fx-text-box-border:red;");
			this.submit.setDisable(true);
		} else {
			this.quantity.setStyle("");
			this.handleChange();
		}
		if (this.requirement != null) {
			this.completed.setVisible(false);
		}
	}

	/**
	 * Validate data in the QuantityType field.
	 */
	public void validateNewQuantity() {
		if (!this.quantityName.getText().trim().isEmpty()) {
			this.quantityMenu.setDisable(false);
		} else {
			this.quantityMenu.setDisable(true);
		}
	}

	/**
	 * Add a new QuantityType.
	 */
	public void newQuantity() {
		if (UiManager.confirm("Create a new Quantity '" + this.quantityName.getText() + '?')) {
			// Create a new type:
			QuantityType qtyType = QuantityType.create(this.quantityName.getText());
			// =================

			// Update the current list:
			this.quantityType.getItems().clear();
			this.quantityType.getItems().addAll(QuantityType.listOfNames());
			this.quantityType.getSelectionModel().select(qtyType.getName());
			// =================
		}
		this.quantityName.clear();
		this.quantityMenu.setDisable(true);
	}

	/**
	 * Submit the form and create a new Task.
	 */
	public void handleSubmit() {
		if (this.requirement == null) {
			// Create a new Requirement:
			this.requirement = new Requirement(this.name.getText(), this.details.getText(),
					Double.parseDouble(this.time.getText()),
					Integer.parseInt(this.quantity.getText()),
					this.quantityType.getValue());
			// =================
		} else {
			// Update the current requirement:
			this.requirement.setName(this.name.getText());
			this.requirement.setDetails(this.details.getText());
			this.requirement.setEstimatedTimeInHours(Double.parseDouble(this.time.getText()));
			this.requirement.setInitialQuantity(Integer.parseInt(this.quantity.getText()));
			this.requirement.setQuantityType(this.quantityType.getValue());
			// =================

		}
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

	/**
	 * Constructor for the RequirementController.
	 */
	public RequirementController() {
	}

	/**
	 * Constructor for a RequirementController with an existing Requirement.
	 *
	 * @param requirement the Requirement to manage
	 */
	public RequirementController(Requirement requirement) {
		this.requirement = requirement;
	}

	@Override public void initialize(URL location, ResourceBundle resources) {
		this.quantityType.getItems().addAll(QuantityType.listOfNames());

		// Row actions:
		this.activities.setRowFactory(e -> {
			TableRow<Activity> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton()
						== MouseButton.PRIMARY && event.getClickCount() == 2) {
					try {
						MainController.ui.activityDetails(row.getItem());
						this.activities.refresh();
					} catch (IOException e1) {
						UiManager.reportError("Unable to open view file");
					}
				}
			});
			return row;
		});
		// =================

		// Quantity actions:
		this.addQuantity.setOnMousePressed(event -> {
			if (event.isPrimaryButtonDown()) {
				context.show(addQuantity, event.getScreenX(), event.getScreenY());
			}
		});
		// =================

		// Hide the Activities table:
		if (this.requirement == null) {
			this.pane.getChildren().remove(this.activities);
			this.pane.getRowConstraints().remove(3);

			Node bottomNode = this.pane.getChildren().get(0);
			this.pane.getChildren().remove(bottomNode);
			this.pane.getRowConstraints().remove(3);

			GridPane.setColumnSpan(bottomNode, 2);
			this.pane.addRow(3, bottomNode);
			// =================
		} else {
			// Disable/modify elements:
			this.title.setText("Requirement");

			if (this.requirement.isComplete()) {
				this.completed.setVisible(true);
			}

			// Activity columns:
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			quantityColumn.setCellValueFactory(new PropertyValueFactory<>("activityQuantity"));
			dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateString"));
			// =================

			// Fill in data:
			this.name.setText(this.requirement.getName());
			this.details.setText(this.requirement.getDetails().getAsString());
			this.time.setText(Double.toString(this.requirement.getEstimatedTimeInHours()));
			this.quantity.setText(Integer.toString(this.requirement.getInitialQuantity()));
			this.quantityType.getSelectionModel().select(
					this.requirement.getQuantityType().getName());
			this.activities.getItems().addAll(this.requirement.getActivityLog());
			// =================
		}
		// =================

		Platform.runLater(() -> this.pane.requestFocus());
	}
}
