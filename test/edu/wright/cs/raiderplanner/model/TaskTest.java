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

import edu.wright.cs.raiderplanner.model.Task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


/**
 * Created by bijan on 06/05/2017.
 */
public class TaskTest {
	Task task;
	Task task2;

	/**
	 * This test case should set up all of the objects necessary for the following tests in
	 * 			this suite.
	 * @throws Exception to handle when any of the required objects cannot be instantiated.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		String name = "Do stuff";
		String details = "Do lots of stuff";
		LocalDate deadline = LocalDate.of(2017, 12, 12);
		int weighting = 5;
		String type = "Stuff";
		task = new Task(name, details, deadline, weighting, type);

		String name2 = "Do stuff 2";
		String details2 = "Do lots of stuff 2";
		LocalDate deadline2 = LocalDate.of(2017, 12, 13);
		int weighting2 = 6;
		String type2 = "Stuff";
		task2 = new Task(name2, details2, deadline2, weighting2, type2);
		task.addDependency(task2);
	}

	@Test
	public void dependenciesComplete() throws Exception {
		task2.setComplete(false);
		assertEquals(false, task.dependenciesComplete());

		task2.setComplete(true);
		assertEquals(true, task.dependenciesComplete());
	}

	@Test
	public void hasDependencies() throws Exception {
		assertEquals(true, task.hasDependencies());

		task.removeDependency(task2);
		assertEquals(false, task.hasDependencies());
	}

	@Test
	public void isComplete() throws Exception {
		assertEquals(false, task.isCheckedComplete());

		task.setComplete(true);
		assertEquals(false, task.isCheckedComplete());

		task2.setComplete(true);
		task.setComplete(true);
		assertEquals(true, task.isCheckedComplete());
	}

	@Test
	public void canCheckComplete() throws Exception {
		assertEquals(false, task.canCheckComplete());

		task2.setComplete(true);
		assertEquals(true, task.canCheckComplete());
	}

	/**
	 * After each run, this case removes all data from the Task objects to avoid
	 * 			interference with other test runs.
	 * @throws Exception to handle when the Task objects cannot be accessed.
	 */
	@AfterEach
	public void tearDown() throws Exception {
		task = null;
		task2 = null;
	}
}
