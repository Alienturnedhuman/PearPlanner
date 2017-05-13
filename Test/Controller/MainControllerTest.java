package Controller;

import View.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

/**
 * Created by bijan on 13/05/2017.
 */
public class MainControllerTest extends ApplicationTest
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        MainController.initialise();
    }

    @After
    public void tearDown() throws Exception
    {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void isNumeric() throws Exception
    {
        assertEquals(true, MainController.isNumeric("23"));
    }

    public <T extends Node> T find (final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }
}