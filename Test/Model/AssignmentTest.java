/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar
 *
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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