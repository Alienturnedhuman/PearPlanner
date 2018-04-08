/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar, Amila Dias
 *
 * Copyright (C) 2018 - Clayton D. Terrill
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
import edu.wright.cs.raiderplanner.model.Assignment;
import edu.wright.cs.raiderplanner.model.Coursework;
import edu.wright.cs.raiderplanner.model.Deadline;
import edu.wright.cs.raiderplanner.model.Event;
import edu.wright.cs.raiderplanner.model.Exam;
import edu.wright.cs.raiderplanner.model.ExamEvent;
import edu.wright.cs.raiderplanner.model.Milestone;
import edu.wright.cs.raiderplanner.model.ModelEntity;
import edu.wright.cs.raiderplanner.model.Module;
import edu.wright.cs.raiderplanner.model.Notification;
import edu.wright.cs.raiderplanner.model.QuantityType;
import edu.wright.cs.raiderplanner.model.Requirement;
import edu.wright.cs.raiderplanner.model.StudyProfile;
import edu.wright.cs.raiderplanner.model.Task;
import edu.wright.cs.raiderplanner.model.TimetableEvent;
import edu.wright.cs.raiderplanner.view.GanttishDiagram;
import edu.wright.cs.raiderplanner.view.UiManager;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.scene.control.agenda.Agenda;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Actions associated with the menu and its items.
 *
 * @author Zilvinas Ceikauskas
 */

public class MenuController implements Initializable {

	/**
	 * Initializes switch names and other buttons.
	 */
	public enum Window {
		EMPTY, DASHBOARD, PROFILES, MODULES, MILESTONES, CALENDAR, CHAT
	}

	private Window current;
	private boolean isNavOpen;
	private boolean mouseDown = false;
	private boolean initialLoad = true;

	// Screen size:
	private double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
	private double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
	private double screenAverage = (screenWidth + screenHeight) / 2;

	// Shadows:
	private int navShadowRadius = (int) (screenAverage * 0.03);
	private int navShadowOffset = (int) (screenAverage * 0.01);
	private DropShadow navShadow = new DropShadow(navShadowRadius, navShadowOffset, 0, Color.BLACK);
	private DropShadow notifShadow = new DropShadow(screenAverage * 0.02, 0, 0.009, Color.BLACK);
	private DropShadow moduleDefaultShadow = new DropShadow(screenAverage * 0.005, 0, 0,
			Color.BLACK);
	private DropShadow moduleHoverShadow = new DropShadow(screenAverage * 0.02, 0, 0, Color.BLACK);
	private InnerShadow modulePressedShadow = new InnerShadow(screenAverage * 0.017, 0, 0,
			Color.BLACK);
	private Stage stage = null;
	// Labels:
	private Label welcome;
	@FXML
	private Label title;

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
	@FXML
	private Button chat;
	@FXML
	private Button closeDrawer;

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
	@FXML
	private HBox exportCalBox;

	// chat variables
	private final BorderPane mainPane = new BorderPane();
	private final GridPane firstPane = new GridPane();
	private final GridPane userMessagePane = new GridPane();
	private final HBox spacingBox = new HBox();
	private TextField tfName = new TextField("");
	private TextField tfHost = new TextField("");
	private TextField tfMessageToSend = new TextField();
	private TextArea msgArea = new TextArea();
	private final Label name = new Label("Name:");
	private final Label host = new Label("Host:");
	private final Button submitButton = new Button("Submit");
	private final Button sendButton = new Button("Send");
	private boolean calendarOpen = false; // Used to monitor status of calendar (open or closed)

	private String userName;
	private String hostName;
	private int portNumber = 1111;

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
		if (this.showNotification.getTranslateY() == 0 && !initialLoad) {
			TranslateTransition closeNot = new TranslateTransition(new Duration(173),
					notifications);
			closeNot.setToY(-(notifications.getHeight() + this.navShadowRadius + 56 + 17));
			closeNot.play();
		}
		initialLoad = false;

		this.updateNotifications();
		this.updateMenu();
		exportCalBox.managedProperty().bind(exportCalBox.visibleProperty());

