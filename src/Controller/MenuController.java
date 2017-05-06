package Controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
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

    public void main()
    {
        menu.fire();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        prepareSlideMenuAnimation();
        if (MainController.SPC.getStudyProfiles().length <= 0)
        {
            this.addActivity.setDisable(true);
            this.studyProfiles.setDisable(true);
            this.milestones.setDisable(true);
        }
        if (MainController.SPC.getNotifications().length > 0)
            this.showNotification.setStyle(this.showNotification.getStyle() +
                                            "-fx-background-image: url('/View/notification1.png');");
        else
            this.showNotification.setStyle(this.showNotification.getStyle() +
                                            "-fx-background-image: url('/View/notification0.png');");
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
