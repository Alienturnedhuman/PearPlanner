package Model;

import Controller.MainController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;


import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Created by bijan on 12/05/2017.
 */


public class EventTest extends ApplicationTest
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        MainController.isNumeric("23");
    }

    Event event;

    @Before
    public void setUp() throws Exception
    {
        event = new Event("09/04/2017T15:00:00Z");
    }

    @Test
    public void validDateString() throws Exception
    {

        // Testing a valid date format
        assertEquals(true, Event.validDateString("02/05/2017T05:02:09Z"));

        // Testing an invalid date format
        assertEquals(false, Event.validDateString("02/05/217T0:02:09Z"));

    }

    @Test
    public void toStringTest() throws Exception
    {

        GregorianCalendar expectedDate = new GregorianCalendar(2017, 3, 9, 15, 0, 0);
        assertEquals(expectedDate.getTime().toString(), event.toString());
    }

    @Test
    public void setDate() throws Exception
    {
        event.setDate("04/10/2017T09:08:13Z");
        GregorianCalendar expectedDate = new GregorianCalendar(2017, 9, 4, 9, 8, 13);
        assertEquals(expectedDate.getTime().toString(), event.toString());
    }

}