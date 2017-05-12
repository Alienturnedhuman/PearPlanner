package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Created by bijan on 06/05/2017.
 */
public class CourseworkTest{

    private Coursework coursework;
    private Person person1, person2;
    private Event event;
    private Deadline deadline;
    private ArrayList<Extension> extensions;
    private ArrayList<Note> notes;

    @Before
    public void setUp()
    {
        person1 = new Person("Dr.", "Mark Fisher", true);
        person2 = new Person("Dr.", "Steven Laycock", true);
        event = new Event("02/03/2017T05:02:09");
        deadline = new Deadline("02/05/2017T05:02:09");
        extensions = new ArrayList<>();
        coursework = new Coursework(30, person1, person1, person2, 100, event, deadline, extensions);
        notes = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception
    {
        person1 = null;
        person2 = null;
        event = null;
        deadline = null;
        extensions = null;
        coursework = null;
        notes = null;
    }

    @Test
    public void getStartDate() throws Exception
    {
        assertEquals(event, coursework.getStartDate());
    }

    @Test
    public void getDeadline() throws Exception
    {
        assertEquals(deadline, coursework.getDeadline());
    }

    @Test
    public void getExtensions() throws Exception
    {
        assertEquals(extensions, coursework.getExtensions());
    }

    @Test
    public void getNotes() throws Exception
    {
        assertEquals(notes, coursework.getNotes());
    }

    @Test
    public void addNote() throws Exception
    {
//        Note note = new Note("Some Title", new GregorianCalendar(12, 05, 2017, 21,
//                19, 10), new MultilineString("Some text"));
//        notes.add(note);
//        assertEquals(notes, coursework.getNotes());
    }

    @Test
    public void removeNote() throws Exception
    {
    }

}