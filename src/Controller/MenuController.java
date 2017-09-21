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

package Controller;

import Model.Activity;
import Model.Assignment;
import Model.Coursework;
import Model.Deadline;
import Model.Event;
import Model.Exam;
import Model.ExamEvent;
import Model.Milestone;
import Model.ModelEntity;
import Model.Module;
import Model.Notification;
import Model.QuantityType;
import Model.Requirement;
import Model.StudyProfile;
import Model.Task;
import Model.TimetableEvent;
import View.GanttishDiagram;
import View.UIManager;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import jfxtras.scene.control.agenda.Agenda;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Zilvinas on 05/05/2017.
 */
public class MenuController implements Initializable {
	/**
	 * Initializes switch names and other buttons.
	 */
	public enum Window {
		Empty, Dashboard, Profiles, Modules, Milestones, Calendar
	}

	private Window current;
	private boolean isNavOpen;

	// Labels:
	private Label welcome;
	@FXML
	Label title;

	// Buttons:
	@FXML
	private Button openMenu;
	@FXML
	private Button showNotification;
	@FXML
	private Button showDash;
	@FXML
	private Button addActivity;
	@FXML
	private Button studyProfiles;
	@FXML
	private Button milestones;
	@FXML
	private Button modules;
	@FXML
	private Button calendar;

	// Panes:
	@FXML
	private AnchorPane navList;
	@FXML
	private AnchorPane notifications;
	@FXML
	private GridPane notificationList;
	@FXML
	private GridPane mainContent;
	@FXML
	private HBox topBox;

	/**
	 * Sets this.current to equal passed variable and calls this.main().
	 */
	public void main(Window wind) {
		this.current = wind;
		this.main();
	}

	/**
	 * Main method containing switch statements.
	 */
	public void main() {
		if (isNavOpen) {
			openMenu.fire();
		}

		this.updateNotifications();
		this.updateMenu();

		switch (this.current) {
		case Dashboard: {
			if (MainController.getSPC().getPlanner().getCurrentStudyProfile() != null) {
				this.loadDashboard();
			}
			break;
		}
		case Profiles: {
			this.loadStudyProfiles();
			break;
		}
		case Modules: {
			this.loadModules();
			break;
		}
		case Milestones: {
			this.loadMilestones();
			break;
		}
		case Calendar: {
			this.loadCalendar();
			break;
		}
		default:
			break;
		}
	}

	/**
	 * Display the Study Dashboard pane.
	 */
	public void loadDashboard() {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.topBox.getChildren().add(this.welcome);
		this.title.setText("Study Dashboard");
		// =================

		StudyProfile profile = MainController.getSPC().getPlanner().getCurrentStudyProfile();

		// Display studyProfile:
		Label studyProfile = new Label(profile.getName());
		studyProfile.getStyleClass().add("title");
		GridPane.setMargin(studyProfile, new Insets(10));
		this.mainContent.addRow(1, studyProfile);

		FlowPane modules = new FlowPane();
		modules.setHgap(30);
		modules.setVgap(20);
		modules.setPrefWrapLength(1000);
		for (Module module : profile.getModules()) {
			VBox vbox = new VBox();
			vbox.setSpacing(5);
			vbox.setMinWidth(200);
			vbox.setMaxWidth(200);
			vbox.setAlignment(Pos.CENTER);

			Label name = new Label(module.getName());
			name.setTextAlignment(TextAlignment.CENTER);
			vbox.getChildren().add(name);

			BufferedImage buff = GanttishDiagram.getBadge(module.calculateProgress(), true, 1);
			Image image = SwingFXUtils.toFXImage(buff, null);
			Pane badge = new Pane();
			VBox.setMargin(badge, new Insets(0, 0, 0, 50));
			badge.setPrefHeight(100);
			badge.setBackground(new Background(new BackgroundImage(image,
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
					BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO,
							BackgroundSize.AUTO, false, false, true, false))));

			vbox.getChildren().add(badge);
			Button view = new Button("View");
			view.setOnAction(e -> module.open(this.current));
			vbox.getChildren().add(view);

			modules.getChildren().add(vbox);
		}
		// =================

