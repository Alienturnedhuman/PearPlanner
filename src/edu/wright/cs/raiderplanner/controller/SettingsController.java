/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar, Amila Dias
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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
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
 * Actions associated with the settings menu and its items.
 * 2/1/2018 - Template Created
 * @author Clayton D. Terrill
 */

public class SettingsController implements Initializable {

	/**
	 * Initializes switch names and other buttons.
	 * Represent states that the settings menu can be in.
	 * May be assigned to a button in the initialize method and
	 * what it does may be applied in the main method's switch statement.
	 */
	public enum Window {
		EMPTY, INFO, GENERAL, ACCOUNT, THEME, NOTIFICATIONS
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
	//private DropShadow notifShadow = new DropShadow(screenAverage * 0.02, 0, 0.009, Color.BLACK);
	private DropShadow moduleDefaultShadow = new DropShadow(screenAverage * 0.005, 0, 0,
			Color.BLACK);
	private DropShadow moduleHoverShadow = new DropShadow(screenAverage * 0.02, 0, 0, Color.BLACK);
	private InnerShadow modulePressedShadow = new InnerShadow(screenAverage * 0.017, 0, 0,
			Color.BLACK);

	// Labels:
	private Label welcome;
	@FXML
	private Label title;

	// Buttons:
	@FXML
	private Button openMenu;
	@FXML
	private Button generalBtn;
	@FXML
	private Button accountBtn;
	@FXML
	private Button themeBtn;
	@FXML
	private Button notificationsBtn;
	@FXML
	private Button closeDrawer;

	// Panes:
	@FXML
	private AnchorPane navList;
	@FXML
	private GridPane mainContent;
	@FXML
	private HBox topBox;
	@FXML

	//chat variables
	private final BorderPane mainPane = new BorderPane();

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

		initialLoad = false;

		//When user chooses different option in menu
		//		calendarOpen changes to monitor status within main window.
		switch (this.current) {
		case INFO: {
			if (MainController.getSpc().getPlanner().getCurrentStudyProfile() != null) {
				this.loadInfo();
			}
			break;
		}
		case GENERAL: {
			this.loadGeneral();
			break;
		}
		case ACCOUNT: {
			this.loadAccount();
			break;
		}
		case THEME: {
			this.loadTheme();
			break;
		}
		case NOTIFICATIONS: {
			this.loadNotification();
			break;
		}
		default:
			break;
		}
	}

	/**
	 * Display the Study INFO pane. Loads at settings startup.
	 * This also initializes aspects of the settings scene.
	 */
	public void loadInfo() {
		// set ToolTips

		StudyProfile profile = MainController.getSpc().getPlanner().getCurrentStudyProfile();

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
						//module.open(this.current);
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
		ScrollPane moduleBox = new ScrollPane();
		moduleBox.setContent(modulesPane);
		moduleBox.setStyle("-fx-background-color: transparent");
		moduleBox.setFitToHeight(true);
		moduleBox.setFitToWidth(true);
		GridPane.setColumnSpan(moduleBox, GridPane.REMAINING);
		this.mainContent.addRow(2, moduleBox);
		 */
		
		// Create a details pane:
	}

	/**
	 * Display the Study INFO pane.
	 * NOT IMPLEMENTED
	 */
	public void loadGeneral() {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.welcome.setText("GENERAL");
		this.topBox.getChildren().add(this.welcome);
		this.title.setText("General Settings");
		
		/*
		VBox detailsBox = new VBox(5);
		Label details = new Label("Testing details label");
		details.setWrapText(true);
		detailsBox.getChildren().addAll(new Label("The top line is right here."),
				new Label("Second sucks"),
				new Label("Third time is a charm."),
				new Label("Shake my head, this is fourth."),
				new Label("This is the last one, I swear."),
				details);
		GridPane.setVgrow(detailsBox, Priority.SOMETIMES);
		GridPane.setHgrow(detailsBox, Priority.ALWAYS);

		this.mainContent.addRow(2, detailsBox);
		GridPane.setColumnSpan(detailsBox, GridPane.REMAINING);
		*/
	}

