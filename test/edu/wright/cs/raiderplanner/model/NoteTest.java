/*
 * Copyright (C) 2018 - Adam Cone
 *
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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.GregorianCalendar;

/**
 * Created by Adam Cone 10/16/2018.
 */
public class NoteTest {
	String title;
	GregorianCalendar timeStamp;
	MultilineString text;
	Note testNote;

	/**
	 * This test case sets up the objects necessary for each tests.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		title = "title";
		timeStamp = new GregorianCalendar(2018, 10, 21, 00, 00, 00);
		text = new MultilineString("information");
		testNote = new Note(title, timeStamp, text);
	}

	/**
	 * This test case attempts to retrieve Note title object information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void getTitle() throws Exception {
		assertEquals(this.title, testNote.getTitle());
	}

	/**
	 * This test case attempts to retrieve Note timeStamp object information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void getTimeStamp() throws Exception {
		assertEquals(this.timeStamp, testNote.getTimeStamp());
	}

	/**
	 * This test case attempts to retrieve Note text object information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void getText() throws Exception {
		assertEquals(this.text, testNote.getText());
	}

	/**
	 * This test case attempts to set Note title object information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void setTitle() throws Exception {
		this.title = "newTitle";
		testNote.setTitle(this.title);
		assertEquals(this.title, testNote.getTitle());
	}

	/**
	 * This test case attempts to set Note timeStamp object information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void setTimeStamp1() throws Exception {
		this.timeStamp = new GregorianCalendar(2018, 10, 22, 00, 00, 00);
		testNote.setTimeStamp(this.timeStamp);
		assertEquals(this.timeStamp, testNote.getTimeStamp());
	}

	/**
	 * This test case attempts to set Note timeStamp object information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void setTimeStamp2() throws Exception {
		this.timeStamp = new GregorianCalendar(2018, 10, 23, 00, 00, 00);
		testNote.setTimeStamp(2018, 10, 23, 00, 00, 00);
		assertEquals(this.timeStamp, testNote.getTimeStamp());
	}

	/**
	 * This test case attempts to set Note text object information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void setText() throws Exception {
		this.text = new MultilineString("newInformation");
		testNote.setText(this.text);
		assertEquals(this.text, testNote.getText());
	}

	/**
	 * After each run, this removes all data to avoid interference with other tests.
	 *
	 * @throws Exception
	 *             to handle when the Task objects cannot be accessed.
	 */
	@AfterEach
	public void tearDown() throws Exception {
		title = null;
		timeStamp = null;
		text = null;
		testNote = null;
	}
}