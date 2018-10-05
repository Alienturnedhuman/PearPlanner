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
 * Created by Adam Cone 09/22/2018.
 */
public class RoomTest {
	Building bd1;
	Building bd2;
	Room rm1;
	Room rm2;

	/**
	 * This test case sets up the objects necessary for each tests.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		bd1 = new Building("CEG", 0.0, 0.0);
		bd2 = new Building("MTH", 0.0, 0.0);
		rm1 = new Room("321", bd1);
		rm2 = new Room("123", bd2);
	}

	/**
	 * This test case attempts to retrieve building object information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void getBuilding() throws Exception {
		assertEquals(bd1, rm1.getBuilding());
	}

	/**
	 * This test case attempts to retrieve room object information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void getRoomNumber() throws Exception {
		assertEquals("321", rm1.getRoomNumber());
	}

	/**
	 * This test case attempts to set building object information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void setBuilding() throws Exception {
		rm1.setBuilding(bd2);
		assertEquals(bd2, rm1.getBuilding());
	}

	/**
	 * This test case attempts to set room object information.
	 *
	 * @throws Exception
	 *             to handle when any of the required objects cannot be instantiated.
	 */
	@Test
	public void setRoomNumber() throws Exception {
		rm1.setRoomNumber("foo");
		assertEquals("foo", rm1.getRoomNumber());
	}

	/**
	 * After each run, this removes all data to avoid interference with other tests.
	 *
	 * @throws Exception
	 *             to handle when the Task objects cannot be accessed.
	 */
	@AfterEach
	public void tearDown() throws Exception {
		bd1 = null;
		bd2 = null;
		rm1 = null;
		rm2 = null;
	}
}