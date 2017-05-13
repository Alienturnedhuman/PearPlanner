package Controller;


import javafx.stage.Stage;
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
        MainController.isNumeric("23");
    }

    @Test
    public void isNumeric() throws Exception
    {
        // Testing a valid number
        assertEquals(true, MainController.isNumeric("23"));

        // Testing an invalid number
        assertEquals(false, MainController.isNumeric("23df"));
    }
}