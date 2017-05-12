package Model;

import Controller.MainController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bijan on 12/05/2017.
 */
public class EventTest {

    Event event;

    @Before
    public void setUp() throws Exception
    {
//        assertEquals(true, MainController.isNumeric("32"));
        event = new Event("12/05/2017T13:54:20");
    }

    @Test
    public void validDateString() throws Exception
    {

    }

    @Test
    public void toStringTest() throws Exception
    {
        assertEquals("12/5/2017T13:54:20", event.toString());
    }

    @Test
    public void setDate() throws Exception
    {
    }

}