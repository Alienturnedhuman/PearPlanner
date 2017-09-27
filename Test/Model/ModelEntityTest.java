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

import Controller.MainController;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Created by bijan on 08/05/2017.
 */
@Ignore
public class ModelEntityTest {

    private ModelEntity modelEntity;
    private GregorianCalendar gregorianCalendar;
    private String[] detailsArray= {"detail"};
    private MultilineString multilineString;
    private Note note;
    private ArrayList<Note> notes;

    @Before
    public void setUp() throws Exception
    {
        gregorianCalendar = new GregorianCalendar(2017, 06, 02, 3,
                31, 30);
        multilineString = new MultilineString("This is some note for testing purposes");
        note = new Note("Note1", gregorianCalendar, multilineString);
        notes = new ArrayList<>();
        modelEntity = new ModelEntity("name1", detailsArray, notes);
    }

    @After
    public void tearDown() throws Exception
    {
        modelEntity = null;
    }


    @Test
    public void getDetails() throws Exception
    {
        assertEquals("detail", modelEntity.getDetails().getAsString());
    }

    @Test
    public void setName1() throws Exception
    {
        modelEntity.setName("Andrew");
        assertEquals("Andrew", modelEntity.getName());
    }

    @Test
    public void setDetails() throws Exception
    {
        // Testing setDetails with String argument
        modelEntity.setDetails("Some details to be added");
        assertEquals("Some details to be added", modelEntity.getDetails().getAsString());
    }

    @Test
    public void setDetails1() throws Exception
    {
        // Testing setDetails with String array argument
        String[] detailArray = {"Some details to be added", "more details to be added"};
        modelEntity.setDetails(detailArray);
        assertArrayEquals(detailArray, modelEntity.getDetails().getAsArray());
    }

    @Test
    public void setDetails2() throws Exception
    {
        // Testing setDetails with String ArrayList argument
        ArrayList<String> detailArrrayList = new ArrayList<>();
        detailArrrayList.add("New details to be added ");
        detailArrrayList.add("And some more details to be added ");
        modelEntity.setDetails(detailArrrayList);
        assertArrayEquals(detailArrrayList.toArray(), modelEntity.getDetails().getAsArray());
    }

    @Test
    public void setDetails3() throws Exception
    {
        // Testing setDetails with multiline String argument
        MultilineString multilineString = new MultilineString("New details to be added ");
        modelEntity.setDetails(multilineString);
        assertArrayEquals(multilineString.getAsArray(), modelEntity.getDetails().getAsArray());
    }

    @Test
    public void addProperties() throws Exception
    {
        modelEntity.addProperties("name2", new MultilineString("Added details"));
        assertEquals("name2", modelEntity.getName());
        assertEquals("Added details", modelEntity.getDetails().getAsString());
    }

    @Test
    public void addProperties1() throws Exception
    {
        modelEntity.addProperties("name3", "Added more details");
        assertEquals("name3", modelEntity.getName());
        assertEquals("Added more details", modelEntity.getDetails().getAsString());
    }
}