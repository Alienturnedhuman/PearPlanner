package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bijan on 12/05/2017.
 */
public class AssignmentTest {

    Assignment assignment;
    Person person1;
    Person person2;

    @Before
    public void setUp() throws Exception
    {
        person1 = new Person("Dr.", "Mark Fisher", true);
        person2 = new Person("Dr.", "Steven Laycock", true);
        assignment = new Assignment(30, person1, person1, person2, 100);
    }

    @After
    public void tearDown() throws Exception
    {
        person1 = null;
        person2 = null;
        assignment = null;
    }

    @Test
    public void toStringTest() throws Exception
    {
        String expected = "Assignment '"+assignment.getName()+"'";
        assertEquals(expected, assignment.toString());
    }

    @Test
    public void toStringTest2() throws Exception
    {
        // Testing with a true verbose
        StringBuilder r = new StringBuilder();
        r.append(assignment);
        r.append("\n");
        r.append("Total marks: "+100);
        r.append("\n");
        r.append("Total weighting: "+30);

        r.append("\n");
        r.append("Set By: "+person1.toString());
        r.append("\n");
        r.append("Marked By: "+person1.toString());
        r.append("\n");
        r.append("Reviewed By: "+person2.toString());

        assertEquals(r.toString(), assignment.toString(true));


        // Testing with a false verbose
        assertEquals(assignment.toString(), assignment.toString(false));
    }



}