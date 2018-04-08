/*
 * Copyright (C) 2018 - Clayton D. Terrill
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

import edu.wright.cs.raiderplanner.model.Settings;

import javafx.animation.TranslateTransition;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Actions associated with the settings menu and its items.
 * Settings menu implemented in correspondence with Issue #238.
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
		EMPTY, ABOUT, GENERAL, ACCOUNT, THEME, NOTIFICATIONS
	}

	private Window current;
	private boolean isNavOpen;

	// Screen size:
	private double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
	private double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
	private double screenAverage = (screenWidth + screenHeight) / 2;

	// Shadows:
	private int navShadowRadius = (int) (screenAverage * 0.03);
	private int navShadowOffset = (int) (screenAverage * 0.01);
	private DropShadow navShadow = new DropShadow(navShadowRadius, navShadowOffset, 0, Color.BLACK);
	// private DropShadow notifShadow = new DropShadow(screenAverage * 0.02, 0, 0.009, Color.BLACK);

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
	private Button aboutBtn;
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

	// chat variables
	private final BorderPane mainPane = new BorderPane();

	private static FileChooser.ExtensionFilter datExtension =
			new FileChooser.ExtensionFilter("dat file", "*.dat");
	private static File savesFolder = new File("./saves");

	Settings settings = new Settings();

	/**
	 * Sets this.current to equal passed variable and calls this.main().
	 */
	public void main(Window wind) {
		this.current = wind;
		this.main();
	}

	/**
	 * Main method containing switch statements.
	 * Switch statements detect what state the app is in.
	 */
	public void main() {
		if (isNavOpen) {
			openMenu.fire();
		}

		switch (this.current) {
		case ABOUT: {
			loadAbout();
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
	 * Display the About menu.
	 */
	public void loadAbout() {
		// set ToolTips
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.title.setText("About");

		// Text Labels:
		Label appName = new Label("RaiderPlanner");
		appName.setFont(Font.font("Ariel", FontWeight.BOLD , 42));

		Label versionNo = new Label("Version 0.0.0\nCopyright © 2017");
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
		detailsBox.getChildren().addAll(
				appName,
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
	}

	/**
	 * Display the general settings.
	 * TODO - Implement more general settings.
	 */
	public void loadGeneral() {
		// Update main pane:
		this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());
		this.topBox.getChildren().clear();
		this.topBox.getChildren().add(this.welcome);
		this.title.setText("General Settings");

		/* Controls for the startup preference */
		Label launchSetting = new Label("Open on startup:");
		launchSetting.setUnderline(true);
		ToggleGroup group = new ToggleGroup();
		RadioButton defaultStartup = new RadioButton("Open Start Menu\t");
		defaultStartup.setToggleGroup(group);
		defaultStartup.setSelected(true);
		RadioButton accountStartup = new RadioButton("Open User Account\t");
		accountStartup.setToggleGroup(group);
		Label fileName = new Label("");
		fileName.setTextFill(Color.GRAY);
		Button browseAccounts = new Button("\tBrowse\t");
		//browseAccounts.setDisable(true);
		VBox launchBox = new VBox(2);
		launchBox.getChildren().addAll(
				launchSetting,
				defaultStartup,
				accountStartup,
				browseAccounts,
				fileName);
		launchBox.setAlignment(Pos.TOP_CENTER);
		GridPane.setVgrow(launchBox, Priority.SOMETIMES);
		GridPane.setHgrow(launchBox, Priority.ALWAYS);
		GridPane.setColumnSpan(launchBox, GridPane.REMAINING);
		this.mainContent.add(launchBox, 0, 1);

		/* The Revert and Save Buttons at the bottom */
		Button revertButton = new Button("\tRevert\t");
		Button saveButton = new Button("\tSave\t\t");
		HBox saveBox = new HBox(2);
		saveBox.getChildren().addAll(
				revertButton,
				saveButton);
		saveBox.setAlignment(Pos.BOTTOM_CENTER);
		GridPane.setVgrow(saveBox, Priority.SOMETIMES);
		GridPane.setHgrow(saveBox, Priority.ALWAYS);
		GridPane.setColumnSpan(saveBox, GridPane.REMAINING);
		this.mainContent.addRow(2, saveBox);

		// Load control contents at first
		fillGeneralControls(fileName, accountStartup,
				browseAccounts, revertButton, saveButton);

		/* Button Events */
		defaultStartup.setOnAction(e ->
				this.toggleAccountStartup(accountStartup, browseAccounts,
						fileName, revertButton, saveButton));
		accountStartup.setOnAction(e ->
				this.toggleAccountStartup(accountStartup, browseAccounts,
						fileName, revertButton, saveButton));
		browseAccounts.setOnAction(e ->
				this.browseAccountsEvent(fileName, revertButton, saveButton));
		saveButton.setOnAction(e ->
				this.saveSettings(revertButton, saveButton));
		revertButton.setOnAction(e ->
				this.fillGeneralControls(fileName, accountStartup,
						browseAccounts, revertButton, saveButton));
	}

	/**
	 * Saves the settings.
	 * @param revertButtonTemp - Button disabled since current settings match saved.
	 * @param saveButtonTemp - Button disabled since current settings match saved.
	 */
	public void saveSettings(Button revertButtonTemp, Button saveButtonTemp) {
		settings.saveSettings();
		revertButtonTemp.setDisable(true);
		saveButtonTemp.setDisable(true);
	}

	/**
	 * Fills the general controls with the saved setting properties.
	 *
	 * @param fileNameTemp - Label containing account file path.
	 * @param accountStartupTemp - RadioButton to determine if account startup is used or not.
	 * @param browseAccountsTemp - Button to enable or disable.
	 * @param revertButtonTemp - Button disabled since current settings match saved.
	 * @param saveButtonTemp - Button disabled since current settings match saved.
	 */
	public void fillGeneralControls(Label fileNameTemp,
			RadioButton accountStartupTemp, Button browseAccountsTemp,
			Button revertButtonTemp, Button saveButtonTemp) {

		settings.loadSettings();
		fileNameTemp.setText(settings.getDefaultFilePath());
		fileNameTemp.setVisible(settings.getAccountStartup());
		accountStartupTemp.setSelected(settings.getAccountStartup());
		browseAccountsTemp.setDisable(!accountStartupTemp.isSelected());
		revertButtonTemp.setDisable(true);
		saveButtonTemp.setDisable(true);
	}

	/**
	 * Toggles the setting property for account startup.
	 *
	 * @param accountStartupTemp - RadioButton that determines startup.
	 * @param browseAccountsTemp - Button to disable.
	 * @param fileNameTemp - Label to hide.
	 * @param revertButtonTemp - Button enabled if original setting changed.
	 * @param saveButtonTemp - Button enabled if original setting changed.
	 */
	public void toggleAccountStartup(RadioButton accountStartupTemp,
			Button browseAccountsTemp, Label fileNameTemp,
			Button revertButtonTemp, Button saveButtonTemp) {

		settings.setAccountStartup(accountStartupTemp.isSelected());
		browseAccountsTemp.setDisable(!browseAccountsTemp.isDisabled());
		fileNameTemp.setVisible(!fileNameTemp.isVisible());

		// NOTE - the following two statements will only function correctly
		//			if the revert and save buttons are just used by the
		//			Account Startup RadioButtons. If other general settings
		//			are implemented, then these should just be set to true.
		//			As of now, these statements work well with making sure
		//			the settings have changed or not.
		revertButtonTemp.setDisable(!revertButtonTemp.isDisabled());
		saveButtonTemp.setDisable(!saveButtonTemp.isDisabled());
	}

	/**
	 * Opens the file browser to find a valid dat file.
	 *
	 * @param fileNameTemp - Label that will display file path.
	 * @param revertButtonTemp - Button enabled if file path changed.
	 * @param saveButtonTemp - Button enabled if file path changed.
	 */
	public void browseAccountsEvent(Label fileNameTemp,
			Button revertButtonTemp, Button saveButtonTemp) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a planner to load");
		fileChooser.getExtensionFilters().add(datExtension);
		if (!savesFolder.exists()) {
			savesFolder.mkdirs();
		}
		fileChooser.setInitialDirectory(savesFolder);
		String filePath = "";
		try {
			filePath = fileChooser.showOpenDialog(null).toString();
			revertButtonTemp.setDisable(false);
			saveButtonTemp.setDisable(false);
		} catch (Exception e) {
			// TODO - Research why this happens.
			// fileChooser.showOpenDialog(null) throws a general exception when the user exits
			// without selecting a file. This is bad coding practice, but it allows the application
			// to run and just sets the filePath string as empty.
			// Using a try-catch prevents the application from breaking.
			filePath = "";
		}
		fileNameTemp.setText(filePath);
		settings.setAccountFilePath(filePath);
	}

	/**
	 * Display the account settings.
	 * TODO - Implement the account settings.
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
	 * Display theme settings.
	 * TODO - Implement the theme settings.
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
	 * Display the notification settings.
	 * TODO - Implement the notification settings.
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
	 * Handles the 'Back' Event.
	 * Will close the settings menu and open the main menu.
	 */
	public void goBack() {
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
		this.aboutBtn.setOnAction(e -> this.main(Window.ABOUT));

		// Set nav to close when clicking outside of it
		this.mainContent.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				if (this.isNavOpen) {
					this.openMenu.fire();
				}
			}
		});

		this.welcome = new Label(""); // Not initially needed
		loadAbout(); // Using 'About' as the one to show first.

		// Render ABOUT initially:
		this.main(Window.ABOUT);
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
}
