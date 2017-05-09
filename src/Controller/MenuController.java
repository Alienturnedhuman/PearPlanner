package Controller;

import Model.Notification;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by Žilvinas on 05/05/2017.
 */
public class MenuController implements Initializable
{
    @FXML private Button menu;
    @FXML private Button showNotification;
    @FXML private Button addActivity;
    @FXML private Button studyProfiles;
    @FXML private Button milestones;
    @FXML private AnchorPane navList;
    @FXML private AnchorPane notifications;
    @FXML private GridPane notificationList;

    public void main()
    {
        menu.fire();
    }

    public void handleMarkAll()
    {
        Arrays.stream(MainController.getSPC().getPlanner().getUnreadNotifications()).forEach(e -> e.read());
        this.showNotification.getStyleClass().remove("unread");

        if (!this.showNotification.getStyleClass().contains("read"))
            this.showNotification.getStyleClass().add("read");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        prepareSlideMenuAnimation();
        if (MainController.getSPC().getPlanner().getListOfStudyProfiles().length <= 0)
        {
            this.addActivity.setDisable(true);
            this.studyProfiles.setDisable(true);
            this.milestones.setDisable(true);
        }
        if (MainController.getSPC().getPlanner().getUnreadNotifications().length > 0)
            this.showNotification.getStyleClass().add("unread");
        else
            this.showNotification.getStyleClass().add("read");

        Notification[] n = MainController.getSPC().getPlanner().getNotifications();
        for (int i = 0; i < n.length; i++)
        {
            Label l = new Label(n[i].getTitle());
            l.getStyleClass().add("notificationItem");
            this.notificationList.addRow(i, l);
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
                closeNot.setToY(-(notifications.getHeight())-56.0);
                closeNot.play();
            }
        });
    }
}
