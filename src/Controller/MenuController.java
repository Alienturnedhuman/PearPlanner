package Controller;

import Model.Notification;
import com.google.common.base.Splitter;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Zilvinas on 05/05/2017.
 */
public class MenuController implements Initializable
{
    @FXML
    private Button menu;
    @FXML
    private Button showNotification;
    @FXML
    private Button addActivity;
    @FXML
    private Button studyProfiles;
    @FXML
    private Button milestones;
    @FXML
    private AnchorPane navList;
    @FXML
    private AnchorPane notifications;
    @FXML
    private GridPane notificationList;

    public void main()
    {
        menu.fire();
    }

    public void handleMarkAll()
    {
        // Mark all notifications as read:
        Notification[] nots = MainController.getSPC().getPlanner().getUnreadNotifications();
        for (int i = 0; i < nots.length; i++)
        {
            nots[i].read();
            // Remove cursor:
            if (nots[i].getLink() == null)
                this.notificationList.getChildren().get(i).setCursor(Cursor.DEFAULT);
        }

        // Handle styles:
        this.notificationList.getChildren().forEach(e -> e.getStyleClass().remove("unread-item"));

        this.showNotification.getStyleClass().remove("unread-button");
        if (!this.showNotification.getStyleClass().contains("read-button"))
            this.showNotification.getStyleClass().add("read-button");
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        prepareSlideMenuAnimation();

        // Disable relevant menu options:
        if (MainController.getSPC().getPlanner().getListOfStudyProfiles().length <= 0)
        {
            this.addActivity.setDisable(true);
            this.studyProfiles.setDisable(true);
            this.milestones.setDisable(true);
        }

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
                pane.setId(new Integer(n.length - i - 1).toString());
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

    private void prepareSlideMenuAnimation()
    {
        TranslateTransition openNav = new TranslateTransition(new Duration(300), navList);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(300), navList);
        menu.setOnAction((ActionEvent e) -> {
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
