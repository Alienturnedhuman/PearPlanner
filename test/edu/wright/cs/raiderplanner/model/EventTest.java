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

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wright.cs.raiderplanner.controller.MainController;
import edu.wright.cs.raiderplanner.model.Event;

import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import org.testfx.framework.junit.ApplicationTest;

import java.util.GregorianCalendar;

/**
 * Created by bijan on 12/05/2017.
 */

public class EventTest extends ApplicationTest {

	@Override
	public void start(Stage primaryStage) throws Exception {
		MainController.isNumeric("23");
	}

	Event event;

	/**
	 * This test case sets up a new Event object each time it is run.
	 * @throws Exception if a new Event cannot be created.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		event = new Event("09/04/2017T15:00:00Z");
	}

	/**
	 * WIP: This test case should verify that the Event.validDateString method returns valid
	 * 				information. Currently, this test causes the build to crash. When resolved,
	 * 				the tag should be changed from @Disabled to @Test.
	 * This test case verifies that the Event.validDateString method returns valid information.
	 * @throws Exception if the Event.validDateString method returns incorrect information.
	 */
	@Disabled
	public void validDateString() throws Exception {
		// Testing a valid date format
		assertEquals(true, Event.validDateString("02/05/2017T05:02:09Z"));

		// Testing an invalid date format
		assertEquals(false, Event.validDateString("02/05/217T0:02:09Z"));
	}

	/**
	 * WIP: This test case should verify that the GregorianCalendar object correctly returns
	 * 			data for a given date. Currently, this test causes the build to crash. When
	 * 			resolved, the tag should be changed from @Disabled to @Test.
	 * @throws Exception if the date/time string returned is not correct.
	 */
	@Disabled
	public void toStringTest() throws Exception {
		GregorianCalendar expectedDate = new GregorianCalendar(2017, 3, 9, 15, 0, 0);
		assertEquals(expectedDate.getTime().toString(), event.toString());
	}

	/**
	 * WIP: This test case should verify that the Event.setDate method correctly sets/saves
	 * 			a given date/time. As long as the previous test cases pass successfully,
	 * 			then this test case should also pass if the Event.setDate method is working.
	 * @throws Exception to handle when the date cannot be set, or when the Event and the
	 * 				GregorianCalendar date do not match.
	 */
	@Disabled
	public void setDate() throws Exception {
		event.setDate("04/10/2017T09:08:13Z");
		GregorianCalendar expectedDate = new GregorianCalendar(2017, 9, 4, 9, 8, 13);
		assertEquals(expectedDate.getTime().toString(), event.toString());
	}

}