	/**
	 * Display the Study INFO pane.
	 * NOT IMPLEMENTED
	 */
	public void loadAccount() {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.welcome.setText("ACCOUNT");
		this.topBox.getChildren().add(this.welcome);
		this.title.setText("Account Settings");
	}

	/**
	 * Display the Study INFO pane.
	 * NOT IMPLEMENTED
	 */
	public void loadTheme() {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.welcome.setText("THEME");
		this.topBox.getChildren().add(this.welcome);
		this.title.setText("Theme Settings");
	}

	/**
	 * Display the Study INFO pane.
	 * NOT IMPLEMENTED
	 */
	public void loadNotification() {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.welcome.setText("NOTIFICATION");
		this.topBox.getChildren().add(this.welcome);
		this.title.setText("Notification Settings");
	}

	/**
	 * Handles the 'Back' Event
	 * Author: Clayton D. Terrill  1/29/2018
	 */
	public void goBack() {
		initialLoad = true; //Required so class may be reused.
		MainController.showMain();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.prepareAnimations();
		this.isNavOpen = false;

		// Set shadows
		navList.setEffect(navShadow);

		// Set button actions:
		this.closeDrawer.setOnAction(e -> openMenu.fire());
		this.generalBtn.setOnAction(e -> this.main(Window.GENERAL));
		this.accountBtn.setOnAction(e -> this.main(Window.ACCOUNT));
		this.themeBtn.setOnAction(e -> this.main(Window.THEME));
		this.notificationsBtn.setOnAction(e -> this.main(Window.NOTIFICATIONS));

		// Set nav to close when clicking outside of it
		this.mainContent.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				if (this.isNavOpen) {
					this.openMenu.fire();
				}
			}
		});

		//  text:
		this.welcome = new Label("RaiderPlanner");
		this.welcome.setFont(Font.font("Ariel", FontWeight.BOLD , 42));
		Label versionNo = new Label("Version 1.0.1\nCopyright © 2017");
		versionNo.setFont(Font.font("Ariel", 12));
		versionNo.setTextFill(Color.GRAY);
		versionNo.setTextAlignment(TextAlignment.CENTER);
		Label details = new Label("\"The best study planner since the Gantt Diagram\"");
		details.setFont(Font.font("Ariel", FontPosture.ITALIC , 24));
		Label stayOnTrack = new Label("Stay on track.");
		stayOnTrack.setFont(Font.font("Ariel", FontWeight.BOLD, 18));
		Label summary = new Label("Life gets complicated, and keeping track of homework and exam\n"
				+ "dates is not the first thing on your mind. RaiderPlanner will be "
				+ "there to help you out.");
		summary.setFont(Font.font("Ariel", 18));
		summary.setTextAlignment(TextAlignment.CENTER);
		Label credits = new Label("Created By:\n"
				+ "Roberto Sánchez\n"
				+ "Wright State University's CEG3120 class\n\n"
				+ "Based on the 'PearPlanner'\n"
				+ "Created By: \n"
				+ "Benjamin Dickson\n"
				+ "Andrew Odintsov\n"
				+ "Zilvinas Ceikauskas\n"
				+ "Bijan Ghasemi Afshar\n");
		credits.setFont(Font.font("Ariel", 14));
		credits.setTextAlignment(TextAlignment.CENTER);

		VBox detailsBox = new VBox(5);
		details.setWrapText(true);
		detailsBox.getChildren().addAll(this.welcome,
				versionNo,
				details,
				stayOnTrack,
				summary,
				credits);
		detailsBox.setAlignment(Pos.TOP_CENTER);
		GridPane.setVgrow(detailsBox, Priority.SOMETIMES);
		GridPane.setHgrow(detailsBox, Priority.ALWAYS);
		GridPane.setColumnSpan(detailsBox, GridPane.REMAINING);

		this.mainContent.addRow(2, detailsBox);
		this.mainContent.setVgap(10);
		this.mainContent.setPadding(new Insets(15));
		this.mainContent.setAlignment(Pos.CENTER);

		// Render INFO initially:
		this.main(Window.INFO);
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
	}

	/**
	 * RowFactory for a TableView of Requirement.
	 *
	 * @param e1
	 *			TableView that contains the RowFactory.
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

}