		// When user chooses different option in menu
		//		calendarOpen changes to monitor status within main window.
		switch (this.current) {
		case DASHBOARD: {
			if (MainController.getSpc().getPlanner().getCurrentStudyProfile() != null) {
				this.loadDashboard();
				calendarOpen = false;
			}
			break;
		}
		case PROFILES: {
			this.loadStudyProfiles();
			calendarOpen = false;
			break;
		}
		case MODULES: {
			this.loadModules();
			calendarOpen = false;
			break;
		}
		case MILESTONES: {
			this.loadMilestones();
			calendarOpen = false;
			break;
		}
		case CALENDAR: {
			this.loadCalendar();
			calendarOpen = true;
			break;
		}
		case CHAT: {
			this.obtainUserInformation();
			calendarOpen = false;
			break;
		}
		default:
			calendarOpen = false;
			break;
		}
		// Based on user choice of menu option "Export Calendar" button is shown/hidden
		exportCalBox.setVisible(calendarOpen);
	}

	/**
	 * Display the Study Dashboard pane.
	 */
	public void loadDashboard() {
		// set ToolTips
		openMenu.setTooltip(new Tooltip("Menu"));
		showNotification.setTooltip(new Tooltip("Notifications"));
		addActivity.setTooltip(new Tooltip("Add activity"));
		calendar.setTooltip(new Tooltip("Open Calendar"));

		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.topBox.getChildren().add(this.welcome);
		this.title.setText("Study Dashboard");

		StudyProfile profile = MainController.getSpc().getPlanner().getCurrentStudyProfile();

		// Display studyProfile:
		Label studyProfile = new Label(profile.getName());
		studyProfile.getStyleClass().add("title");
		GridPane.setMargin(studyProfile, new Insets(10));
		this.mainContent.getColumnConstraints()
				.add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE,
						Region.USE_COMPUTED_SIZE, Priority.ALWAYS, HPos.CENTER, true));
		FlowPane modulesPane = new FlowPane();

		Thread renderModules = new Thread(() -> {
			Label oldLabel = new Label(this.welcome.getText());
			Thread sayLoading = new Thread(() -> {
				this.welcome.setText(this.welcome.getText() + "  LOADING...");
			});
			Platform.runLater(sayLoading);

			for (Module module : profile.getModules()) {
				VBox vbox = new VBox();

				// Set the width of the module to 15% of the screen resolution
				if (screenWidth > screenHeight) {
					vbox.setPrefWidth(screenWidth * 0.14);
				} else {
					// If device is in portrait mode, set vbox width based on height
					vbox.setPrefWidth(screenHeight * 0.14);
				}
				// Set the height of the module to 112% of its width
				vbox.setPrefHeight(vbox.getPrefWidth() * 1.12);
				// Set margin between text and badge to 10% vbox width
				vbox.setSpacing(vbox.getPrefWidth() * 0.1);

				vbox.setAlignment(Pos.CENTER);
				vbox.setCursor(Cursor.HAND);

				Label nameLabel = new Label(module.getName());
				nameLabel.setTextAlignment(TextAlignment.CENTER);

				// Set left margin for title, which creates padding in case title is very long
				VBox.setMargin(nameLabel, new Insets(0, 0, 0, vbox.getPrefWidth() * 0.04));

				vbox.getChildren().add(nameLabel);

				BufferedImage buff = GanttishDiagram.getBadge(module.calculateProgress(), true, 1);
				Image image = SwingFXUtils.toFXImage(buff, null);
				Pane badge = new Pane();

				// Set the distance from left edge to badge 17% of vbox width
				VBox.setMargin(badge, new Insets(0, 0, 0, vbox.getPrefWidth() * 0.17));
				// Set the badge width to 66% that of vbox
				badge.setPrefHeight(vbox.getPrefWidth() * 0.66);

				badge.setBackground(new Background(new BackgroundImage(image,
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
						BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO,
								BackgroundSize.AUTO, false, false, true, false))));
				vbox.getChildren().add(badge);

				/*
				 * If mouse clicks on module, depress it. If mouse leaves module while depressed,
				 * undepress button. If mouse re-enters, then re-depress module. If mouse is not
				 * depressed when it enters module, show hover effect.
				 */
				vbox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
					if (mouseDown) {
						vbox.setEffect(this.modulePressedShadow);
					} else {
						vbox.setEffect(this.moduleHoverShadow);
					}
				});
				vbox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
					vbox.setEffect(this.moduleDefaultShadow);
				});
				vbox.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
					if (e.getButton() == MouseButton.PRIMARY) {
						vbox.setEffect(this.modulePressedShadow);
						mouseDown = true;
					}
				});
				vbox.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
					if (e.getButton() == MouseButton.PRIMARY) {
						vbox.setEffect(this.moduleDefaultShadow);
						mouseDown = false;
					}
				});
				vbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					if (e.getButton() == MouseButton.PRIMARY) {
						module.open(this.current);
					}
				});
				vbox.setOnTouchPressed(new EventHandler<TouchEvent>() {
					@Override
					public void handle(TouchEvent event) {
						vbox.setEffect(modulePressedShadow);
					}
				});
				vbox.setOnTouchReleased(new EventHandler<TouchEvent>() {
					@Override
					public void handle(TouchEvent event) {
						vbox.setEffect(moduleDefaultShadow);
					}
				});

				vbox.setEffect(this.moduleDefaultShadow);
				vbox.setStyle("-fx-background-color: white");

				Thread addModule = new Thread(() -> {
					modulesPane.getChildren().add(vbox);
				});
				Platform.runLater(addModule);

				// Ensure shadows don't overlap with edge of FlowPane
				FlowPane.setMargin(vbox, new Insets(screenHeight * 0.033, 0, screenHeight * 0.022,
						screenWidth * 0.037));
			}
			Thread removeLoading = new Thread(() -> {
				this.welcome.setText(oldLabel.getText());
			});
			Platform.runLater(removeLoading);
		});
		renderModules.start();

		/*
		 * Allow modules to be scrollable if window is too small to display them all on screen
		 * simultaneously.
		 */
		ScrollPane moduleBox = new ScrollPane();
		moduleBox.setContent(modulesPane);
		moduleBox.setStyle("-fx-background-color: transparent");
		moduleBox.setFitToHeight(true);
		moduleBox.setFitToWidth(true);
		GridPane.setColumnSpan(moduleBox, GridPane.REMAINING);
		this.mainContent.addRow(2, moduleBox);
	}

	/**
	 * Display the 'Add Activity' window.
	 */
	public void addActivity() {
		try {
			Activity activity = MainController.ui.addActivity();
			if (activity != null) {
				MainController.getSpc().addActivity(activity);
			}

		} catch (IOException e) {
			UiManager.reportError("Unable to open View file");
		} catch (Exception e) {
			UiManager.reportError(e.getMessage());
		}
	}

	/**
	 * Display the Milestones pane.
	 */
	public void loadMilestones() {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.title.setText("Milestones");

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

		ArrayList<TableColumn<Milestone, ?>> colList = new ArrayList<>(
				Arrays.asList(nameColumn, deadlineColumn, completedColumn, progressColumn));

		ObservableList<Milestone> list = FXCollections.observableArrayList(
				MainController.getSpc().getPlanner().getCurrentStudyProfile().getMilestones());

		// Create a table:
		TableView<Milestone> table = new TableView<>();
		table.setItems(list);
		table.getColumns().addAll(colList);
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
			row.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				if (event.getButton() == MouseButton.PRIMARY) {
					if (this.isNavOpen) {
						closeDrawer.fire();
					}
					if (this.showNotification.getTranslateY() == 0) {
						TranslateTransition closeNot = new TranslateTransition(new Duration(173),
								notifications);
						closeNot.setToY(-(notifications.getHeight() + this.navShadowRadius + 56
								+ 17));
						closeNot.play();
					}

					if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
							&& event.getClickCount() == 2) {
						try {
							MainController.ui.milestoneDetails(row.getItem());
							this.main();
						} catch (IOException e1) {
							UiManager.reportError("Unable to open View file");
						}
					}
				}
			});
			return row;
		});

		this.mainContent.addRow(2, table);
		this.mainContent.getStyleClass().add("list-item");
		GridPane.setColumnSpan(table, GridPane.REMAINING);

		// Actions toolbar:
		HBox actions = new HBox();
		GridPane.setHgrow(actions, Priority.ALWAYS);
		actions.setSpacing(5);
		actions.setPadding(new Insets(5, 5, 10, 0));

		// Buttons:
		Button add = new Button("Add a new Milestone");
		Button remove = new Button("Remove");
		remove.setDisable(true);

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

		// Bind actions on buttons:
		add.setOnAction(e -> {
			try {
				Milestone milestone = MainController.ui.addMilestone();
				if (milestone != null) {
					list.add(milestone);
					MainController.getSpc().addMilestone(milestone);
				}
			} catch (IOException e1) {
				UiManager.reportError("Unable to open View file");
			}
		});

		remove.setOnAction(e -> {
			if (UiManager.confirm("Are you sure you want to remove this milestone?")) {
				Milestone mm = table.getSelectionModel().getSelectedItem();
				list.remove(mm);
				MainController.getSpc().removeMilestone(mm);
			}
		});

		actions.getChildren().addAll(add, remove);

		mainContent.addRow(3, actions);
	}

	/**
	 * Display the Calendar pane.
	 */
	public void loadCalendar() {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.title.setText("Calendar");

		// Layout:
		VBox layout = new VBox();
		GridPane.setHgrow(layout, Priority.ALWAYS);
		GridPane.setColumnSpan(layout, GridPane.REMAINING);
		layout.setSpacing(10);
		layout.setPadding(new Insets(15));
		layout.getStylesheets().add("/edu/wright/cs/raiderplanner/content/stylesheet.css");
		// Nav bar:
		HBox nav = new HBox();
		nav.setSpacing(15.0);

		HBox xx = new HBox();
		HBox.setHgrow(xx, Priority.ALWAYS);
		// Buttons:
		Button agendaFwd = new Button(">");
		Button agendaBwd = new Button("<");

		nav.getChildren().addAll(xx, agendaBwd, agendaFwd);
		// Content:
		Agenda content = new Agenda();
		VBox.setVgrow(content, Priority.ALWAYS);
		content.setAllowDragging(false);
		content.setAllowResize(false);
		content.autosize();
		content.setActionCallback(param -> null);
		content.setEditAppointmentCallback(param -> null);
		// Agenda buttons:
		agendaBwd.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				content.setDisplayedLocalDateTime(content.getDisplayedLocalDateTime().minusDays(7));
			}
		});
		agendaFwd.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				content.setDisplayedLocalDateTime(content.getDisplayedLocalDateTime().plusDays(7));
			}
		});
		// Populate Agenda:
		ArrayList<Event> calendarEvents =
				MainController.getSpc().getPlanner().getCurrentStudyProfile().getCalendar();
		for (Event e : calendarEvents) {
			// TODO - find a way to eliminate this if/else-if/instanceof anti-pattern
			if (e instanceof TimetableEvent) {
				LocalDateTime stime = LocalDateTime.ofInstant(e.getDate().toInstant(),
						ZoneId.systemDefault());
				content.appointments().addAll(new Agenda.AppointmentImplLocal()
						.withStartLocalDateTime(stime)
						.withEndLocalDateTime(stime.plusMinutes(e.getDuration()))

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
						.withEndLocalDateTime(stime.plusMinutes(e.getDuration()))
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
		Button printBtn = new Button();
		printBtn.getStyleClass().addAll("button-image", "print-button");
		printBtn.setOnMouseClicked(event -> {
			// Prints the currently selected week
			PrinterJob job = PrinterJob.createPrinterJob();
			if (job != null) {
				if (!job.showPrintDialog(null)) {
					// user cancelled
					job.cancelJob();
					return;
				}
				content.print(job);
				job.endJob();
			}
		});
		nav.getChildren().add(1, printBtn);
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
		this.title.setText("Study Profiles");


		// Columns:
		TableColumn<StudyProfile, String> nameColumn = new TableColumn<>("Profile name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<StudyProfile, Integer> yearColumn = new TableColumn<>("Year");
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
		yearColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		TableColumn<StudyProfile, Integer> semesterColumn = new TableColumn<>("Semester");
		semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semesterNo"));
		semesterColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		ArrayList<TableColumn<StudyProfile, ?>> colList = new ArrayList<>(
				Arrays.asList(nameColumn, yearColumn, semesterColumn));

		ObservableList<StudyProfile> list = FXCollections
				.observableArrayList(MainController.getSpc().getPlanner().getStudyProfiles());

		// Create a table:

		TableView<StudyProfile> table = new TableView<>();
		table.setItems(list);
		//limit the number of rows to allow space for buttons below the table
		GridPane.setRowSpan(table, 20);
		table.getColumns().addAll(colList);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		GridPane.setHgrow(table, Priority.ALWAYS);
		GridPane.setVgrow(table, Priority.ALWAYS);

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
			row.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				if (event.getButton() == MouseButton.PRIMARY) {
					if (this.isNavOpen) {
						closeDrawer.fire();
					}
					if (this.showNotification.getTranslateY() == 0) {
						TranslateTransition closeNot = new TranslateTransition(new Duration(173),
								notifications);
						closeNot.setToY(-(notifications.getHeight() + this.navShadowRadius + 56
								+ 17));
						closeNot.play();
					}
					if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
							&& event.getClickCount() == 2) {
						try {
							MainController.ui.studyProfileDetails(row.getItem());
							this.main();
						} catch (IOException e1) {
							UiManager.reportError("Unable to open View file");
						}
					}
				}
			});
			return row;
		});

		this.mainContent.addRow(2, table);
		GridPane.setColumnSpan(table, GridPane.REMAINING);
		this.mainContent.getStyleClass().add("list-item");
	}

	/**
	 * Display the Modules pane.
	 */
	public void loadModules() {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.title.setText("Modules");
		// Columns:
		TableColumn<Module, String> codeColumn = new TableColumn<>("Module code");
		codeColumn.setCellValueFactory(new PropertyValueFactory<>("moduleCode"));

		TableColumn<Module, String> nameColumn = new TableColumn<>("Module name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Module, Number> timeSpent = new TableColumn<>("Time spent");
		timeSpent.setCellValueFactory(new PropertyValueFactory<Module, Number>("timeSpent") {
			@Override
			public ObservableValue<Number> call(
					TableColumn.CellDataFeatures<Module, Number> param) {
				return new SimpleIntegerProperty(
						MainController.getSpc().getPlanner().getTimeSpent(param.getValue()));
			}
		});
		timeSpent.setStyle("-fx-alignment: CENTER-RIGHT;");

		ArrayList<TableColumn<Module, ?>> colList = new ArrayList<>(
				Arrays.asList(codeColumn, nameColumn, timeSpent));

		ObservableList<Module> list = FXCollections.observableArrayList(
				MainController.getSpc().getPlanner().getCurrentStudyProfile().getModules());

		// Create a table:
		TableView<Module> table = new TableView<>();
		table.setItems(list);
		//limit the number of rows to allow space for buttons below the table
		GridPane.setRowSpan(table, 20);
		table.getColumns().addAll(colList);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		GridPane.setHgrow(table, Priority.ALWAYS);
		GridPane.setVgrow(table, Priority.ALWAYS);
		GridPane.setColumnSpan(table, GridPane.REMAINING);

		// Set click event:
		table.setRowFactory(e -> {
			TableRow<Module> row = new TableRow<>();
			row.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				if (event.getButton() == MouseButton.PRIMARY) {
					if (this.isNavOpen) {
						closeDrawer.fire();
					}
					if (this.showNotification.getTranslateY() == 0) {
						TranslateTransition closeNot = new TranslateTransition(new Duration(173),
								notifications);
						closeNot.setToY(-(notifications.getHeight() + this.navShadowRadius + 56
								+ 17));
						closeNot.play();
					}

					if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
							&& event.getClickCount() == 2) {
						this.loadModule(row.getItem(), this.current, null);
					}
				}
			});
			return row;
		});
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
		this.title.setText(module.getModuleCode() + " " + module.getName());
		// =================

		// Create a back button:
		this.backButton(previousWindow, previous);
		// =================

		// Create a details pane:
		VBox detailsBox = new VBox(5);
		Label details = new Label(module.getDetails().getAsString());
		details.setWrapText(true);
		detailsBox.getChildren().addAll(new Label("Organised by: " + module.getOrganiser()),
				details);
		GridPane.setVgrow(detailsBox, Priority.SOMETIMES);
		GridPane.setHgrow(detailsBox, Priority.ALWAYS);
		GridPane.setColumnSpan(detailsBox, GridPane.REMAINING);

		mainContent.addRow(2, detailsBox);

		// Assignments:
		TableColumn<Assignment, String> nameColumn = new TableColumn<>("Assignment");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Assignment, String> deadlineColumn = new TableColumn<>("Date");
		deadlineColumn.setCellValueFactory(
				new PropertyValueFactory<Assignment, String>("deadlineString") {
					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Assignment, String> param) {

						SimpleStringProperty value = new SimpleStringProperty();
						// TODO - find a way to get rid of this instanceof
						if (param.getValue() instanceof Coursework) {
							Coursework cw = (Coursework) param.getValue();
							value.setValue(cw.getDeadlineString());
						} else if (param.getValue() instanceof Exam) {
							Exam exam = (Exam) param.getValue();
							value.setValue(exam.getTimeSlot().getDateString());
						}
						return value;

					}
				});
		deadlineColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		TableColumn<Assignment, Integer> weightingColumn = new TableColumn<>("Weighting");
		weightingColumn.setCellValueFactory(new PropertyValueFactory<>("weighting"));
		weightingColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		ArrayList<TableColumn<Assignment, ?>> colList = new ArrayList<>(
				Arrays.asList(nameColumn, deadlineColumn, weightingColumn));

		ObservableList<Assignment> list = FXCollections
				.observableArrayList(module.getAssignments());

		// Create a moduleContent:
		TableView<Assignment> moduleContent = new TableView<>();
		moduleContent.setItems(list);
		moduleContent.getColumns().addAll(colList);
		moduleContent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		GridPane.setHgrow(moduleContent, Priority.ALWAYS);
		GridPane.setVgrow(moduleContent, Priority.ALWAYS);


		// Set click event:
		moduleContent.setRowFactory(e -> {
			TableRow<Assignment> row = new TableRow<>();
			row.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
						&& event.getClickCount() == 2) {
					this.loadAssignment(row.getItem(), Window.EMPTY, module);
				}
			});
			return row;
		});
		this.mainContent.addRow(3, moduleContent);
		GridPane.setColumnSpan(moduleContent, GridPane.REMAINING);
	}

	/**
	 * This method will create a window that will prompt the user for a username and host name. If a
	 * name is not entered then a username is randomly chosen. When the submit button is pressed a
	 * new interface will be loaded which is the chat window.
	 */
	public void obtainUserInformation() {

		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.title.setText("Chat");
		this.mainContent.getChildren().addAll(firstPane);
		createFirstWindow();
		submitButtonAction();
	}

	/**
	 * This method will create the peer to peer chat window. It will load the text area where the
	 * user will see messages from peers and a place for the user to send his or her own message.
	 */
	public void loadChatWindow() {

		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.title.setText("Chat");
		this.mainContent.getChildren().addAll(mainPane);
		createUserMessagePane();
		createMainPane();
		sendButtonAction();
	}

	/**
	 * This will load the msg_area which is where the user will see messages from other users and
	 * him or herself. This will also load the text field where the user will be able to send his or
	 * her own message to peers.
	 */
	public void createMainPane() {
		mainPane.setCenter(msgArea);
		mainPane.setBottom(userMessagePane);
	}

	/**
	 * This will set the message area to uneditable and set the size for all the buttons. The method
	 * will also create padding between the text area, the message area, and the send button.
	 */
	public void createUserMessagePane() {
		msgArea.setEditable(false);
		tfMessageToSend.setPrefWidth(800);
		userMessagePane.setPadding(new Insets(10, 10, 10, 10));
		sendButton.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, null, null)));
		spacingBox.setPadding(new Insets(0, 5, 0, 5));
		userMessagePane.add(tfMessageToSend, 0, 0);
		userMessagePane.add(spacingBox, 1, 0);
		userMessagePane.add(sendButton, 2, 0);
		sendButton.setMinWidth(100);
	}

	/**
	 * This will load all the textfields,labels and buttons for the window that prompts the user for
	 * his or her username and host name.
	 */
	public void createFirstWindow() {
		firstPane.add(name, 0, 0);
		firstPane.add(tfName, 1, 0);
		firstPane.add(host, 0, 1);
		firstPane.add(tfHost, 1, 1);
		firstPane.add(submitButton, 1, 2);
	}

	/**
	 * This will take in the action of when the submit button is pressed. The submit button is for
	 * the chat window where the user inputs his or her information. If the user does not enter a
	 * username/hostname, an error will pop up notifying them to enter those values. Then at the
	 * very end the chat window will be loaded.
	 */
	public void submitButtonAction() {
		submitButton.setOnAction((ActionEvent exception1) -> {
			if ((tfName.getText() != null && !(tfName.getText().equals("")))
					&& (tfHost.getText() != null && !(tfHost.getText().equals("")))) {
				userName = tfName.getText();
				hostName = tfHost.getText();
				loadChatWindow();
			} else {
				UiManager.displayError("Username and host are required.");
			}
		});
	}

	/**
	 *  This will take in the action of when the send button is pressed. If a user sends a message,
	 *  the line of text will append to the chat log so the user can see what they sent. It follows
	 *  the format of USER: sentence.
	 *  The text box with the user input will be set back to blank after a message is sent.
	 */
	public void sendButtonAction() {
		sendButton.setOnAction((ActionEvent exception1) -> {
			if (!(tfMessageToSend.getText().equals(""))) {
				msgArea.appendText(userName + ": " + tfMessageToSend.getText() + "\n");
				tfMessageToSend.setText("");
			}
		});
	}

	/**
	 * This will set the username for the peer-to-peer chat.
	 */
	public void setUserName(String user) {
		userName = user;
	}

	/**
	 * This will set the PortNumber for the peer-to-peer chat.
	 */
	public void setPortNumber(int userPort) {
		portNumber = userPort;
	}

	/**
	 * Returns the currently registered user's chat ID.
	 *
	 * @return the currently registered user's chat ID.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Returns the current host name registered to the chat user.
	 *
	 * @return the current host name registered to the chat user.
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * Display the Assignment pane.
	 */
	public void loadAssignment(Assignment assignment, Window previousWindow, ModelEntity previous) {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		// =================

		// Create a back button:
		this.backButton(previousWindow, previous);

		// Display modules:
		Label assignments = new Label(assignment.getName());
		assignments.getStyleClass().add("title");
		this.mainContent.addRow(1, assignments);
		GridPane.setColumnSpan(assignments, GridPane.REMAINING);

		// Ganttish chart button:
		Button gantt = new Button("Generate a Ganttish Diagram");
		gantt.setOnAction(e -> showGantt(assignment, previousWindow, previous));
		GridPane.setHalignment(gantt, HPos.RIGHT);
		GridPane.setColumnSpan(gantt, GridPane.REMAINING);
		this.mainContent.add(gantt, 0, 1);

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

		mainContent.addRow(2, detailsBox);
		GridPane.setColumnSpan(detailsBox, GridPane.REMAINING);

		// content pane:
		GridPane content = new GridPane();
		GridPane.setVgrow(content, Priority.ALWAYS);
		GridPane.setHgrow(content, Priority.ALWAYS);
		GridPane.setColumnSpan(content, GridPane.REMAINING);
		content.setVgap(5);

		// Requirements columns:
		TableColumn<Requirement, String> reqNameColumn = new TableColumn<>("Requirement");
		reqNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Requirement, Integer> remainingColumn = new TableColumn<>("Remaining");
		remainingColumn.setCellValueFactory(new PropertyValueFactory<>("remainingQuantity"));
		remainingColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		TableColumn<Requirement, QuantityType> typeColumn = new TableColumn<>("Quantity type");
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("quantityType"));

		ArrayList<TableColumn<Requirement, ?>> colList = new ArrayList<>(
				Arrays.asList(reqNameColumn, remainingColumn, typeColumn));

		ObservableList<Requirement> requirementList = FXCollections
				.observableArrayList(assignment.getRequirements());

		// Create Requirements table:
		TableView<Requirement> requirements = new TableView<>();
		requirements.setItems(requirementList);
		requirements.getColumns().addAll(colList);
		requirements.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		GridPane.setHgrow(requirements, Priority.ALWAYS);
		GridPane.setVgrow(requirements, Priority.ALWAYS);

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

		// Buttons:
		Button addNewReq = new Button("Add a new requirement");

		Button deleteReq = new Button("Remove");
		deleteReq.setDisable(true);

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
				UiManager.reportError("Unable to open View file");
			} catch (Exception e1) {
				UiManager.reportError(e1.getMessage());
			}
		});

		deleteReq.setOnAction(e -> {
			if (UiManager.confirm("Are you sure you want to remove this requirement?")) {
				Requirement rr = requirements.getSelectionModel().getSelectedItem();
				requirementList.remove(rr);
				assignment.removeRequirement(rr);
				requirements.refresh();
			}
		});

		actionsReq.getChildren().addAll(addNewReq, deleteReq);

		content.add(actionsReq, 0, 1);

		// Tasks columns:
		TableColumn<Task, String> nameColumn = new TableColumn<>("Task");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Task, String> deadlineColumn = new TableColumn<>("Deadline");
		deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
		deadlineColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		TableColumn<Task, BooleanProperty> canComplete = new TableColumn<>("Can be completed?");
		canComplete.setCellValueFactory(new PropertyValueFactory<>("possibleToComplete"));
		canComplete.setStyle("-fx-alignment: CENTER-RIGHT;");

		ArrayList<TableColumn<Task, ?>> taskColList = new ArrayList<>(
				Arrays.asList(nameColumn, deadlineColumn, canComplete));

		ObservableList<Task> list = FXCollections.observableArrayList(assignment.getTasks());

		// Create Tasks table:
		TableView<Task> tasks = new TableView<>();
		tasks.setItems(list);
		tasks.getColumns().addAll(taskColList);
		tasks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		GridPane.setHgrow(tasks, Priority.ALWAYS);
		GridPane.setVgrow(tasks, Priority.ALWAYS);

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
						UiManager.reportError("Unable to open view file");
					}
				}
			});
			return row;
		});

		content.addColumn(1, tasks);

		// Actions toolbar:
		HBox actionsTask = new HBox();
		GridPane.setHgrow(actionsTask, Priority.ALWAYS);
		actionsTask.setSpacing(5);
		actionsTask.setPadding(new Insets(5, 5, 10, 0));

		// Buttons:
		Button addNew = null;
		addNew = new Button("Add a new task");

		Button check = new Button("Toggle complete");
		check.getStyleClass().add("set-button");
		check.setDisable(true);

		Button delete = new Button("Remove");
		delete.setDisable(true);

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
				UiManager.reportError("Unable to open View file");
			} catch (Exception e1) {
				UiManager.reportError(e1.getMessage());
			}
		});

		check.setOnAction(e -> {
			tasks.getSelectionModel().getSelectedItem().toggleComplete();
			tasks.refresh();
		});

		delete.setOnAction(e -> {
			if (UiManager.confirm("Are you sure you want to remove this task?")) {
				Task tt = tasks.getSelectionModel().getSelectedItem();
				list.remove(tt);
				assignment.removeTask(tt);
				this.updateMenu();
			}
		});

		// Gap:
		HBox gap = new HBox();
		HBox.setHgrow(gap, Priority.ALWAYS);

		actionsTask.getChildren().addAll(addNew, gap, check, delete);

		content.add(actionsTask, 1, 1);

		this.mainContent.addRow(3, content);
		GridPane.setColumnSpan(content, GridPane.REMAINING);
	}

	/**
	 * Handles the 'Mark all as read' button event.
	 */
	public void handleMarkAll() {
		Notification[] nots = MainController.getSpc().getPlanner().getUnreadNotifications();
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
	 * The identifier of the notification which was clicked.
	 */
	public void handleRead(int id) {
		// Get notification:
		int idInList = MainController.getSpc().getPlanner().getNotifications().length - 1 - id;
		Notification not = MainController.getSpc().getPlanner().getNotifications()[idInList];

		// If not read:
		if (!not.isRead()) {
			// Mark notification as read:
			not.read();

			// Swap styles:
			this.notificationList.getChildren().get(id).getStyleClass().remove("unread-item");
			if (MainController.getSpc().getPlanner().getUnreadNotifications().length <= 0) {
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
			UiManager.reportSuccess("File imported successfully!");
		}
		this.main();
	}

	/**
	 * Handles the 'Settings' event.
	 */
	public void showSettings() {
		initialLoad = true; // Required so the notifications don't appear.
		MainController.showSettings();
	}

	/**
	 * Handles the 'Help' event.
	 */
	public void openBrowser() {
		MainController.openBrowser();
	}

	/**
	 * Handles 'Export Calendar' event.
	 */
	public void exportCalendar() {
		MainController.exportCalendar();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.prepareAnimations();
		this.isNavOpen = false;

		// Set shadows
		notifications.setEffect(notifShadow);
		navList.setEffect(navShadow);

		// Set button actions:
		this.closeDrawer.setOnAction(e -> openMenu.fire());
		this.showDash.setOnAction(e -> this.main(Window.DASHBOARD));
		this.studyProfiles.setOnAction(e -> this.main(Window.PROFILES));
		this.modules.setOnAction(e -> this.main(Window.MODULES));
		this.milestones.setOnAction(e -> this.main(Window.MILESTONES));
		this.calendar.setOnAction(e -> this.main(Window.CALENDAR));
		this.chat.setOnAction(e -> this.main(Window.CHAT));

		// Set nav to close when clicking outside of it
		this.mainContent.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				if (this.showNotification.getTranslateY() == 0) {
					TranslateTransition closeNot = new TranslateTransition(new Duration(173),
						notifications);
					closeNot.setToY(-(notifications.getHeight() + this.navShadowRadius + 56 + 17));
					closeNot.play();
				}
				if (this.isNavOpen) {
					this.openMenu.fire();
				}
			}
		});

		/*
		 * Welcome text. Displays the appropriate welcoming message depending on if the user
		 * is new or a returning user. Also takes into account if the user entered their
		 * name or not during account creation.
		 */
		if (MainController.getSpc().getPlanner().getCurrentStudyProfile() != null) {
			if ((MainController.getSpc().getPlanner().getUserName()).isEmpty()) {
				this.welcome = new Label("Welcome back!");
			} else {
				this.welcome = new Label("Welcome back, "
						+ MainController.getSpc().getPlanner().getUserName() + "!");
			}
		} else {
			if ((MainController.getSpc().getPlanner().getUserName()).isEmpty()) {
				this.welcome = new Label("Welcome!");
			} else {
				this.welcome = new Label(
						"Welcome " + MainController.getSpc().getPlanner().getUserName() + "!");
			}
		}
		this.welcome.setPadding(new Insets(10, 15, 10, 15));
		this.topBox.getChildren().add(this.welcome);

		this.mainContent.setVgap(10);
		this.mainContent.setPadding(new Insets(15));

		// Render dashboard:
		this.main(Window.DASHBOARD);
	}

	/**
	 * Prepare notifications.
	 */
	private void updateNotifications() {
		MainController.getSpc().checkForNotifications();

		// Set notification button style:
		if (MainController.getSpc().getPlanner().getUnreadNotifications().length > 0) {
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
		Notification[] pendingNotifs =
				MainController.getSpc().getPlanner().getNotifications();
		for (int i = pendingNotifs.length - 1; i >= 0; i--) {
			GridPane pane = new GridPane();

			// Check if has a link or is unread:
			if (pendingNotifs[i].getLink() != null || !pendingNotifs[i].isRead()) {
				pane.setCursor(Cursor.HAND);
				pane.setId(Integer.toString(pendingNotifs.length - i - 1));
				pane.setOnMouseClicked(e ->
					this.handleRead(Integer.parseInt(pane.getId()))
				);
				// Check if unread:
				if (!pendingNotifs[i].isRead()) {
					pane.getStyleClass().add("unread-item");
				}
			}

			// Create labels:
			Label titleLabel = new Label(pendingNotifs[i].getTitle());
			titleLabel.getStyleClass().add("notificationItem-title");
			titleLabel.setMaxWidth(250.0);

			Label details = (pendingNotifs[i].getDetails() != null)
					? new Label(pendingNotifs[i].getDetailsAsString())
					: new Label();
			details.getStyleClass().add("notificationItem-details");
			details.setMaxWidth(250.0);

			String dateFormatted = pendingNotifs[i].getDateTime().get(Calendar.DAY_OF_MONTH) + " "
					+ pendingNotifs[i].getDateTime().getDisplayName(Calendar.MONTH, Calendar.LONG,
							Locale.getDefault())
					+ " at " + pendingNotifs[i].getDateTime().get(Calendar.HOUR) + " "
					+ pendingNotifs[i].getDateTime().getDisplayName(Calendar.AM_PM, Calendar.LONG,
							Locale.getDefault());
			Label date = new Label(dateFormatted);
			date.getStyleClass().addAll("notificationItem-date");
			GridPane.setHalignment(date, HPos.RIGHT);
			GridPane.setHgrow(date, Priority.ALWAYS);

			pane.addRow(1, titleLabel);
			pane.addRow(2, details);
			pane.addRow(3, date);
			pane.addRow(4, new Separator(Orientation.HORIZONTAL));
			this.notificationList.addRow(pendingNotifs.length - i - 1, pane);
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
		if (MainController.getSpc().getPlanner().getCurrentStudyProfile() == null) {
			this.addActivity.setDisable(true);
			this.milestones.setDisable(true);
			this.studyProfiles.setDisable(true);
			this.modules.setDisable(true);
			this.calendar.setDisable(true);
		} else {
			if (MainController.getSpc().getCurrentTasks().size() <= 0) {
				this.addActivity.setDisable(true);
				this.milestones.setDisable(true);
			}

			if (MainController.getSpc().getPlanner().getCurrentStudyProfile()
					.getModules().length <= 0) {
				this.modules.setDisable(true);
			}
		}
	}

	/**
	 * Creates a back button.
	 */
	public void backButton(Window previousWindow, ModelEntity previous) {
		if (previous != null || previousWindow != Window.EMPTY) {
			Button back = new Button();
			back.getStyleClass().addAll("button-image", "back-button");

			if (previous == null && previousWindow != Window.EMPTY) {
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
		TranslateTransition openNav = new TranslateTransition(new Duration(222), navList);
		openNav.setToX(0);
		TranslateTransition closeNav = new TranslateTransition(new Duration(173), navList);
		openMenu.setOnAction((ActionEvent e1) -> {
			this.isNavOpen = !isNavOpen;
			if (navList.getTranslateX() != 0) {
				openNav.play();
				this.isNavOpen = true;
			} else {
				closeNav.setToX(
						-(navList.getWidth() + this.navShadowRadius + this.navShadowOffset));
				closeNav.play();
			}
		});

		TranslateTransition openNot = new TranslateTransition(new Duration(222), notifications);
		openNot.setToY(17);
		TranslateTransition closeNot = new TranslateTransition(new Duration(173), notifications);

		showNotification.setOnAction((ActionEvent e1) -> {
			if (notifications.getTranslateY() != 17) {
				openNot.play();
			} else {
				closeNot.setToY(-(notifications.getHeight() + this.navShadowRadius + 56 + 17));
				closeNot.play();
			}
		});
	}

	/**
	 * RowFactory for a TableView of Requirement.
	 *
	 * @param e1
	 * TableView that contains the RowFactory.
	 *
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
					UiManager.reportError("Unable to open view file");
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

	/**
	 * Displays a GanttishDiagram window for the given Assignment.
	 *
	 * @param assignment
	 * Assignment for which to generate the GanttishDiagram.
	 */
	public void showGantt(Assignment assignment, Window previousWindow, ModelEntity previous) {
		stage = new Stage();
		mainContent.getChildren().remove(1, mainContent.getChildren().size());
		topBox.getChildren().clear();
		title.setText(assignment.getName() + " Gantt Diagram");

		// Layout:
		VBox layout = new VBox();
		layout.setSpacing(10);
		layout.setPadding(new Insets(15));
		layout.getStylesheets().add("/edu/wright/cs/raiderplanner/content/stylesheet.css");
		// =================

		// Nav bar:
		HBox nav = new HBox();
		nav.setSpacing(15.0);
		// =================
		HBox xx = new HBox();
		HBox.setHgrow(xx, Priority.ALWAYS);
		// =================

		// Buttons:
		Button back = new Button();
		back.getStyleClass().addAll("button-image", "back-button");
		back.setOnAction(e -> {
			this.title.setText(assignment.getName());
			this.loadAssignment(assignment, previousWindow, previous);
		});
		Button save = new Button("Save");
		save.setOnAction(e -> {
			String path = MainController.ui.saveFileDialog(stage);
			GanttishDiagram.createGanttishDiagram(MainController.getSpc().getPlanner(), assignment,
					path);
		});
		// =================

		nav.getChildren().addAll(back, xx, save);

		// Content:
		BufferedImage gantt = GanttishDiagram
				.createGanttishDiagram(MainController.getSpc().getPlanner(), assignment);
		Image image = SwingFXUtils.toFXImage(gantt, null);
		Pane content = new Pane();
		VBox.setVgrow(content, Priority.ALWAYS);
		content.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(
						BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
		// =================

		layout.getChildren().addAll(nav, content);
		layout.setMinSize(333, 555);
		GridPane.setColumnSpan(layout, GridPane.REMAINING);
		// Set the scene:
		mainContent.getChildren().add(layout);
	}

}
