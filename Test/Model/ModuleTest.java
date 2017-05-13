package Model;

import Controller.MainController;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bijan on 13/05/2017.
 */
public class ModuleTest extends ApplicationTest
{

    Person person;
    Module module;
    ArrayList<Assignment> assignments;
    ArrayList<TimetableEvent> timetableEvents;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        MainController.isNumeric("23");
    }

    @Before
    public void setUp() throws Exception
    {
        person = new Person("Dr.", "Mark Fisher", true);
        module = new Module(person, "CMP-1550Y");
        assignments = new ArrayList<>();
        timetableEvents = new ArrayList<>();
        Person person1 = new Person("Dr.", "Mark Fisher", true);
        Person person2 = new Person("Dr.", "Rudy Lapeer", true);
        assignments.add(new Assignment(30, person1, person1, person2, 100));
        TimeTableEventType timeTableEventType = new TimeTableEventType();
        Building building = new Building("JSC", 52.6308859, 1.297355);
        Room room = new Room("JSC_1_02", building);
        TimetableEvent timetableEvent = new TimetableEvent("13/05/2017T14:50:12", room, person1,
                new TimeTableEventType(), 120);
        timetableEvents.add(timetableEvent);

    }

    @After
    public void tearDown() throws Exception
    {
        person = null;
        module = null;
    }

    @Test
    public void toStringTest1() throws Exception
    {
        String expected = "Module: "+module.getName()+" ( "+module.getModuleCode()+" )";
        assertEquals(expected, module.toString());
    }

    @Test
    public void addAssignment() throws Exception
    {
        // Testing with one Assignment in the list
        module.addAssignment(assignments.get(0));
        for (int i=0; i<module.getAssignments().size(); i++){
            assertEquals(assignments.get(i).toString(true), module.getAssignments().get(i).toString(true));
        }

        // Testing with two assignments in the list
        Person person1 = new Person("Dr.", "Steven Laycock", true);
        Person person2 = new Person("Dr.", "Rudy Lapeer", true);
        assignments.add(new Assignment(40, person2, person2, person1, 100));
        module.addAssignment(assignments.get(1));
        for (int i=0; i<module.getAssignments().size(); i++){
            assertEquals(assignments.get(i).toString(true), module.getAssignments().get(i).toString(true));
        }

        // Adding a duplicate to the list
        module.addAssignment(assignments.get(1));
        assertNotEquals(3, module.getAssignments().size());
    }

    @Test
    public void toStringTest2() throws Exception
    {
        // Testing with no assignment in the list
        StringBuilder expected = new StringBuilder();
        expected.append(module.toString());
        expected.append("\n");
        expected.append("Organiser: "+person.toString());
        expected.append("\n");
        expected.append("Total Assignments: "+Integer.toString(module.getAssignments().size()));
        expected.append("\n");

        int i =-1;
        int ii = module.getAssignments().size();

        while(++i<ii)
        {
            expected.append("\n");
            expected.append(module.getAssignments().get(i).toString(true));
        }
        assertEquals(expected.toString(), module.toString(true));


        // Testing with one assignment in the list

        module.addAssignment(assignments.get(0));
        expected.delete(0, expected.length());
        expected.append(module.toString());
        expected.append("\n");
        expected.append("Organiser: "+person.toString());
        expected.append("\n");
        expected.append("Total Assignments: "+Integer.toString(module.getAssignments().size()));
        expected.append("\n");

        i =-1;
        ii = module.getAssignments().size();

        while(++i<ii)
        {
            expected.append("\n");
            expected.append(module.getAssignments().get(i).toString(true));
        }
        assertEquals(expected.toString(), module.toString(true));

    }

    @Test
    public void removeAssignment() throws Exception
    {
        module.removeAssignment(assignments.get(0));
        assignments.remove(0);
        assertEquals(assignments, module.getAssignments());
    }

    @Test
    public void addTimetableEvent() throws Exception
    {
        // Testing with one Time Table Event in the list
        module.addTimetableEvent(timetableEvents.get(0));
        for (int i=0; i<module.getAssignments().size(); i++){
            assertEquals(timetableEvents.get(i), module.getTimetable().get(i));
        }

        // Testing with two Time Table Event in the list
        Person person2 = new Person("Dr.", "Rudy Lapeer", true);
        Building building = new Building("JSC", 52.6308859, 1.297355);
        Room room = new Room("JSC_3_03", building);
        TimetableEvent timetableEvent = new TimetableEvent("20/08/2017T15:50:12", room, person2,
                new TimeTableEventType(), 120);
        timetableEvents.add(timetableEvent);
        module.addTimetableEvent(timetableEvents.get(1));
        for (int i=0; i<module.getAssignments().size(); i++){
            assertEquals(timetableEvents.get(i), module.getTimetable().get(i));
        }

        // Adding a duplicate to the list
        module.addTimetableEvent(timetableEvents.get(1));
        assertNotEquals(3, module.getTimetable().size());
    }

    @Test
    public void removeTimetableEvent() throws Exception
    {
        module.removeTimetableEvent(timetableEvents.get(0));
        timetableEvents.remove(0);
        assertEquals(timetableEvents, module.getTimetable());
    }

}