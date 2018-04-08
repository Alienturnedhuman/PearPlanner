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

import edu.wright.cs.raiderplanner.model.Assignment;
import edu.wright.cs.raiderplanner.model.Requirement;
import edu.wright.cs.raiderplanner.model.Task;
import edu.wright.cs.raiderplanner.model.TaskType;
import edu.wright.cs.raiderplanner.view.UiManager;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * Created by Zilvinas on 12/05/2017.
 */
public class TaskController implements Initializable {
	public static DataFormat format = new DataFormat("object/Requirement");

	private Task task;
	private boolean success = false;

	/**
	 * @return The Task associated with this TaskController.
	 */
	public Task getTask() {
		return this.task;
	}

	/**
	 * @return Whether this TaskController has successfully handled a Task
	 * 				submission.
	 */
	public boolean isSuccess() {
		return success;
	}

	// Buttons:
	@FXML private Button submit;
	@FXML private Button addReq;
	@FXML private Button addDep;
	@FXML private Button removeReq;
	@FXML private Button removeDep;
	@FXML private ToggleButton markComplete;
	@FXML private Button addTaskType;
	@FXML private MenuItem taskTypeMenu;

	// Panes:
	@FXML private GridPane pane;
	@FXML private ContextMenu context;

	// Text:
	@FXML private TextArea details;
	@FXML private ComboBox<String> taskType;
	@FXML private DatePicker deadline;
	@FXML private TextField name;
	@FXML private TextField weighting;
	@FXML private TextField taskTypeName;

	// Labels:
	@FXML private Label title;
	@FXML private Label completed;
	@FXML private Label canComplete;

	// Lists:
	@FXML private ListView<Requirement> requirements;
	@FXML private ListView<Task> dependencies;

	/**
	 * Handle changes to the input fields.
	 */
	public void handleChange() {
		// Check the input fields:
		if (!this.name.getText().trim().isEmpty()
				&& !this.weighting.getText().trim().isEmpty()
				&& !this.deadline.getEditor().getText().trim().isEmpty()
				&& !this.deadline.getValue().isBefore(LocalDate.now())
				&& this.taskType.getSelectionModel().getSelectedIndex() != -1) {
			this.submit.setDisable(false);
		// =================
		}

		// Process requirements and dependencies:
		if (this.task != null) {
			this.task.replaceDependencies(this.dependencies.getItems());
			this.task.replaceRequirements(this.requirements.getItems());

			if (!this.task.isCheckedComplete() && this.task.canCheckComplete()) {
				this.canComplete.setText("Can be completed.");
				this.canComplete.setTextFill(Paint.valueOf("green"));
				this.canComplete.setVisible(true);
				this.markComplete.setDisable(false);
			} else if (!this.task.canCheckComplete()) {
				this.task.setComplete(false);
				this.canComplete.setText("Cannot be completed at this point.");
				this.canComplete.setTextFill(Paint.valueOf("red"));
				this.canComplete.setVisible(true);
				this.markComplete.setDisable(true);
			}
		}
		// =================
	}

