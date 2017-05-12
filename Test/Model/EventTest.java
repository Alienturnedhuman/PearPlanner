//package Model;
//
//import Controller.MainController;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
///**
// * Created by bijan on 12/05/2017.
// */
//public class EventTest {
//
//    Event event;
//
//    @Before
//    public void setUp() throws Exception
//    {
//        event = new Event("02/05/2017T05:02:09");
//    }
//
//    @Test
//    public void validDateString() throws Exception
//    {
//        // Testing a valid date format
//        assertEquals(true, Event.validDateString("02/05/2017T05:02:09"));
//
//        // Testing an invalid date format
//        assertEquals(false, Event.validDateString("02/05/217T0:02:09"));
//    }
//
//    @Test
//    public void toStringTest() throws Exception
//    {
//        assertEquals("02/05/2017T05:02:09", event.toString());
//    }
//
//    @Test
//    public void setDate() throws Exception
//    {
//        event.setDate("04/10/2017T09:08:13");
//        assertEquals("04/10/2017T09:08:13", event.toString());
//    }
//
//}