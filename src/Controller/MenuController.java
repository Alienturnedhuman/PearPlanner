package Controller;

import Model.Module;
import Model.Notification;
import Model.StudyProfile;
import View.UIManager;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Zilvinas on 05/05/2017.
 */
public class MenuController implements Initializable
{
    public enum Window
    {
        Dashboard, Profiles, Modules
    }

    private Window current;
    private boolean isNavOpen;

    // Labels:
    @FXML private Label welcome;

    // Buttons:
    @FXML private Button openMenu;
    @FXML private Button showNotification;
    @FXML private Button showDash;
    @FXML private Button addActivity;
    @FXML private Button studyProfiles;
    @FXML private Button milestones;
    @FXML private Button modules;

    // Panes:
    @FXML private AnchorPane navList;
    @FXML private AnchorPane notifications;
    @FXML private GridPane notificationList;
    @FXML private GridPane mainContent;


    public void main(Window wind)
    {
        this.current = wind;
        this.main();
    }

    public void main()
    {
        if (isNavOpen)
            openMenu.fire();

        this.updateNotifications();
        this.updateMenu();

        switch (this.current)
        {
            case Dashboard:
            {
                if (MainController.getSPC().getPlanner().getListOfStudyProfileNames().length > 0)
                    this.loadDashboard();
                break;
            }
            case Profiles:
            {
                this.loadStudyProfiles();
                break;
            }
            case Modules:
            {
                this.loadModules();
                break;
            }
        }
    }

    /**
     * Display the Study Dashboard pane
     */
    public void loadDashboard()
    {
        // Update main pane:
        this.mainContent.getChildren().get(0).setVisible(true);
        this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());

        // Display modules:
        Label modules = new Label("Modules");
        modules.getStyleClass().add("title");
        this.mainContent.addRow(1, modules);

