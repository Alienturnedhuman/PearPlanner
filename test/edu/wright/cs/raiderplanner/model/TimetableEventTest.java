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

/**
 * Created by Adam Cone 11/14/2018.
 */
public class TimetableEventTest {

	private String date;
	private Room room;
	private Person lecturer;
	private TimeTableEventType timeTableEventType;
	private int duration;
	private TimetableEvent timetableEventTest;

	/**
	 * This test case sets up the objects necessary for each tests.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		date = "11/14/2018";
		room = new Room("152");
		lecturer = new Person("Mr.", "Adam Cone", true, "adam@wright.edu");
		timeTableEventType = new TimeTableEventType();
		duration = 60;
		timetableEventTest = new TimetableEvent(date, room, lecturer, timeTableEventType, duration);
	}

	/**
	 * This test case attempts to retrieve room information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void testGetRoom() throws Exception {
		assertEquals(this.room, timetableEventTest.getRoom());
	}

	/**
	 * This test case attempts to retrieve lecturer information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void testGetLecturer() throws Exception {
		assertEquals(this.lecturer, timetableEventTest.getLecturer());
	}

	/**
	 * This test case attempts to retrieve timeTableEventType information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void testGetTimeTableEventType() throws Exception {
		assertEquals(this.timeTableEventType, timetableEventTest.getTimeTableEventType());
	}

	/**
	 * This test case attempts to set room information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void testSetRoom() throws Exception {
		Room newRoom = new Room("300");
		timetableEventTest.setRoom(newRoom);
		assertEquals(newRoom, timetableEventTest.getRoom());
	}

	/**
	 * This test case attempts to set lecturer information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void testSetLecturer() throws Exception {
		Person newLecturer = new Person("Mr.", "Dam Done", true, "dam@wright.edu");
		timetableEventTest.setLecturer(newLecturer);
		assertEquals(newLecturer, timetableEventTest.getLecturer());
	}

	/**
	 * This test case attempts to set timeTableEventType information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void testSetTimeTableEventType() throws Exception {
		TimeTableEventType newTimeTableEventType = new TimeTableEventType();
		timetableEventTest.setTimeTableEventType(newTimeTableEventType);
		assertEquals(newTimeTableEventType, timetableEventTest.getTimeTableEventType());
	}

	/**
	 * After each run, this removes all data to avoid interference with other tests.
	 *
	 * @throws Exception
	 *             to handle when the Task objects cannot be accessed.
	 */
	@AfterEach
	public void tearDown() throws Exception {
		date = null;
		room = null;
		lecturer = null;
		timeTableEventType = null;
		duration = 0;
		timetableEventTest = null;
	}
}