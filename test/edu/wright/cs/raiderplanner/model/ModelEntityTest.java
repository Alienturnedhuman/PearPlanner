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

package edu.wright.cs.raiderplanner.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wright.cs.raiderplanner.model.ModelEntity;
import edu.wright.cs.raiderplanner.model.MultilineString;
import edu.wright.cs.raiderplanner.model.Note;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by bijan on 08/05/2017.
 */
@Disabled
public class ModelEntityTest {

	private ModelEntity modelEntity;
	private GregorianCalendar gregorianCalendar;
	private MultilineString multilineString;
	private Note note;
	private ArrayList<Note> notes;

	/**
	 * This test case should set up all of the objects necessary for the following tests in
	 * 			this suite.
	 * @throws Exception to handle when any of the required objects cannot be instantiated.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		gregorianCalendar = new GregorianCalendar(2017, 06, 02, 3,
				31, 30);
		multilineString = new MultilineString("This is some note for testing purposes");
		note = new Note("Note1", gregorianCalendar, multilineString);
		notes = new ArrayList<>();
		notes.add(note);
		modelEntity = new Milestone("Milestone", "Test", LocalDate.of(2018, 2, 4));
	}

	/**
	 * After each run, this case removes all data from the ModelEntity object to avoid
	 * 			interference with other test runs.
	 * @throws Exception to handle when the ModelEntity cannot be accessed.
	 */
	@AfterEach
	public void tearDown() throws Exception {
		modelEntity = null;
	}

	@Test
	public void getDetails() throws Exception {
		assertEquals("detail", modelEntity.getDetails().getAsString());
	}

	@Test
	public void setName1() throws Exception {
		modelEntity.setName("Andrew");
		assertEquals("Andrew", modelEntity.getName());
	}

	@Test
	public void setDetails() throws Exception {
		// Testing setDetails with String argument
		modelEntity.setDetails("Some details to be added");
		assertEquals("Some details to be added", modelEntity.getDetails().getAsString());
	}

	@Test
	public void setDetails1() throws Exception {
		// Testing setDetails with String array argument
		String[] detailArray = {"Some details to be added", "more details to be added"};
		modelEntity.setDetails(detailArray);
		assertArrayEquals(detailArray, modelEntity.getDetails().getAsArray());
	}

	@Test
	public void setDetails2() throws Exception {
		// Testing setDetails with String ArrayList argument
		ArrayList<String> detailArrrayList = new ArrayList<>();
		detailArrrayList.add("New details to be added ");
		detailArrrayList.add("And some more details to be added ");
		modelEntity.setDetails(detailArrrayList);
		assertArrayEquals(detailArrrayList.toArray(), modelEntity.getDetails().getAsArray());
	}

	@Test
	public void setDetails3() throws Exception {
		// Testing setDetails with multiline String argument
		MultilineString multilineStr = new MultilineString("New details to be added ");
		modelEntity.setDetails(multilineStr);
		assertArrayEquals(multilineStr.getAsArray(), modelEntity.getDetails().getAsArray());
	}

	@Test
	public void addProperties() throws Exception {
		modelEntity.addProperties("name2", new MultilineString("Added details"));
		assertEquals("name2", modelEntity.getName());
		assertEquals("Added details", modelEntity.getDetails().getAsString());
	}

	@Test
	public void addProperties1() throws Exception {
		modelEntity.addProperties("name3", "Added more details");
		assertEquals("name3", modelEntity.getName());
		assertEquals("Added more details", modelEntity.getDetails().getAsString());
	}

}
