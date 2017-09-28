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

package View;

import Controller.*;
import Model.*;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.scene.control.agenda.Agenda;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 * Created by Zilvinas on 04/05/2017.
 */
public class UIManager
{
    private static Stage mainStage = new Stage();
    private static MenuController mc = new MenuController();

    /**
     * Displays a 'Create Account' window and handles the creation of
     * a new Account object
     *
     * @return newly created Account
     * @throws Exception
     */
    public Account createAccount() throws Exception
    {
        AccountController accountControl = new AccountController();
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Hello World!");

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CreateAccount.fxml"));
        loader.setController(accountControl);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 550, 232));
        stage.setTitle("Create Account");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();

        // Handle creation of the Account object:
        if (accountControl.isSuccess())
        {
            Account newAccount = accountControl.getAccount();
            return newAccount;
        } else
            throw new Exception("User quit.");

    }

    /**
     * Displays the main menu
     *
     * @throws Exception
     */
    public void mainMenu() throws Exception
    {
        // Load in the .fxml file:
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Hello World!");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainMenu.fxml"));
        loader.setController(UIManager.mc);
        Parent root = loader.load();

        // Set the scene:
        mainStage.setScene(new Scene(root, 1000, 750, true, SceneAntialiasing.BALANCED));
        mainStage.setTitle("RaiderPlanner");
        mainStage.getIcons().add(new Image("file:icon.png"));
        mainStage.showAndWait();
    }

    /**
     * Display the 'Add Activity' window
     *
     * @return newly created Activity
     */
    public Activity addActivity() throws Exception
    {
        ActivityController ac = new ActivityController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Activity.fxml"));
        loader.setController(ac);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 358));
        stage.setTitle("New Activity");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();

        // Add the Activity to the StudyPlanner
        if (ac.isSuccess())
            return ac.getActivity();
        return null;
    }

    /**
     * Displays the Activity details page
     *
     * @param activity Activity for which the details should be displayed.
     */
    public void activityDetails(Activity activity) throws IOException
    {
        ActivityController ac = new ActivityController(activity);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Activity.fxml"));
        loader.setController(ac);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 358));
        stage.setTitle("Activity");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();
    }

    /**
     * Displays the 'Add Milestone' window.
     *
     * @return newly created Milestone object.
     */
    public Milestone addMilestone() throws IOException
    {
        MilestoneController mc = new MilestoneController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Milestone.fxml"));
        loader.setController(mc);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 355));
        stage.setTitle("Milestone");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();

        // Add the Milestone to the StudyPlanner
        if (mc.isSuccess())
            return mc.getMilestone();
        return null;
    }

    /**
     * Displays the Milestone details page
     *
     * @param milestone Milestone for which the details should be shown.
     */
    public void milestoneDetails(Milestone milestone) throws IOException
    {
        MilestoneController mc = new MilestoneController(milestone);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Milestone.fxml"));
        loader.setController(mc);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 355));
        stage.setTitle("Milestone");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();
    }

    /**
     * Displays the StudyProfile details page
     *
     * @param profile StudyProfile for which the details should be shown.
     */
    public void studyProfileDetails(StudyProfile profile) throws IOException
    {
        StudyProfileController spc = new StudyProfileController(profile);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/StudyProfile.fxml"));
        loader.setController(spc);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 232));
        stage.setTitle(profile.getName());
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();
    }

    /**
     * Displays the Module details page
     *
     * @param module  Module for which the details should be shown.
     * @param current Window from which this method is called.
     * @throws IOException
     */
    public void moduleDetails(Module module, MenuController.Window current) throws IOException
    {
        UIManager.mc.loadModule(module, current, null);
    }

    /**
     * Displays the Module details page
     *
     * @param module  Module for which the details should be shown.
     * @param current Window from which this method is called.
     * @throws IOException
     */
    public void moduleDetails(Module module, ModelEntity current) throws IOException
    {
        UIManager.mc.loadModule(module, MenuController.Window.Empty, current);
    }

    /**
     * Displays the Assignment details page
     *
     * @param assignment Assignment for which the details should be shown.
     * @param current    Window from which this method is called.
     * @throws IOException
     */
    public void assignmentDetails(Assignment assignment, MenuController.Window current) throws IOException
    {
        UIManager.mc.loadAssignment(assignment, current, null);
    }

    /**
     * Displays the Assignment details page
     *
     * @param assignment Assignment for which the details should be shown.
     * @param current    Window from which this method is called.
     * @throws IOException
     */
    public void assignmentDetails(Assignment assignment, ModelEntity current) throws IOException
    {
        UIManager.mc.loadAssignment(assignment, MenuController.Window.Empty, current);
    }

    /**
     * Creates a window for adding a new Task
     *
     * @return newly created Task
     */
    public Task addTask() throws Exception
    {
        TaskController tc = new TaskController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Task.fxml"));
        loader.setController(tc);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 558));
        stage.setTitle("New Task");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();

        // Handle creation of the Account object:
        if (tc.isSuccess())
            return tc.getTask();
        return null;
    }

    /**
     * Displays the Task details page
     *
     * @param task for which the details should be displayed.
     * @throws IOException
     */
    public void taskDetails(Task task) throws IOException
    {
        TaskController tc = new TaskController(task);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Task.fxml"));
        loader.setController(tc);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 558));
        stage.setTitle("Task");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();
    }

    /**
     * Creates a window for adding a new Requirement
     *
     * @return newly created Requirement
     */
    public Requirement addRequirement() throws Exception
    {
        RequirementController rc = new RequirementController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Requirement.fxml"));
        loader.setController(rc);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 260));
        stage.setTitle("New Requirement");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();

        // Handle creation of the Account object:
        if (rc.isSuccess())
            return rc.getRequirement();
        return null;
    }

    /**
     * Displays the Requirement details page
     *
     * @param requirement Requirement for which the details should be displayed
     */
    public void requirementDetails(Requirement requirement) throws IOException
    {
        RequirementController rc = new RequirementController(requirement);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Requirement.fxml"));
        loader.setController(rc);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 558));
        stage.setTitle("Requirement");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();
    }

    /**
     * Displays a GanttishDiagram window for the given Assignment.
     *
     * @param assignment Assignment for which to generate the GanttishDiagram.
     */
    public void showGantt(Assignment assignment)
    {
        Stage stage = new Stage();

        // Layout:
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(15));
        layout.getStylesheets().add("/Content/stylesheet.css");
        // =================

        // Nav bar:
        HBox nav = new HBox();
        nav.setSpacing(15.0);
        // =================

        // Title:
        Label title = new Label("Ganttish Diagram");
        title.getStyleClass().add("title");
        HBox x = new HBox();
        HBox.setHgrow(x, Priority.ALWAYS);
        // =================

        // Buttons:
        Button save = new Button("Save");
        save.setOnAction(e -> {
            String path = this.saveFileDialog(stage);
            GanttishDiagram.createGanttishDiagram(MainController.getSPC().getPlanner(), assignment, path);
        });
        Button close = new Button("Close");
        close.setOnAction(e -> ((Stage) close.getScene().getWindow()).close());
        // =================

        nav.getChildren().addAll(title, x, save, close);

        // Content:
        BufferedImage gantt = GanttishDiagram.createGanttishDiagram(MainController.getSPC().getPlanner(), assignment);
        Image image = SwingFXUtils.toFXImage(gantt, null);
        Pane content = new Pane();
        VBox.setVgrow(content, Priority.ALWAYS);
        content.setBackground(new Background(
                new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
        // =================

        layout.getChildren().addAll(nav, content);

        // Set the scene:
        stage.setScene(new Scene(layout, 1300, 800, true, SceneAntialiasing.BALANCED));
        stage.setTitle("Ganttish Diagram");
        stage.resizableProperty().setValue(true);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.setFullScreen(true);
        stage.showAndWait();
        // =================
    }

    /**
     * Displays a Calendar window with events of this Study Profile.
     */
    public void showCalendar()
    {
        Stage stage = new Stage();

        // Layout:
        VBox layout = new VBox();
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
        HBox x = new HBox();
        HBox.setHgrow(x, Priority.ALWAYS);
        // =================

        // Buttons:
        Button save = new Button("Export");
        save.setOnAction(e -> {
            // Not implemented yet.
        });
        Button agendaFwd = new Button(">");
        Button agendaBwd = new Button("<");
        // =================

        nav.getChildren().addAll(title, x, agendaBwd, agendaFwd);

        // Content:
        Agenda content = new Agenda();
        VBox.setVgrow(content, Priority.ALWAYS);
        content.setAllowDragging(false);
        content.setAllowResize(false);
        content.autosize();
        content.setActionCallback(param -> null);
        content.setEditAppointmentCallback(param -> null);
        // Agenda buttons:
        agendaBwd.setOnMouseClicked(event -> content.setDisplayedLocalDateTime(content.getDisplayedLocalDateTime().minusDays(7)));
        agendaFwd.setOnMouseClicked(event -> content.setDisplayedLocalDateTime(content.getDisplayedLocalDateTime().plusDays(7)));
        // =================

        // Populate Agenda:
        ArrayList<Event> calendar = MainController.getSPC().getPlanner().getCurrentStudyProfile().getCalendar();
        for (Event e : calendar)
        {
            if (e instanceof TimetableEvent)
            {
                LocalDateTime sTime = LocalDateTime.ofInstant(e.getDate().toInstant(), ZoneId.systemDefault());
                content.appointments().addAll(
                        new Agenda.AppointmentImplLocal()
                                .withStartLocalDateTime(sTime)
                                .withEndLocalDateTime(sTime.plusMinutes(((TimetableEvent) e).getDuration()))

                                .withSummary(e.getName() + "\n" + "@ " + ((TimetableEvent) e).getRoom().getLocation())
                                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group5"))
                );
            } else if (e instanceof ExamEvent)
            {
                LocalDateTime sTime = LocalDateTime.ofInstant(e.getDate().toInstant(), ZoneId.systemDefault());
                content.appointments().addAll(
                        new Agenda.AppointmentImplLocal()
                                .withStartLocalDateTime(sTime)
                                .withSummary(e.getName() + "\n" + "@ " + ((ExamEvent) e).getRoom().getLocation())
                                .withEndLocalDateTime(sTime.plusMinutes(((ExamEvent) e).getDuration()))
                                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group20"))
                );
            } else if (e instanceof Deadline)
            {
                LocalDateTime sTime = LocalDateTime.ofInstant(e.getDate().toInstant(), ZoneId.systemDefault());
                content.appointments().addAll(
                        new Agenda.AppointmentImplLocal()
                                .withStartLocalDateTime(sTime.minusMinutes(60))
                                .withSummary(e.getName())
                                .withEndLocalDateTime(sTime)
                                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1"))
                );
            } else
            {
                LocalDateTime sTime = LocalDateTime.ofInstant(e.getDate().toInstant(), ZoneId.systemDefault());
                content.appointments().addAll(
                        new Agenda.AppointmentImplLocal()
                                .withStartLocalDateTime(sTime)
                                .withSummary(e.getName())
                                .withEndLocalDateTime(sTime.plusMinutes(60))
                                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group3"))
                );
            }
        }
        // =================

        // =================

        layout.getChildren().addAll(nav, content);

        // Set the scene:
        stage.setScene(new Scene(layout, 1300, 800));
        stage.setTitle("Calendar");
        stage.resizableProperty().setValue(true);
        stage.getIcons().add(new Image("file:icon.png"));

        Platform.runLater(() -> content.setDisplayedLocalDateTime(content.getDisplayedLocalDateTime().plusMinutes(1050)));
        stage.showAndWait();
        // =================
    }

    /**
     * Displays a file dialog for importing .xml files
     *
     * @return a File object
     */
    public File loadFileDialog()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a HUB file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML file", "*.xml"));
        File file = fileChooser.showOpenDialog(mainStage);
        return file;
    }

    /**
     * Displays a file dialog for saving an .jpeg file
     *
     * @return String path
     */
    public String saveFileDialog(Stage stage)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Ganttish Diagram");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG file", "*.png"));
        File path = fileChooser.showSaveDialog(stage);
        return path.getAbsolutePath();
    }

    /**
     * Displays an error message
     *
     * @param message to be displayed
     */
    public static void reportError(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    /**
     * Reports that an action was successful and displays a message
     *
     * @param message to be displayed
     */
    public static void reportSuccess(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    /**
     * Reports that an action was successful
     */
    public static void reportSuccess()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Success!");
        alert.showAndWait();
    }

    /**
     * Confirm box with 'Yes' or 'No' as available options
     *
     * @param message message to be displayed
     * @return true for yes, false for no.
     */
    public static boolean confirm(String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        return alert.getResult().equals(ButtonType.YES);
    }

    /**
     * Please don't use
     */
    public static void areYouFeelingLucky()
    {
        while (UIManager.confirm("Are you feeling lucky?") == (Math.random() < 0.5)) ;
    }

}