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

package controller;

import org.junit.jupiter.api.Test;

/**
 * Created by bijan on 08/05/2017.
 */
public class StudyPlannerControllerTest {
	/**
	 * This test cases sets up the Account and Controller necessary for this test suite.
	 * @throws Exception to handle errors when creating the objects.
	 */
	//Was causing a FindBugs issue because the test wasnt fully created.
	//commented out until the test gets created in full.
	/**
	@BeforeEach .
	public void setUp() throws Exception {
		Account account = new Account(new Person("Mr","Adrew",true),"100125464");
		StudyPlannerController studyPlannerController = new StudyPlannerController(account);
	}
	*/
	/**
	 * WIP: This test case should attempt to retrieve user study profiles
	 * @throws Exception to handle when the study profiles cannot be found or loaded properly.
	 */
	@Test
	public void getStudyProfiles() throws Exception {
	}

	/**
	 * WIP: This test case should verify the presence of the study profile file.
	 * @throws Exception to handle when the study planner file is missing.
	 */
	@Test
	public void fileValidation() throws Exception {
	}

	/**
	 * WIP: This test case should verify that the study profile file is not corrupted.
	 * @throws Exception if the study profile does not contain a profile, or if it is corrupted.
	 */
	@Test
	public void containsStudyProfile() throws Exception {
	}

	/**
	 *  WIP: This test case should attempt to retrieve the current version.
	 * @throws Exception when the current version cannot be found.
	 */
	@Test
	public void getCurrentVersion() throws Exception {
	}

	/**
	 * WIP: This test case should test the process for creating a new study profile.
	 * @throws Exception when a study profile cannot be created successfully.
	 */
	@Test
	public void createStudyProfile() throws Exception {
	}

	/**
	 * WIP: This test case should test the process of updating the study profile with new data.
	 * @throws Exception when the study profile cannot be updated successfully.
	 */
	@Test
	public void updateStudyProfile() throws Exception {
	}

	/**
	 * WIP: This test case should attempt to return the current list of tasks in the study profile.
	 * @throws Exception if the list cannot be retrieved.
	 */
	@Test
	public void getListOfTasks() throws Exception {
	}

	/**
	 * WIP: This test case should attempt to add a new activity to the study profile.
	 * @throws Exception when an activity is not able to be added.
	 */
	@Test
	public void newActivity() throws Exception {
	}

}
