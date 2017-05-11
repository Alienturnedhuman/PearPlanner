package View;



import Controller.AccountController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;

/**
 * PearPlanner
 * Created by Team BRONZE on 08/05/2017
 */
public abstract class FXBase extends ApplicationTest {



    @Override
    public void start(Stage stage) throws Exception {

        AccountController accountControl = new AccountController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccount.fxml"));
        loader.setController(accountControl);
        Parent root = loader.load();

        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        Scene scene = new Scene(root, 550, 232);

        stage.setScene(scene);

        stage.show();
    }

    @After
    public void afterEachTest() throws TimeoutException {
       FxToolkit.hideStage();
       release(new KeyCode[]{});
       release(new MouseButton[]{});
    }


    public <T extends Node> T find (final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }



}