		GridPane.setColumnSpan(modules, GridPane.REMAINING);
		GridPane.setMargin(modules, new Insets(10));
		this.mainContent.addRow(2, modules);
	}

	/**
	 * Display the 'Add Activity' window.
	 */
	public void addActivity() {
		try {
			Activity activity = MainController.ui.addActivity();
			if (activity != null) {
				MainController.getSPC().addActivity(activity);
			}
			openMenu.fire();

		} catch (Exception e) {
			UIManager.reportError("Unable to open View file");
		}
	}

	/**
	 * Display the Milestones pane.
	 */
	public void loadMilestones() {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.title.setText("");
		// =================

		// Display milestones:
		Label milestones = new Label("Milestones");
		milestones.getStyleClass().add("title");
		this.mainContent.addRow(1, milestones);
		// =================

		// Columns:
		TableColumn<Milestone, String> nameColumn = new TableColumn<>("Milestone");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Milestone, String> deadlineColumn = new TableColumn<>("Deadline");
		deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
		deadlineColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		TableColumn<Milestone, String> completedColumn = new TableColumn<>("Tasks completed");
		completedColumn.setCellValueFactory(new PropertyValueFactory<>("taskCompletedAsString"));
		completedColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		TableColumn<Milestone, Integer> progressColumn = new TableColumn<>("Progress");
		progressColumn.setCellValueFactory(new PropertyValueFactory<>("progressPercentage"));
		progressColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		ObservableList<Milestone> list = FXCollections.observableArrayList(
				MainController.getSPC().getPlanner().getCurrentStudyProfile().getMilestones());
		// =================

		// Create a table:
		TableView<Milestone> table = new TableView<>();
		table.setItems(list);
		table.getColumns().addAll(nameColumn, deadlineColumn, completedColumn, progressColumn);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		GridPane.setHgrow(table, Priority.ALWAYS);
		GridPane.setVgrow(table, Priority.ALWAYS);
		// =================

		// Set click event:
		table.setRowFactory(e -> {
			TableRow<Milestone> row = new TableRow<Milestone>() {
				@Override
				protected void updateItem(final Milestone item, final boolean empty) {
					super.updateItem(item, empty);
					// If Milestone completed, mark:
					if (!empty && item != null && item.isComplete()) {
						this.getStyleClass().add("current-item");
					}
				}
			};
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
						&& event.getClickCount() == 2) {
					try {
						MainController.ui.milestoneDetails(row.getItem());
						this.main();
					} catch (IOException e1) {
						UIManager.reportError("Unable to open View file");
					}
				}
			});
			return row;
		});
		// =================

		this.mainContent.addRow(2, table);
		this.mainContent.getStyleClass().add("list-item");

		// Actions toolbar:
		HBox actions = new HBox();
		GridPane.setHgrow(actions, Priority.ALWAYS);
		actions.setSpacing(5);
		actions.setPadding(new Insets(5, 5, 10, 0));
		// =================

		// Buttons:
		Button add = new Button("Add a new Milestone");

		Button remove = new Button("Remove");
		remove.setDisable(true);
		// =================

		// Bind properties on buttons:
		remove.disableProperty().bind(new BooleanBinding() {
			{
				bind(table.getSelectionModel().getSelectedItems());
			}

			@Override
			protected boolean computeValue() {
				return !(list.size() > 0 && table.getSelectionModel().getSelectedItem() != null);
			}
		});
		// =================

		// Bind actions on buttons:
		add.setOnAction(e -> {
			try {
				Milestone milestone = MainController.ui.addMilestone();
				if (milestone != null) {
					list.add(milestone);
					MainController.getSPC().addMilestone(milestone);
				}
			} catch (IOException e1) {
				UIManager.reportError("Unable to open View file");
			} catch (Exception e1) {
			}
		});

		remove.setOnAction(e -> {
			if (UIManager.confirm("Are you sure you want to remove this milestone?")) {
				Milestone mm = table.getSelectionModel().getSelectedItem();
				list.remove(mm);
				MainController.getSPC().removeMilestone(mm);
			}
		});
		// =================

		actions.getChildren().addAll(add, remove);

		mainContent.addRow(3, actions);
		// =================
	}

	/**
	 * Display the Calendar pane.
	 */
	public void loadCalendar() {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.title.setText("");
		// =================
		// Layout:
		VBox layout = new VBox();
		GridPane.setHgrow(layout, Priority.ALWAYS);
		layout.setSpacing(10);
		layout.setPadding(new Insets(15));
		layout.getStylesheets().add("/Content/stylesheet.css");
		// =================
		// Nav bar:
		HBox nav = new HBox();
		nav.setSpacing(15.0);
		// =================
		// Title:
		Label title = new Label("Calendar");
		title.getStyleClass().add("title");
		HBox xx = new HBox();
		HBox.setHgrow(xx, Priority.ALWAYS);
		// =================
		// Buttons:
		Button agendaFwd = new Button(">");
		Button agendaBwd = new Button("<");
		// =================
		nav.getChildren().addAll(title, xx, agendaBwd, agendaFwd);
		// Content:
		Agenda content = new Agenda();
		VBox.setVgrow(content, Priority.ALWAYS);
		content.setAllowDragging(false);
		content.setAllowResize(false);
		content.autosize();
		content.setActionCallback(param -> null);
		content.setEditAppointmentCallback(param -> null);
		// Agenda buttons:
		agendaBwd.setOnMouseClicked(event -> content
				.setDisplayedLocalDateTime(content.getDisplayedLocalDateTime().minusDays(7)));
		agendaFwd.setOnMouseClicked(event -> content
				.setDisplayedLocalDateTime(content.getDisplayedLocalDateTime().plusDays(7)));
		// =================
		// Populate Agenda:
		ArrayList<Event> calendar = MainController.getSPC().getPlanner().getCurrentStudyProfile()
				.getCalendar();
		for (Event e : calendar) {
			if (e instanceof TimetableEvent) {
				LocalDateTime stime = LocalDateTime.ofInstant(e.getDate().toInstant(),
						ZoneId.systemDefault());
				content.appointments().addAll(new Agenda.AppointmentImplLocal()
						.withStartLocalDateTime(stime)
						.withEndLocalDateTime(stime.plusMinutes(((TimetableEvent) e).getDuration()))

						.withSummary(e.getName() + "\n" + "@ "
								+ ((TimetableEvent) e).getRoom().getLocation())
						.withAppointmentGroup(
								new Agenda.AppointmentGroupImpl().withStyleClass("group5")));
			} else if (e instanceof ExamEvent) {
				LocalDateTime stime = LocalDateTime.ofInstant(e.getDate().toInstant(),
						ZoneId.systemDefault());
				content.appointments().addAll(new Agenda.AppointmentImplLocal()
						.withStartLocalDateTime(stime)
						.withSummary(
								e.getName() + "\n" + "@ " + ((ExamEvent) e).getRoom().getLocation())
						.withEndLocalDateTime(stime.plusMinutes(((ExamEvent) e).getDuration()))
						.withAppointmentGroup(
								new Agenda.AppointmentGroupImpl().withStyleClass("group20")));
			} else if (e instanceof Deadline) {
				LocalDateTime stime = LocalDateTime.ofInstant(e.getDate().toInstant(),
						ZoneId.systemDefault());
				content.appointments().addAll(new Agenda.AppointmentImplLocal()
						.withStartLocalDateTime(stime.minusMinutes(60)).withSummary(e.getName())
						.withEndLocalDateTime(stime).withAppointmentGroup(
								new Agenda.AppointmentGroupImpl().withStyleClass("group1")));
			} else {
				LocalDateTime stime = LocalDateTime.ofInstant(e.getDate().toInstant(),
						ZoneId.systemDefault());
				content.appointments().addAll(new Agenda.AppointmentImplLocal()
						.withStartLocalDateTime(stime).withSummary(e.getName())
						.withEndLocalDateTime(stime.plusMinutes(60)).withAppointmentGroup(
								new Agenda.AppointmentGroupImpl().withStyleClass("group3")));
			}
		}
		layout.getChildren().addAll(nav, content);
		Platform.runLater(() -> content
				.setDisplayedLocalDateTime(content.getDisplayedLocalDateTime().plusMinutes(1050)));
		this.mainContent.getChildren().add(layout);
	}

	/**
	 * Display the Study Profiles pane.
	 */
	public void loadStudyProfiles() {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.title.setText("");
		// =================

		// Display profiles:
		Label profiles = new Label("Study Profiles");
		profiles.getStyleClass().add("title");
		this.mainContent.addRow(1, profiles);
		// =================

		// Columns:
		TableColumn<StudyProfile, String> nameColumn = new TableColumn<>("Profile name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<StudyProfile, Integer> yearColumn = new TableColumn<>("Year");
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
		yearColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		TableColumn<StudyProfile, Integer> semesterColumn = new TableColumn<>("Semester");
		semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semesterNo"));
		semesterColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		ObservableList<StudyProfile> list = FXCollections
				.observableArrayList(MainController.getSPC().getPlanner().getStudyProfiles());
		// =================

		// Create a table:

		TableView<StudyProfile> table = new TableView<>();
		table.setItems(list);
		table.getColumns().addAll(nameColumn, yearColumn, semesterColumn);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		GridPane.setHgrow(table, Priority.ALWAYS);
		GridPane.setVgrow(table, Priority.ALWAYS);
		// =================

		// Set click event:
		table.setRowFactory(e -> {
			TableRow<StudyProfile> row = new TableRow<StudyProfile>() {
				@Override
				protected void updateItem(final StudyProfile item, final boolean empty) {
					super.updateItem(item, empty);
					// If current Profile, mark:
					if (!empty && item != null && item.isCurrent()) {
						this.getStyleClass().add("current-item");
					}
				}
			};
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
						&& event.getClickCount() == 2) {
					try {
						MainController.ui.studyProfileDetails(row.getItem());
						this.main();
					} catch (IOException e1) {
						UIManager.reportError("Unable to open View file");
					}
				}
			});
			return row;
		});
		// =================

		this.mainContent.addRow(2, table);
		this.mainContent.getStyleClass().add("list-item");
	}

	/**
	 * Display the Modules pane.
	 */
	public void loadModules() {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.title.setText("");
		// =================

		// Display modules:
		Label modules = new Label("Modules");
		modules.getStyleClass().add("title");
		this.mainContent.addRow(1, modules);
		// =================

		// Columns:
		TableColumn<Module, String> codeColumn = new TableColumn<>("Module code");
		codeColumn.setCellValueFactory(new PropertyValueFactory<>("moduleCode"));

		TableColumn<Module, String> nameColumn = new TableColumn<>("Module name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Module, Integer> timeSpent = new TableColumn<>("Time spent");
		timeSpent.setCellValueFactory(new PropertyValueFactory("timeSpent") {
			@Override
			public ObservableValue call(TableColumn.CellDataFeatures param) {
				return new SimpleIntegerProperty(MainController.getSPC().getPlanner()
						.getTimeSpent((Module) param.getValue()));
			}
		});
		timeSpent.setStyle("-fx-alignment: CENTER-RIGHT;");

		ObservableList<Module> list = FXCollections.observableArrayList(
				MainController.getSPC().getPlanner().getCurrentStudyProfile().getModules());
		// =================

		// Create a table:
		TableView<Module> table = new TableView<>();
		table.setItems(list);
		table.getColumns().addAll(codeColumn, nameColumn, timeSpent);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		GridPane.setHgrow(table, Priority.ALWAYS);
		GridPane.setVgrow(table, Priority.ALWAYS);
		// =================

		// Set click event:
		table.setRowFactory(e -> {
			TableRow<Module> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
						&& event.getClickCount() == 2) {
					this.loadModule(row.getItem(), this.current, null);
				}
			});
			return row;
		});
		// =================

		this.mainContent.addRow(2, table);
		this.mainContent.getStyleClass().add("list-item");
	}

	/**
	 * Display the Module pane.
	 */
	public void loadModule(Module module, Window previousWindow, ModelEntity previous) {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.title.setText("");
		// =================

		// Create a back button:
		this.backButton(previousWindow, previous);
		// =================

		// Display modules:
		Label modules = new Label(module.getModuleCode() + " " + module.getName());
		modules.getStyleClass().add("title");
		this.mainContent.addRow(1, modules);
		// =================

		// Create a details pane:
		VBox detailsBox = new VBox(5);
		Label details = new Label(module.getDetails().getAsString());
		details.setWrapText(true);
		detailsBox.getChildren().addAll(new Label("Organised by: " + module.getOrganiser()),
				details);
		GridPane.setVgrow(detailsBox, Priority.SOMETIMES);
		GridPane.setHgrow(detailsBox, Priority.ALWAYS);
		// =================

		mainContent.addRow(2, detailsBox);

		// Assignments:
		TableColumn<Assignment, String> nameColumn = new TableColumn<>("Assignment");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Assignment, String> deadlineColumn = new TableColumn<>("Date");
		deadlineColumn.setCellValueFactory(new PropertyValueFactory("deadlineString") {
			@Override
			public ObservableValue call(TableColumn.CellDataFeatures param) {
				SimpleStringProperty value = new SimpleStringProperty();
				if (param.getValue() instanceof Coursework) {
					Coursework c = (Coursework) param.getValue();
					value.setValue(c.getDeadlineString());
				} else if (param.getValue() instanceof Exam) {
					Exam e = (Exam) param.getValue();
					value.setValue(e.getTimeSlot().getDateString());
				}
				return value;
			}
		});
		deadlineColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		TableColumn<Assignment, Integer> weightingColumn = new TableColumn<>("Weighting");
		weightingColumn.setCellValueFactory(new PropertyValueFactory<>("weighting"));
		weightingColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		ObservableList<Assignment> list = FXCollections
				.observableArrayList(module.getAssignments());
		// =================

		// Create a moduleContent:
		TableView<Assignment> moduleContent = new TableView<>();
		moduleContent.setItems(list);
		moduleContent.getColumns().addAll(nameColumn, deadlineColumn, weightingColumn);
		moduleContent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		GridPane.setHgrow(moduleContent, Priority.ALWAYS);
		GridPane.setVgrow(moduleContent, Priority.ALWAYS);
		// =================

		// Set click event:
		moduleContent.setRowFactory(e -> {
			TableRow<Assignment> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
						&& event.getClickCount() == 2) {
					this.loadAssignment(row.getItem(), Window.Empty, module);
				}
			});
			return row;
		});
		// =================

		this.mainContent.addRow(3, moduleContent);
	}

	/**
	 * Display the Assignment pane.
	 */
	public void loadAssignment(Assignment assignment, Window previousWindow, ModelEntity previous) {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.title.setText("");
		// =================

		// Create a back button:
		this.backButton(previousWindow, previous);
		// =================

		// Display modules:
		Label assignments = new Label(assignment.getName());
		assignments.getStyleClass().add("title");
		this.mainContent.addRow(1, assignments);
		// =================

		// Ganttish chart button:
		Button gantt = new Button("Generate a Ganttish Diagram");
		gantt.setOnAction(e -> MainController.ui.showGantt(assignment));
		GridPane.setHalignment(gantt, HPos.RIGHT);
		GridPane.setColumnSpan(gantt, GridPane.REMAINING);
		this.mainContent.add(gantt, 0, 1);
		// =================

		// Create a details pane:
		VBox detailsBox = new VBox(5);
		Label details = new Label(assignment.getDetails().getAsString());
		details.setWrapText(true);
		String date = "";
		if (assignment instanceof Coursework) {
			Coursework cc = (Coursework) assignment;
			date = "Deadline: " + cc.getDeadlineString();
		} else if (assignment instanceof Exam) {
			Exam e1 = (Exam) assignment;
			date = "Date: " + e1.getTimeSlot().getDateString();
		}
		detailsBox.getChildren().addAll(new Label("Weighting: " + assignment.getWeighting()),
				new Label(date), new Label("Set by: " + assignment.getSetBy().getPreferredName()),
				new Label("Marked by: " + assignment.getMarkedBy().getPreferredName()),
				new Label("Reviewed by: " + assignment.getReviewedBy().getPreferredName()),
				details);
		GridPane.setVgrow(detailsBox, Priority.SOMETIMES);
		GridPane.setHgrow(detailsBox, Priority.ALWAYS);
		// =================

		mainContent.addRow(2, detailsBox);

		// Content pane:
		GridPane content = new GridPane();
		GridPane.setVgrow(content, Priority.ALWAYS);
		GridPane.setHgrow(content, Priority.ALWAYS);
		content.setVgap(5);
		// =================

		// Requirements columns:
		TableColumn<Requirement, String> rNameColumn = new TableColumn<>("Requirement");
		rNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Requirement, Integer> remainingColumn = new TableColumn<>("Remaining");
		remainingColumn.setCellValueFactory(new PropertyValueFactory<>("remainingQuantity"));
		remainingColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		TableColumn<Requirement, QuantityType> typeColumn = new TableColumn<>("Quantity type");
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("quantityType"));

		ObservableList<Requirement> requirementList = FXCollections
				.observableArrayList(assignment.getRequirements());
		// =================

		// Create Requirements table:
		TableView<Requirement> requirements = new TableView<>();
		requirements.setItems(requirementList);
		requirements.getColumns().addAll(rNameColumn, remainingColumn, typeColumn);
		requirements.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		GridPane.setHgrow(requirements, Priority.ALWAYS);
		GridPane.setVgrow(requirements, Priority.ALWAYS);
		// =================

		// Set RowFactory:
		requirements
				.setRowFactory(e -> MenuController.requirementRowFactory(requirements, assignment));
		// =================

		content.addColumn(0, requirements);

		// Actions toolbar:
		HBox actionsReq = new HBox();
		GridPane.setHgrow(actionsReq, Priority.ALWAYS);
		actionsReq.setSpacing(5);
		actionsReq.setPadding(new Insets(5, 5, 10, 0));
		// =================

		// Buttons:
		Button addNewReq = new Button("Add a new requirement");

		Button deleteReq = new Button("Remove");
		deleteReq.setDisable(true);
		// =================

		// Bind properties on buttons:
		deleteReq.disableProperty().bind(new BooleanBinding() {
			{
				bind(requirements.getSelectionModel().getSelectedItems());
			}

			@Override
			protected boolean computeValue() {
				return !(requirementList.size() > 0
						&& requirements.getSelectionModel().getSelectedItem() != null);
			}
		});
		// =================

		// Bind actions on buttons:
		addNewReq.setOnAction(e -> {
			try {
				Requirement req = MainController.ui.addRequirement();
				if (req != null) {
					requirementList.add(req);
					assignment.addRequirement(req);
					requirements.refresh();
				}
			} catch (IOException e1) {
				UIManager.reportError("Unable to open View file");
			} catch (Exception e1) {
			}
		});

		deleteReq.setOnAction(e -> {
			if (UIManager.confirm("Are you sure you want to remove this requirement?")) {
				Requirement rr = requirements.getSelectionModel().getSelectedItem();
				requirementList.remove(rr);
				assignment.removeRequirement(rr);
				requirements.refresh();
			}
		});
		// =================

		actionsReq.getChildren().addAll(addNewReq, deleteReq);

		content.add(actionsReq, 0, 1);
		// =================

		// Tasks columns:
		TableColumn<Task, String> nameColumn = new TableColumn<>("Task");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Task, String> deadlineColumn = new TableColumn<>("Deadline");
		deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
		deadlineColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		TableColumn<Task, BooleanProperty> canComplete = new TableColumn<>("Can be completed?");
		canComplete.setCellValueFactory(new PropertyValueFactory<>("possibleToComplete"));
		canComplete.setStyle("-fx-alignment: CENTER-RIGHT;");

		ObservableList<Task> list = FXCollections.observableArrayList(assignment.getTasks());
		// =================

		// Create Tasks table:
		TableView<Task> tasks = new TableView<>();
		tasks.setItems(list);
		tasks.getColumns().addAll(nameColumn, deadlineColumn, canComplete);
		tasks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		GridPane.setHgrow(tasks, Priority.ALWAYS);
		GridPane.setVgrow(tasks, Priority.ALWAYS);
		// =================

		// Set click event:
		tasks.setRowFactory(e -> {
			TableRow<Task> row = new TableRow<Task>() {
				@Override
				protected void updateItem(final Task item, final boolean empty) {
					super.updateItem(item, empty);
					// If completed, mark:
					if (!empty && item != null && item.isCheckedComplete()) {
						this.getStyleClass().add("current-item");
					} else {
						this.getStyleClass().remove("current-item");
					}
				}
			};
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
						&& event.getClickCount() == 2) {
					try {
						MainController.ui.taskDetails(row.getItem());
						tasks.refresh();
					} catch (IOException e1) {
						UIManager.reportError("Unable to open View file");
					}
				}
			});
			return row;
		});
		// =================

		content.addColumn(1, tasks);

		// Actions toolbar:
		HBox actionsTask = new HBox();
		GridPane.setHgrow(actionsTask, Priority.ALWAYS);
		actionsTask.setSpacing(5);
		actionsTask.setPadding(new Insets(5, 5, 10, 0));
		// =================

		// Buttons:
		Button addNew = new Button("Add a new task");

		Button check = new Button("Toggle complete");
		check.getStyleClass().add("set-button");
		check.setDisable(true);

		Button delete = new Button("Remove");
		delete.setDisable(true);
		// =================

		// Bind properties on buttons:
		delete.disableProperty().bind(new BooleanBinding() {
			{
				bind(tasks.getSelectionModel().getSelectedItems());
			}

			@Override
			protected boolean computeValue() {
				return !(list.size() > 0 && tasks.getSelectionModel().getSelectedItem() != null);
			}
		});

		check.disableProperty().bind(new BooleanBinding() {
			{
				bind(tasks.getSelectionModel().getSelectedItems());
			}

			@Override
			protected boolean computeValue() {
				return !(list.size() > 0 && tasks.getSelectionModel().getSelectedItem() != null
						&& tasks.getSelectionModel().getSelectedItem().canCheckComplete());
			}
		});
		// =================

		// Bind actions on buttons:
		addNew.setOnAction(e -> {
			try {
				Task task = MainController.ui.addTask();
				if (task != null) {
					list.add(task);
					assignment.addTask(task);
				}
				this.updateMenu();
			} catch (IOException e1) {
				UIManager.reportError("Unable to open View file");
			} catch (Exception e1) {
			}
		});

		check.setOnAction(e -> {
			tasks.getSelectionModel().getSelectedItem().toggleComplete();
			tasks.refresh();
		});

		delete.setOnAction(e -> {
			if (UIManager.confirm("Are you sure you want to remove this task?")) {
				Task tt = tasks.getSelectionModel().getSelectedItem();
				list.remove(tt);
				assignment.removeTask(tt);
				this.updateMenu();
			}
		});
		// =================

		// Gap:
		HBox gap = new HBox();
		HBox.setHgrow(gap, Priority.ALWAYS);
		// =================

		actionsTask.getChildren().addAll(addNew, gap, check, delete);

		content.add(actionsTask, 1, 1);

		this.mainContent.addRow(3, content);
	}

	/**
	 * Handles the 'Mark all as read' button event.
	 */
	public void handleMarkAll() {
		Notification[] nots = MainController.getSPC().getPlanner().getUnreadNotifications();
		// Mark all notifications as read:
		for (int i = 0; i < nots.length; ++i) {
			int index = this.notificationList.getChildren().size() - 1 - i;
			nots[i].read();
			// Remove cursor:
			if (nots[i].getLink() == null) {
				this.notificationList.getChildren().get(index).setCursor(Cursor.DEFAULT);
			}

			// Change style:
			this.notificationList.getChildren().get(index).getStyleClass().remove("unread-item");
		}

		// Handle styles:
		this.showNotification.getStyleClass().remove("unread-button");
		if (!this.showNotification.getStyleClass().contains("read-button")) {
			this.showNotification.getStyleClass().add("read-button");
		}
	}

	/**
	 * Handles clicking on a specific notification.
	 *
	 * @param id
	 */
	public void handleRead(int id) {
		// Get notification:
		int idInList = MainController.getSPC().getPlanner().getNotifications().length - 1 - id;
		Notification not = MainController.getSPC().getPlanner().getNotifications()[idInList];

		// If not read:
		if (!not.isRead()) {
			// Mark notification as read:
			not.read();

			// Swap styles:
			this.notificationList.getChildren().get(id).getStyleClass().remove("unread-item");
			if (MainController.getSPC().getPlanner().getUnreadNotifications().length <= 0) {
				this.showNotification.getStyleClass().remove("unread-button");
				if (!this.showNotification.getStyleClass().contains("read-button")) {
					this.showNotification.getStyleClass().add("read-button");
				}
			}

			if (not.getLink() == null) {
				this.notificationList.getChildren().get(id).setCursor(Cursor.DEFAULT);
			}
		}

		if (not.getLink() != null) {
			not.getLink().open(this.current);
			this.main();
		}
	}

	/**
	 * Handles the 'Import HUB file' event.
	 */
	public void importFile() {
		if (MainController.importFile()) {
			UIManager.reportSuccess("File imported successfully!");
		}
		this.main();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.prepareAnimations();
		this.isNavOpen = false;

		// Set button actions:
		this.showDash.setOnAction(e -> this.main(Window.Dashboard));
		this.studyProfiles.setOnAction(e -> this.main(Window.Profiles));
		this.modules.setOnAction(e -> this.main(Window.Modules));
		this.milestones.setOnAction(e -> this.main(Window.Milestones));
		this.calendar.setOnAction(e -> this.main(Window.Calendar));
		// =================

		// Welcome text:
		this.welcome = new Label(
				"Welcome back, " + MainController.getSPC().getPlanner().getUserName() + "!");
		this.welcome.setPadding(new Insets(10, 15, 10, 15));
		this.topBox.getChildren().add(this.welcome);
		// =================

		this.mainContent.setVgap(10);
		this.mainContent.setPadding(new Insets(15));

		// Render dashboard:
		this.main(Window.Dashboard);
		// =================
	}

	/**
	 * Prepare notifications.
	 */
	private void updateNotifications() {
		MainController.getSPC().checkForNotifications();

		// Set notification button style:
		if (MainController.getSPC().getPlanner().getUnreadNotifications().length > 0) {
			if (!this.showNotification.getStyleClass().contains("unread-button")) {
				this.showNotification.getStyleClass().remove("read-button");
				this.showNotification.getStyleClass().add("unread-button");
			}
		} else if (!this.showNotification.getStyleClass().contains("read-button")) {
			this.showNotification.getStyleClass().add("read-button");
			this.showNotification.getStyleClass().remove("unread-button");
		}

		// Process notifications:
		this.notificationList.getChildren().clear();
		Notification[] notifications = MainController.getSPC().getPlanner().getNotifications();
		for (int i = notifications.length - 1; i >= 0; i--) {
			GridPane pane = new GridPane();

			// Check if has a link or is unread:
			if (notifications[i].getLink() != null || !notifications[i].isRead()) {
				pane.setCursor(Cursor.HAND);
				pane.setId(Integer.toString(notifications.length - i - 1));
				pane.setOnMouseClicked(e -> this.handleRead(Integer.parseInt(pane.getId())));

				// Check if unread:
				if (!notifications[i].isRead()) {
					pane.getStyleClass().add("unread-item");
				}
			}

			// Create labels:
			Label title = new Label(notifications[i].getTitle());
			title.getStyleClass().add("notificationItem-title");
			title.setMaxWidth(250.0);

			Label details = notifications[i].getDetails() != null ? new Label(notifications[i].getDetailsAsString())
					: new Label();
			details.getStyleClass().add("notificationItem-details");
			details.setMaxWidth(250.0);

			String dateFormatted = notifications[i].getDateTime().get(Calendar.DAY_OF_MONTH) + " "
					+ notifications[i].getDateTime().getDisplayName(Calendar.MONTH, Calendar.LONG,
							Locale.getDefault())
					+ " at " + notifications[i].getDateTime().get(Calendar.HOUR) + " " + notifications[i].getDateTime()
							.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
			Label date = new Label(dateFormatted);
			date.getStyleClass().addAll("notificationItem-date");
			GridPane.setHalignment(date, HPos.RIGHT);
			GridPane.setHgrow(date, Priority.ALWAYS);

			pane.addRow(1, title);
			pane.addRow(2, details);
			pane.addRow(3, date);
			pane.addRow(4, new Separator(Orientation.HORIZONTAL));
			this.notificationList.addRow(notifications.length - i - 1, pane);
		}
	}

	/**
	 * Handles menu options.
	 */
	private void updateMenu() {
		this.addActivity.setDisable(false);
		this.milestones.setDisable(false);
		this.studyProfiles.setDisable(false);
		this.modules.setDisable(false);
		this.calendar.setDisable(false);

		// Disable relevant menu options:
		if (MainController.getSPC().getPlanner().getCurrentStudyProfile() == null) {
			this.addActivity.setDisable(true);
			this.milestones.setDisable(true);
			this.studyProfiles.setDisable(true);
			this.modules.setDisable(true);
			this.calendar.setDisable(true);
		} else {
			if (MainController.getSPC().getCurrentTasks().size() <= 0) {
				this.addActivity.setDisable(true);
				this.milestones.setDisable(true);
			}

			if (MainController.getSPC().getPlanner().getCurrentStudyProfile()
					.getModules().length <= 0) {
				this.modules.setDisable(true);
			}
		}
	}

	/**
	 * Creates a back button.
	 */
	public void backButton(Window previousWindow, ModelEntity previous) {
		if (previous != null || previousWindow != Window.Empty) {
			Button back = new Button();
			back.getStyleClass().addAll("button-image", "back-button");

			if (previous == null && previousWindow != Window.Empty) {
				back.setOnAction(e -> this.main(previousWindow));
			} else {
				back.setOnAction(e -> previous.open(this.current));
			}

			this.topBox.getChildren().add(back);
		}
	}

	/**
	 * Prepares animations for the main window.
	 */
	private void prepareAnimations() {
		TranslateTransition openNav = new TranslateTransition(new Duration(300), navList);
		openNav.setToX(0);
		TranslateTransition closeNav = new TranslateTransition(new Duration(300), navList);
		openMenu.setOnAction((ActionEvent e1) -> {
			this.isNavOpen = !isNavOpen;
			if (navList.getTranslateX() != 0) {
				openNav.play();
			} else {
				closeNav.setToX(-(navList.getWidth()));
				closeNav.play();
			}
		});

		TranslateTransition openNot = new TranslateTransition(new Duration(350), notifications);
		openNot.setToY(0);
		TranslateTransition closeNot = new TranslateTransition(new Duration(350), notifications);

		showNotification.setOnAction((ActionEvent e1) -> {
			if (notifications.getTranslateY() != 0) {
				openNot.play();
			} else {
				closeNot.setToY(-(notifications.getHeight()) - 56.0);
				closeNot.play();
			}
		});
	}

	/**
	 * RowFactory for a TableView of Requirement.
	 *
	 * @param e
	 *            TableView that contains the RowFactory.
	 * @return new RowFactory
	 */
	protected static TableRow<Requirement> requirementRowFactory(TableView<Requirement> e1,
			Assignment assignment) {
		TableRow<Requirement> row = new TableRow<Requirement>() {
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
				e1.refresh();
			}
		};

		row.setOnMouseClicked(event -> {
			if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
					&& event.getClickCount() == 2) {
				try {
					MainController.ui.requirementDetails(row.getItem());
					e1.refresh();
				} catch (IOException e) {
					UIManager.reportError("Unable to open View file");
				}
			}
		});

		row.setOnDragDetected(event -> {

			if (row.getItem() == null) {
				return;
			}
			Dragboard dragboard = row.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.put(TaskController.format, row.getItem());
			dragboard.setContent(content);
			event.consume();
		});

		row.setOnDragOver(event -> {
			if (event.getGestureSource() != row
					&& event.getDragboard().hasContent(TaskController.format)) {
				event.acceptTransferModes(TransferMode.MOVE);
				event.consume();
			}
		});

		row.setOnDragEntered(event -> {
			if (event.getGestureSource() != row
					&& event.getDragboard().hasContent(TaskController.format)) {
				row.setOpacity(0.3);
			}
		});

		row.setOnDragExited(event -> {
			if (event.getGestureSource() != row
					&& event.getDragboard().hasContent(TaskController.format)) {
				row.setOpacity(1);
			}
		});

		row.setOnDragDropped(event -> {

			if (row.getItem() == null) {
				return;
			}

			Dragboard db = event.getDragboard();
			boolean success = false;

			if (event.getDragboard().hasContent(TaskController.format)) {
				ObservableList<Requirement> items = e1.getItems();
				Requirement dragged = (Requirement) db.getContent(TaskController.format);

				int draggedId = items.indexOf(dragged);
				int thisId = items.indexOf(row.getItem());

				e1.getItems().set(draggedId, row.getItem());
				e1.getItems().set(thisId, dragged);

				ArrayList<Requirement> reqs = assignment.getRequirements();
				reqs.set(draggedId, row.getItem());
				reqs.set(thisId, dragged);

				success = true;
				e1.refresh();
			}
			event.setDropCompleted(success);
			event.consume();
		});
		return row;
	}
}
