package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bijan on 06/05/2017.
 */
public class CourseworkTest{
//    public class CourseworkTest extends AssignmentTest{

    private Coursework coursework;

    @Before
    public void setUp(){
        Person person1 = new Person("Dr.", "Mark Fisher", true);
        Person person2 = new Person("Dr.", "Steven Laycock", true);
        Event event = new Event("02/03/2017T05:02:09");
        Deadline deadline = new Deadline("02/05/2017T05:02:09");
        ArrayList<Extension> extensions = new ArrayList<>();
        coursework = new Coursework(30, person1, person1, person2, 100, event, deadline, extensions);
//        assignment = coursework;
    }

    @Test
    public void getStartDate() throws Exception
    {
    }

    @Test
    public void getDeadline() throws Exception
    {
    }

    @Test
    public void getExtensions() throws Exception
    {
    }

    @Test
    public void getNotes() throws Exception
    {
    }

    @Test
    public void addNote() throws Exception
    {
    }

    @Test
    public void removeNote() throws Exception
    {
    }

}