	/**
	 * Validate data in the Weighting field.
	 */
	public void validateWeighting() {
		if (!MainController.isNumeric(this.weighting.getText())
				|| Integer.parseInt(this.weighting.getText()) > 100
				|| Integer.parseInt(this.weighting.getText()) < 0) {
			this.weighting.setStyle("-fx-text-box-border:red;");
			this.submit.setDisable(true);
		} else {
			this.weighting.setStyle("");
			this.handleChange();
		}
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
	 * Handle the 'Add requirement' button action.
	 */
	public void addRequirement() {
		try {
			Requirement req = MainController.ui.addRequirement();
			if (req != null) {
				this.requirements.getItems().add(req);
			}
		} catch (IOException e1) {
			UiManager.reportError("Unable to open view file");
		} catch (Exception e1) {
			UiManager.reportError(e1.getMessage());
		}
	}

	/**
	 * Handle the 'Add dependency' button action.
	 */
	public void addDependency() {
		// Table items:
		ObservableList<Task> list = FXCollections.observableArrayList(MainController
				.getSpc().getCurrentTasks());
		list.removeAll(this.dependencies.getItems());
		if (this.task != null) {
			list.remove(this.task);
			list.removeAll(this.task.getDependencies());
		}
		// =================

		// Parse selected Tasks:
		this.dependencies.getItems().addAll(TaskController.taskSelectionWindow(list));
		// =================
	}

	/**
	 * Handles the 'Mark as complete' button action.
	 */
	public void toggleComplete() {
		if (this.task.isCheckedComplete()) {
			this.task.toggleComplete();
			this.completed.setVisible(false);
			this.canComplete.setVisible(true);
		} else if (this.task.canCheckComplete()) {
			this.task.toggleComplete();
			this.completed.setVisible(true);
			this.canComplete.setVisible(false);
		}
	}

	/**
	 * Validate data in the TaskType field.
	 */
	public void validateNewTaskType() {
		if (!this.taskTypeName.getText().trim().isEmpty()) {
			this.taskTypeMenu.setDisable(false);
		} else {
			this.taskTypeMenu.setDisable(true);
		}
	}

	/**
	 * Add a new TaskType.
	 */
	public void newTaskType() {
		if (UiManager.confirm("Create a new Task type '" + this.taskTypeName.getText() + '?')) {
			// Create a new type:
			TaskType task = TaskType.create(this.taskTypeName.getText());
			// =================

			// Update the current list:
			this.taskType.getItems().clear();
			this.taskType.getItems().addAll(TaskType.listOfNames());
			this.taskType.getSelectionModel().select(task.getName());
			// =================
		}
		this.taskTypeName.clear();
		this.taskTypeMenu.setDisable(true);
	}

	/**
	 * Submit the form and create a new Task.
	 */
	public void handleSubmit() {
		if (this.task == null) {
			// Create a new Task:
			this.task = new Task(this.name.getText(), this.details.getText(),
					this.deadline.getValue(),
					Integer.parseInt(this.weighting.getText()), this.taskType.getValue());

			for (Requirement req : this.requirements.getItems()) {
				this.task.addRequirement(req);
			}

			for (Task t : this.dependencies.getItems()) {
				this.task.addDependency(t);
			}
			// =================
		} else {
			// Update the current task:
			this.task.setName(this.name.getText());
			this.task.setDetails(this.details.getText());
			this.task.setDeadline(this.deadline.getValue());
			this.task.setWeighting(Integer.parseInt(this.weighting.getText()));
			this.task.setType(this.taskType.getValue());
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
	 * Constructor for the TaskController.
	 */
	public TaskController() {
	}

	/**
	 * Constructor for a TaskController with an existing Task.
	 *
	 * @param task : given value is a Task type
	 */
	public TaskController(Task task) {
		this.task = task;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.taskType.getItems().addAll(TaskType.listOfNames());

		// Bind properties on buttons:
		this.removeDep.disableProperty().bind(new BooleanBinding() {
			{
				bind(dependencies.getSelectionModel().getSelectedItems());
			}

			@Override
			protected boolean computeValue() {
				return !(dependencies.getItems().size() > 0
						&& dependencies.getSelectionModel().getSelectedItem() != null);
			}
		});

		this.removeReq.disableProperty().bind(new BooleanBinding() {
			{
				bind(requirements.getSelectionModel().getSelectedItems());
			}

			@Override
			protected boolean computeValue() {
				return !(requirements.getItems().size() > 0
						&& requirements.getSelectionModel().getSelectedItem() != null);
			}
		});
		// =================

		// Button actions:
		this.removeDep.setOnAction(e -> {
			if (UiManager.confirm("Are you sure you want to remove this dependency?")) {
				Task task = this.dependencies.getSelectionModel().getSelectedItem();
				this.dependencies.getItems().remove(task);
				if (this.task != null) {
					this.task.removeDependency(task);
				}
			}
		});

		this.removeReq.setOnAction(e -> {
			if (UiManager.confirm("Are you sure you want to remove this requirement?")) {
				Requirement requirement = this.requirements.getSelectionModel().getSelectedItem();
				this.requirements.getItems().remove(requirement);
				if (this.task != null) {
					this.task.removeRequirement(requirement);
				}
			}
		});

		this.requirements.setCellFactory(this::requirementCellFactory);
		// =================

		// TaskType actions:
		this.addTaskType.setOnMousePressed(event -> {
			if (event.isPrimaryButtonDown()) {
				context.show(addTaskType, event.getScreenX(), event.getScreenY());
			}
		});
		// =================

		// Handle Task details:
		if (this.task != null) {
			// Disable/modify elements:
			this.title.setText("Task");
			this.markComplete.setVisible(true);
			this.canComplete.setTextFill(Paint.valueOf("green"));

			if (this.task.isCheckedComplete()) {
				this.completed.setVisible(true);
				this.markComplete.setSelected(true);
				this.markComplete.setDisable(false);
			} else if (this.task.canCheckComplete()) {
				this.markComplete.setDisable(false);
				this.canComplete.setVisible(true);
			} else {
				this.canComplete.setText("Cannot be completed at this point.");
				this.canComplete.setTextFill(Paint.valueOf("red"));
				this.canComplete.setVisible(true);
			}
			// =================

			// Fill in data:
			this.name.setText(this.task.getName());
			this.details.setText(this.task.getDetails().getAsString());
			this.weighting.setText(Integer.toString(this.task.getWeighting()));
			this.taskType.getSelectionModel().select(this.task.getType().getName());
			this.deadline.setValue(this.task.getDeadlineDate().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate());
			this.dependencies.getItems().addAll(this.task.getDependencies());
			this.requirements.getItems().addAll(this.task.getRequirements());
			// =================
		}
		// =================

		// ListChangeListener:
		this.dependencies.getItems().addListener((ListChangeListener<Task>) c -> handleChange());
		this.requirements.getItems().addListener((ListChangeListener<Requirement>)
				c -> handleChange());
		// =================

		Platform.runLater(() -> this.pane.requestFocus());
	}

	/**
	 * RowFactory for a TableRow of Task.
	 *
	 * @param etableView TableView that contains the TableRow.
	 * @return new TableRow
	 */
	protected static TableRow<Task> taskRowFactory(TableView<Task> etableView) {
		TableRow<Task> row = new TableRow<Task>() {
			@Override
			protected void updateItem(final Task item, final boolean empty) {
				super.updateItem(item, empty);
				// If Task is completed, mark:
				if (!empty && item != null && item.isCheckedComplete()) {
					this.getStyleClass().add("current-item");
				}
				// TODO something wrong with TableRow styles
			}
		};
		row.setOnMouseClicked(event -> {
			if (!row.isEmpty() && event.getButton()
					== MouseButton.PRIMARY && event.getClickCount() == 2) {
				Stage current = (Stage) row.getScene().getWindow();
				current.close();
			}
		});
		return row;
	}

	/**
	 * Creates a Task selection window.
	 *
	 * @param list List of Tasks to be put into the window.
	 * @return A list of selected Tasks
	 */
	protected static ObservableList<Task> taskSelectionWindow(ObservableList<Task> list) {
		// Layout:
		VBox layout = new VBox();
		layout.setSpacing(10);
		layout.setAlignment(Pos.BOTTOM_RIGHT);
		// =================

		// Tasks columns:
		TableColumn<Task, String> nameColumn = new TableColumn<>("Task");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Task, String> assignmentColumn = new TableColumn<>("Assignments");
		assignmentColumn.setCellValueFactory(new PropertyValueFactory("assignments") {
			@Override public ObservableValue call(TableColumn.CellDataFeatures param) {
				SimpleStringProperty value = new SimpleStringProperty("");
				for (Assignment a : ((Task) param.getValue()).getAssignmentReferences()) {
					value.set(value.getValue() + a.getName() + "\n");
				}
				return value;
			}
		});

		TableColumn<Task, String> deadlineColumn = new TableColumn<>("Deadline");
		deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
		deadlineColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		// =================

		// Create a table:
		TableView<Task> tasks = new TableView<>();
		tasks.setItems(list);
		tasks.getColumns().addAll(nameColumn, assignmentColumn, deadlineColumn);
		tasks.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// =================

		// Table attributes:
		tasks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		GridPane.setHgrow(tasks, Priority.ALWAYS);
		GridPane.setVgrow(tasks, Priority.ALWAYS);
		// =================

		// Set click event:
		tasks.setRowFactory(TaskController::taskRowFactory);
		// =================

		// Button:
		Button ok = new Button("OK");
		ok.setOnAction(e -> {
			Stage current = (Stage) ok.getScene().getWindow();
			current.close();
		});
		VBox.setMargin(ok, new Insets(5));
		ok.setDefaultButton(true);
		// =================

		layout.getChildren().addAll(tasks, ok);

		// Set a new scene:
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(layout, 550, 300));
		stage.setTitle("Select dependencies");
		stage.getIcons().add(new Image("file:icon.png"));
		stage.showAndWait();
		// =================

		return tasks.getSelectionModel().getSelectedItems();
	}

	/**
	 * CellFactory for a ListView of Requirement.
	 *
	 * @param elistView ListView that contains the ListCell.
	 * @return new ListCell
	 */
	protected ListCell<Requirement> requirementCellFactory(ListView<Requirement> elistView) {
		ListCell<Requirement> cell = new ListCell<Requirement>() {
			@Override
			protected void updateItem(final Requirement item, final boolean empty) {
				super.updateItem(item, empty);
				// If completed, mark:
				if (!empty && item != null) {
					setText(item.toString());
					if (item.isComplete()) {
						this.getStyleClass().add("current-item");
					}
				} else {
					setText(null);
					this.getStyleClass().remove("current-item");
				}
			}
		};

		cell.setOnMouseClicked(event -> {
			if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY
					&& event.getClickCount() == 2) {
				try {
					MainController.ui.requirementDetails(cell.getItem());
					elistView.refresh();
				} catch (IOException e1) {
					UiManager.reportError("Unable to open view file");
				}
			}
		});

		cell.setOnDragDetected(event -> {

			if (cell.getItem() == null) {
				return;
			}
			Dragboard dragboard = cell.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.put(TaskController.format, cell.getItem());
			dragboard.setContent(content);
			event.consume();
		});

		cell.setOnDragOver(event -> {
			if (event.getGestureSource() != cell
					&& event.getDragboard().hasContent(TaskController.format)) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});

		cell.setOnDragEntered(event -> {
			if (event.getGestureSource() != cell
					&& event.getDragboard().hasContent(TaskController.format)) {
				cell.setOpacity(0.3);
			}
		});

		cell.setOnDragExited(event -> {
			if (event.getGestureSource() != cell
					&& event.getDragboard().hasContent(TaskController.format)) {
				cell.setOpacity(1);
			}
		});

		cell.setOnDragDropped(event -> {

			if (cell.getItem() == null) {
				return;
			}

			Dragboard db = event.getDragboard();
			boolean success = false;

			if (event.getDragboard().hasContent(TaskController.format)) {
				ObservableList<Requirement> items = elistView.getItems();
				Requirement dragged = (Requirement) db.getContent(TaskController.format);

				int draggedId = items.indexOf(dragged);
				int thisId = items.indexOf(cell.getItem());

				elistView.getItems().set(draggedId, cell.getItem());
				elistView.getItems().set(thisId, dragged);

				success = true;
			}
			event.setDropCompleted(success);
			event.consume();
		});
		return cell;
	}
}

