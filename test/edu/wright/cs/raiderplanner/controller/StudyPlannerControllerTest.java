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

package edu.wright.cs.raiderplanner.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wright.cs.raiderplanner.controller.StudyPlannerController;
import edu.wright.cs.raiderplanner.model.Account;
import edu.wright.cs.raiderplanner.model.Event;
import edu.wright.cs.raiderplanner.model.HubFile;
import edu.wright.cs.raiderplanner.model.Module;
import edu.wright.cs.raiderplanner.model.Person;
import edu.wright.cs.raiderplanner.model.Task;
import edu.wright.cs.raiderplanner.model.VersionControlEntity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Created by bijan on 08/05/2017.
 */
public class StudyPlannerControllerTest {

	StudyPlannerController studyPlannerController;

	/**
	 * This test cases sets up the Account and Controller necessary for this test suite.
	 * @throws Exception to handle errors when creating the objects.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		Account account = new Account(new Person("Mr","Adrew",true),"100125464");
		studyPlannerController = new StudyPlannerController(account);

		int v1 = 2;
		int y1 = 2017;
		int s1 = 3;
		ArrayList<Module> m1 = new ArrayList<>();
		ArrayList<VersionControlEntity> a1 = new ArrayList<>();
		ArrayList<Event> cal1 = new ArrayList<>();
		HubFile hubFile = new HubFile(v1, y1, s1, m1, a1, cal1);
		studyPlannerController.createStudyProfile(hubFile);
	}

	/**
	 * WIP: This test case should attempt to retrieve user study profiles.
	 * @throws Exception to handle when the study profiles cannot be found or loaded properly.
	 */

	@Test
	public void getStudyProfiles() throws Exception {
		assertEquals(1, studyPlannerController.getPlanner().getStudyProfiles().length);
	}

	/**
	 * WIP: This test case should verify that the study profile file is not corrupted.
	 * @throws Exception if the study profile does not contain a profile, or if it is corrupted.
	 */
	@Test
	public void containsStudyProfile() throws Exception {
		assertEquals(true, studyPlannerController.containsStudyProfile(2017, 3));
	}

	/**
	 *  WIP: This test case should attempt to retrieve the current version.
	 * @throws Exception when the current version cannot be found.
	 */
	@Test
	public void getCurrentVersion() throws Exception {
		studyPlannerController.getPlanner().setVersion(5);
		assertEquals(5, studyPlannerController.getPlanner().getVersion());
	}

	/**
	 * WIP: This test case should test the process for creating a new study profile.
	 * @throws Exception when a study profile cannot be created successfully.
	 */
	@Test
	public void createStudyProfile() throws Exception {
		int v1 = 5;
		int y1 = 1997;
		int s1 = 30;
		ArrayList<Module> m1 = new ArrayList<>();
		ArrayList<VersionControlEntity> a1 = new ArrayList<>();
		ArrayList<Event> cal1 = new ArrayList<>();
		HubFile newHubFile = new HubFile(v1, y1, s1, m1, a1, cal1);
		assertEquals(true, studyPlannerController.createStudyProfile(newHubFile));
	}

	/**
	 * WIP: This test case should test the process of updating the study profile with new data.
	 * @throws Exception when the study profile cannot be updated successfully.
	 */
	@Test
	public void updateStudyProfile() throws Exception {
		studyPlannerController.getPlanner().getStudyProfiles()[0].setName("John Doe");
		String profileName = studyPlannerController.getPlanner().getStudyProfiles()[0].getName();
		assertEquals("John Doe", profileName);
	}

	/**
	 * WIP: This test case should attempt to return the current list of tasks in the study profile.
	 * @throws Exception if the list cannot be retrieved.
	 */
	@Test
	public void getListOfTasks() throws Exception {
		ArrayList<Task> task = studyPlannerController.getPlanner().getStudyProfiles()[0].getTasks();
		assertEquals(new ArrayList<Task>(), task);
	}

	/**
	 * WIP: This test case should attempt to add a new activity to the study profile.
	 * @throws Exception when an activity is not able to be added.
	 */
	@Test
	public void newActivity() throws Exception {
	}

	/**
	 * This test case erases the data from the StudyPlannerController object to avoid any
	 * 				leftover data which could conflict with future tests.
	 * @throws Exception to handle when the StudyPlannerController object cannot be found.
	 */
	@AfterEach
	public void tearDown() throws Exception {
		studyPlannerController = null;
	}
}
