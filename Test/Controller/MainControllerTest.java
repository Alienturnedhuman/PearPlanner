package Controller;

import View.Main;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
        MainController.initialise();
    }


    @Test
    public void isNumeric() throws Exception
    {
        assertEquals(true, MainController.isNumeric("23"));
    }

}