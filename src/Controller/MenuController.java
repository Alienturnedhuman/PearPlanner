package Controller;

import Model.Module;
import Model.Notification;
import View.UIManager;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Zilvinas on 05/05/2017.
 */
public class MenuController implements Initializable
{
    // Labels:
    @FXML
    private Label welcome;

    // Buttons:
    @FXML
    private Button openMenu;
    @FXML
    private Button showNotification;
    @FXML
    private Button addActivity;
    @FXML
    private Button studyProfiles;
    @FXML
    private Button milestones;

    // Panes:
    @FXML
    private AnchorPane navList;
    @FXML
    private AnchorPane notifications;
    @FXML
    private GridPane notificationList;
    @FXML
    private GridPane mainContent;


    public void main()
    {
        openMenu.fire();
        if (MainController.getSPC().getPlanner().getListOfStudyProfiles().length > 0)
            this.loadMain();
    }

    public void loadMain()
    {
        this.mainContent.getChildren().remove(1, this.mainContent.getChildren().size());

        // Create labels (move to .fxml when a final layout is agreed upon):

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
     * @param id
     */
    public void handleRead(int id)
    {
        // Get notification:
        Notification not = MainController.getSPC().getPlanner().getNotifications()[id];

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
                this.showNotification.getStyleClass().add("read-button");
            }

            if (not.getLink() == null)
                this.notificationList.getChildren().get(id).setCursor(Cursor.DEFAULT);
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
        this.welcome.setText("Welcome back, " + MainController.getSPC().getPlanner().getUserName() + "!");

        // Disable relevant openMenu options:
        if (MainController.getSPC().getPlanner().getListOfStudyProfiles().length <= 0)
        {
            this.addActivity.setDisable(true);
            this.studyProfiles.setDisable(true);
            this.milestones.setDisable(true);
        } else
            this.loadMain();

        // Set notification button style:
        if (MainController.getSPC().getPlanner().getUnreadNotifications().length > 0)
            this.showNotification.getStyleClass().add("unread-button");
        else
            this.showNotification.getStyleClass().add("read-button");

        // Process notifications:
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

            Label details = new Label(n[i].getDetailsAsString());
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
     * Prepares animations for the main window
     */
    private void prepareAnimations()
    {
        TranslateTransition openNav = new TranslateTransition(new Duration(300), navList);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(300), navList);
        openMenu.setOnAction((ActionEvent e) -> {
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
}
