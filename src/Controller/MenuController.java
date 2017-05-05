package Controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Žilvinas on 05/05/2017.
 */
public class MenuController implements Initializable
{
    @FXML
    private Button menu;
    @FXML
    private Button showNotification;
    @FXML
    private AnchorPane navList;
    @FXML
    AnchorPane notifications;

    public void initialize(URL location, ResourceBundle resources)
    {
        prepareSlideMenuAnimation();
    }

    private void prepareSlideMenuAnimation()
    {
        TranslateTransition openNav = new TranslateTransition(new Duration(350), navList);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), navList);
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
