//package Model;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
///**
// * Created by bijan on 13/05/2017.
// */
//public class ExamTest
//{
//    Building building;
//    Room room;
//    Exam finalexam, examResit;
//    ExamEvent examEvent;
//    Person person1, person2;
//
//    @Before
//    public void setUp() throws Exception
//    {
//        building = new Building("JSC", 52.6308859, 1.297355);
//        room = new Room("JSC_1_02", building);
//        examEvent = new ExamEvent("13/05/2017T14:50:12", room, 120);
//        person1 = new Person("Dr.", "Mark Fisher", true);
//        person2 = new Person("Dr.", "Rudy Lapeer", true);
//        examResit = null;
//        finalexam = new Exam(30, person1, person1, person2, 100, examEvent, examResit);
//}
//
//    @After
//    public void tearDown() throws Exception
//    {
//        building = null;
//        room = null;
//        examEvent = null;
//        person1 = null;
//        person2 = null;
//        examResit = null;
//        finalexam = null;
//    }
//
//    @Test
//    public void getResit() throws Exception
//    {
//        assertEquals(examResit, finalexam.getResit());
//    }
//
//    @Test
//    public void getTimeSlot() throws Exception
//    {
////        assertEquals(examEvent, finalexam.getTimeSlot());
//    }
//
//}