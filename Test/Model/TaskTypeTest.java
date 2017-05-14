package Model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bijan on 14/05/2017.
 */
public class TaskTypeTest
{

    @Test
    public void listOfNames() throws Exception
    {
        String[] expected = {"Other", "Reading", "Exercises", "Listening", "Coursework", "Revision", "Meeting"};
        assertArrayEquals(expected, TaskType.listOfNames());
    }

    @Test
    public void get() throws Exception
    {
        // Testing with an existing Task Type
        assertEquals("Reading", TaskType.get("Reading").getName());

        // Testing with a non-existing Task Type
        assertEquals("Other", TaskType.get("jdsf").getName());
    }

    @Test
    public void listOfTaskTypes() throws Exception
    {
        TaskType[] taskTypes = {TaskType.get("Other"), TaskType.get("Reading"), TaskType.get("Exercises"),
                TaskType.get("Listening"), TaskType.get("Coursework"), TaskType.get("Revision"), TaskType.get("Meeting")};
        assertArrayEquals(taskTypes, TaskType.listOfTaskTypes());
    }

    @Test
    public void exists() throws Exception
    {
        // Testing with an existing Task Type
        assertTrue(TaskType.exists("Other"));

        // Testing with a non-existing Task Type
        assertFalse(TaskType.exists("lskdhr"));
    }

    @Test
    public void exists1() throws Exception
    {
        // Testing with an existing Task Type
        assertTrue(TaskType.exists(TaskType.get("Other")));

        // Testing with a non-existing Task Type (Expect to get the default)****
        assertTrue(TaskType.exists(TaskType.get("jsfjfsdf")));
    }

    @Test
    public void equals() throws Exception
    {
        // Testing with two equal Task Types
        assertTrue(TaskType.get("Reading").equals("Reading"));

        // Testing with two non-equal Task Types
        assertFalse(TaskType.get("Reading").equals("Listening"));
    }

    @Test
    public void equals1() throws Exception
    {
        // Testing with two equal Task Types
        assertTrue(TaskType.get("Reading").equals(TaskType.get("Reading")));

        // Testing with two non-equal Task Types
        assertFalse(TaskType.get("Reading").equals(TaskType.get("Listening")));
    }

}