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

import edu.wright.cs.raiderplanner.model.Milestone;
import edu.wright.cs.raiderplanner.model.Task;
import edu.wright.cs.raiderplanner.view.UiManager;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * Created by Žilvinas on 14/05/2017.
 */
public class MilestoneController implements Initializable {
	private Milestone milestone;
	private boolean success = false;
	/**
	 * Standard getter method for milestone.
	 * @return milestone
	 */

	public Milestone getMilestone() {
		return this.milestone;
	}

	/**
	 * Getter for checking if milestone controller initialized successful.
	 * @return boolean bases on initialized success
	 */
	public boolean isSuccess() {
		return success;
	}

	// Panes:
	@FXML private GridPane pane;

	// Buttons:
	@FXML private Button submit;
	@FXML private Button add;
	@FXML private Button remove;

	// Text:
	@FXML private TextArea details;
	@FXML private DatePicker deadline;
	@FXML private TextField name;

	// Labels:
	@FXML private Label title;
	@FXML private Label completed;

	// Lists:
	@FXML private ListView<Task> tasks;

	/**
	 * Handle changes to the input fields.
	 */
	public void handleChange() {
		// Check the input fields:
		if (!this.name.getText().trim().isEmpty()
				&&
				!this.deadline.getEditor().getText().trim().isEmpty()
				&&
				this.tasks.getItems().size() > 0) {
			this.submit.setDisable(false);
		}
		// =================

		// Process tasks:
		if (this.milestone != null) {
			this.milestone.replaceTasks(this.tasks.getItems());

			if (!this.milestone.isComplete()) {
				this.completed.setVisible(false);
			} else {
				this.completed.setVisible(true);
			}
		}
		// =================
	}

	/**
	 * Validate data in the Deadline field.
	 */
	public void validateDeadline() {
		if (this.deadline.getValue().isBefore(LocalDate.now())) {
			this.deadline.setStyle("-fx-border-color:red;");
			this.submit.setDisable(true);
		} else {
			this.deadline.setStyle("");
			this.handleChange();
		}
	}

	/**
	 * Handle the 'Add Task' button action.
	 */
	public void addTask() {
		// Table items:
		ObservableList<Task> list =
				FXCollections.observableArrayList(MainController.getSpc().getCurrentTasks());
		list.removeAll(this.tasks.getItems());
		if (this.milestone != null) {
			list.removeAll(this.milestone.getTasks());
		}
		// =================

		// Parse selected Tasks:
		this.tasks.getItems().addAll(TaskController.taskSelectionWindow(list));
		// =================
	}

	/**
	 * Submit the form and create a new Milestone.
	 */
	public void handleSubmit() {
		if (this.milestone == null) {
			// Create a new Milestone:
			this.milestone =
					new Milestone(
							this.name.getText(), this.details.getText(), this.deadline.getValue());
			this.milestone.addTasks(this.tasks.getItems());
			// =================
		} else {
			// Update the current Milestone:
			this.milestone.setName(this.name.getText());
			this.milestone.setDetails(this.details.getText());
			this.milestone.setDeadline(this.deadline.getValue());
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

	// Constructors:

	/**
	 * Constructor for the MilestoneController.
	 */
	public MilestoneController() {
	}

	/**
	 * Constructor for an MilestoneController with an existing Milestone.
	 *
	 * @param milestone constructs milestone from milestone
	 */
	public MilestoneController(Milestone milestone) {
		this.milestone = milestone;
	}

	@Override public void initialize(URL location, ResourceBundle resources) {
		// Bind properties on buttons:
		this.remove.disableProperty().bind(new BooleanBinding() {
			{
				bind(tasks.getSelectionModel().getSelectedItems());
			}

			@Override
			protected boolean computeValue() {
				return !(tasks.getItems().size() > 0
						&& tasks.getSelectionModel().getSelectedItem() != null);
			}
		});
		// =================

		// Button actions:
		this.remove.setOnAction(e -> {
			if (UiManager.confirm("Are you sure you want to remove this dependency?")) {
				Task tempTask = this.tasks.getSelectionModel().getSelectedItem();
				this.tasks.getItems().remove(tempTask);
				if (this.milestone != null) {
					this.milestone.removeTask(tempTask);
				}
			}
		});

		this.tasks.setCellFactory(e -> {
			ListCell<Task> cell = new ListCell<Task>() {
				@Override
				protected void updateItem(final Task item, final boolean empty) {
					super.updateItem(item, empty);
					// If completed, mark:
					if (!empty && item != null) {
						setText(item.toString());
						if (item.isCheckedComplete()) {
							this.getStyleClass().add("current-item");
						}
					} else {
						setText(null);
						this.getStyleClass().remove("current-item");
					}
				}
			};
			return cell;
		});
		// =================

		// Handle Milestone details:
		if (this.milestone != null) {
			// Disable/modify elements:
			this.title.setText("Milestone");

			if (this.milestone.isComplete()) {
				this.completed.setVisible(true);
			}
			// =================

			// Fill in data:
			this.name.setText(this.milestone.getName());
			this.details.setText(this.milestone.getDetails().getAsString());
			this.deadline.setValue(
					this.milestone.getDeadlineDate().toInstant().atZone(ZoneId.systemDefault()
							).toLocalDate());
			this.tasks.getItems().addAll(this.milestone.getTasks());
			// =================
		}
		// =================

		// ListChangeListener:
		this.tasks.getItems().addListener((ListChangeListener<Task>) c -> handleChange());
		// =================

		Platform.runLater(() -> this.pane.requestFocus());
	}
}