        int i = 2;
        for (Module module : MainController.getSPC().getPlanner().getCurrentStudyProfile().getModules())
        {
            Label temp = new Label(module.getName());
            temp.getStyleClass().add("list-item");
            this.mainContent.addRow(i++, temp);
        }
    }

    /**
     * Display the Study Profiles pane
     */
    public void loadStudyProfiles()
    {
        // Update main pane:
        this.mainContent.getChildren().get(0).setVisible(false);
        this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());

        // Display profiles:
        Label profiles = new Label("Study Profiles");
        profiles.getStyleClass().add("title");
        this.mainContent.addRow(1, profiles);

        // Columns:
        TableColumn<StudyProfile, String> nameColumn = new TableColumn<>("Profile name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<StudyProfile, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<StudyProfile, Integer> semesterColumn = new TableColumn<>("Semester");
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semesterNo"));

        ObservableList<StudyProfile> list = FXCollections.observableArrayList(MainController.getSPC().getPlanner().getStudyProfiles());

        // Create a table:
        TableView<StudyProfile> table = new TableView<>();
        table.setItems(list);
        table.getColumns().addAll(nameColumn, yearColumn, semesterColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setHgrow(table, Priority.ALWAYS);
        GridPane.setVgrow(table, Priority.ALWAYS);

        // Set click event:
        table.setRowFactory(e -> {
            TableRow<StudyProfile> row = new TableRow<StudyProfile>()
            {
                @Override
                protected void updateItem(final StudyProfile item, final boolean empty)
                {
                    super.updateItem(item, empty);
                    // If current Profile, mark:
                    if (!empty && item != null)
                        if (item.isCurrent())
                            this.getStyleClass().add("current-item");
                }
            };
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
                {
                    try
                    {
                        StudyProfile profile = row.getItem();
                        MainController.ui.studyProfileDetails(profile);
                        this.main();
                    } catch (IOException e1)
                    {
                        UIManager.reportError("Unable to open View file");
                    }
                }
            });
            return row;
        });

        this.mainContent.addRow(2, table);
        this.mainContent.getStyleClass().add("list-item");
    }

    /**
     * Display the Modules pane
     */
    public void loadModules()
    {
        // Update main pane:
        this.mainContent.getChildren().get(0).setVisible(false);
        this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());

        // Display modules:
        Label modules = new Label("Modules");
        modules.getStyleClass().add("title");
        this.mainContent.addRow(1, modules);

        // Columns:
        TableColumn<Module, String> codeColumn = new TableColumn<>("Module code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("moduleCode"));

        TableColumn<Module, String> nameColumn = new TableColumn<>("Module name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Module, String> organiserColumn = new TableColumn<>("Module Organiser");
        organiserColumn.setCellValueFactory(new PropertyValueFactory<>("organiser"));

        TableColumn<Module, Integer> assignmentColumn = new TableColumn<>("Assignments");
        assignmentColumn.setCellValueFactory(new PropertyValueFactory<>("noOfAssignments"));
        assignmentColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

        ObservableList<Module> list = FXCollections.observableArrayList(MainController.getSPC()
                .getPlanner().getCurrentStudyProfile().getModules());

        // Create a table:
        TableView<Module> table = new TableView<>();
        table.setItems(list);
        table.getColumns().addAll(codeColumn, nameColumn, organiserColumn, assignmentColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setHgrow(table, Priority.ALWAYS);
        GridPane.setVgrow(table, Priority.ALWAYS);

        // Set click event:
        table.setRowFactory(e -> {
            TableRow<Module> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
                {
                    try
                    {
                        Module module = row.getItem();
                        MainController.ui.moduleDetails(module);
                        this.main();
                    } catch (IOException e1)
                    {
                        UIManager.reportError("Unable to open View file");
                    }
                }
            });
            return row;
        });

        this.mainContent.addRow(2, table);
        this.mainContent.getStyleClass().add("list-item");
    }

    /**
     * Handles the 'Mark all as read' button event
     */
    public void handleMarkAll()
    {
        Notification[] nots = MainController.getSPC().getPlanner().getUnreadNotifications();
        // Mark all notifications as read:
        for (int i = 0; i < nots.length; ++i)
        {
            nots[i].read();
            // Remove cursor:
            if (nots[i].getLink() == null)
                this.notificationList.getChildren().get(i).setCursor(Cursor.DEFAULT);

            // Change style:
            this.notificationList.getChildren().get(i).getStyleClass().remove("unread-item");
        }

        // Handle styles:
        this.showNotification.getStyleClass().remove("unread-button");
        if (!this.showNotification.getStyleClass().contains("read-button"))
            this.showNotification.getStyleClass().add("read-button");
    }

    /**
     * Handles clicking on a specific notification
     *
     * @param id
     */
    public void handleRead(int id)
    {
        // Get notification:
        int idInList = MainController.getSPC().getPlanner().getNotifications().length - 1 - id;
        Notification not = MainController.getSPC().getPlanner().getNotifications()[idInList];

        // If not read:
        if (!not.isRead())
        {
            // Mark notification as read:
            not.read();

            // Swap styles:
            this.notificationList.getChildren().get(id).getStyleClass().remove("unread-item");
            if (MainController.getSPC().getPlanner().getUnreadNotifications().length <= 0)
            {
                this.showNotification.getStyleClass().remove("unread-button");
                if (!this.showNotification.getStyleClass().contains("read-button"))
                    this.showNotification.getStyleClass().add("read-button");
            }

            if (not.getLink() == null)
                this.notificationList.getChildren().get(id).setCursor(Cursor.DEFAULT);
        }

        if (not.getLink() != null)
        {
            not.getLink().open();
            this.main();
        }
    }

    /**
     * Handles the 'Import HUB file' event
     */
    public void importFile()
    {
        if (MainController.importFile())
            UIManager.reportSuccess("File imported successfully!");
        this.main();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.prepareAnimations();
        this.isNavOpen = false;

        // Set button actions:
        this.showDash.setOnAction(e -> this.main(Window.Dashboard));
        this.studyProfiles.setOnAction(e -> this.main(Window.Profiles));
        this.modules.setOnAction(e -> this.main(Window.Modules));

        // Welcome text:
        this.welcome.setText("Welcome back, " + MainController.getSPC().getPlanner().getUserName() + "!");

        // Render dashboard:
        this.main(Window.Dashboard);
    }

    /**
     * Prepare notifications
     */
    private void updateNotifications()
    {
        // Set notification button style:
        if (MainController.getSPC().getPlanner().getUnreadNotifications().length > 0)
        {
            if (!this.showNotification.getStyleClass().contains("unread-button"))
            {
                this.showNotification.getStyleClass().remove("read-button");
                this.showNotification.getStyleClass().add("unread-button");
            }
        } else if (!this.showNotification.getStyleClass().contains("read-button"))
        {
            this.showNotification.getStyleClass().add("read-button");
            this.showNotification.getStyleClass().remove("unread-button");
        }

        // Process notifications:
        this.notificationList.getChildren().clear();
        Notification[] n = MainController.getSPC().getPlanner().getNotifications();
        for (int i = n.length - 1; i >= 0; i--)
        {
            GridPane pane = new GridPane();

            // Check if has a link:
            if (n[i].getLink() != null || !n[i].isRead())
            {
                pane.setCursor(Cursor.HAND);
                pane.setId(Integer.toString(n.length - i - 1));
                pane.setOnMouseClicked(e -> this.handleRead(Integer.parseInt(pane.getId())));

                // Check if unread:
                if (!n[i].isRead())
                    pane.getStyleClass().add("unread-item");
            }

            // Create labels:
            Label title = new Label(n[i].getTitle());
            title.getStyleClass().add("notificationItem-title");

            Label details = n[i].getDetails() != null ? new Label(n[i].getDetailsAsString()) : new Label();
            details.getStyleClass().add("notificationItem-details");

            String dateFormatted = n[i].getDateTime().get(Calendar.DAY_OF_MONTH) + " " +
                    n[i].getDateTime().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " at " +
                    n[i].getDateTime().get(Calendar.HOUR) + " " +
                    n[i].getDateTime().getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
            Label date = new Label(dateFormatted);
            date.getStyleClass().addAll("notificationItem-date");
            GridPane.setHalignment(date, HPos.RIGHT);
            GridPane.setHgrow(date, Priority.ALWAYS);

            pane.addRow(1, title);
            pane.addRow(2, details);
            pane.addRow(3, date);
            pane.addRow(4, new Separator(Orientation.HORIZONTAL));
            this.notificationList.addRow(n.length - i - 1, pane);
        }
    }

    /**
     * Handles menu options
     */
    private void updateMenu()
    {
        this.addActivity.setDisable(false);
        this.milestones.setDisable(false);
        this.studyProfiles.setDisable(false);
        this.modules.setDisable(false);

        // Disable relevant menu options:
        if (MainController.getSPC().getPlanner().getCurrentStudyProfile() == null)
        {
            this.addActivity.setDisable(true);
            this.milestones.setDisable(true);
            this.studyProfiles.setDisable(true);
            this.modules.setDisable(true);
        } else if (MainController.getSPC().getCurrentTasks().size() <= 0)
        {
            this.addActivity.setDisable(true);
            this.milestones.setDisable(true);
        }

        if (MainController.getSPC().getPlanner().getCurrentStudyProfile().getModules().length <= 0)
            this.modules.setDisable(true);
    }

    /**
     * Prepares animations for the main window
     */
    private void prepareAnimations()
    {
        TranslateTransition openNav = new TranslateTransition(new Duration(300), navList);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(300), navList);
        openMenu.setOnAction((ActionEvent e) -> {
            this.isNavOpen = !isNavOpen;
            if (navList.getTranslateX() != 0)
            {
                openNav.play();
            } else
            {
                closeNav.setToX(-(navList.getWidth()));
                closeNav.play();
            }
        });

        TranslateTransition openNot = new TranslateTransition(new Duration(350), notifications);
        openNot.setToY(0);
        TranslateTransition closeNot = new TranslateTransition(new Duration(350), notifications);

        showNotification.setOnAction((ActionEvent e) -> {
            if (notifications.getTranslateY() != 0)
            {
                openNot.play();
            } else
            {
                closeNot.setToY(-(notifications.getHeight()) - 56.0);
                closeNot.play();
            }
        });
    }

//    public static class Previous
//    {
//        public Window current;
//        public Previous previous;
//
//        public Previous(Window current, Previous prev)
//        {
//            this.current = current;
//            this.previous = prev;
//        }
//    }
}
