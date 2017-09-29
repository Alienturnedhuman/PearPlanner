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

package Controller;

import Model.Account;
import Model.Person;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bijan on 08/05/2017.
 */
public class StudyPlannerControllerTest {

    @Before
    public void setUp() throws Exception
    {
        Account a = new Account(new Person("Mr","Adrew",true),"100125464");
        StudyPlannerController studyPlannerController = new StudyPlannerController(a);
    }

    @Test
    public void getStudyProfiles() throws Exception
    {
    }

    @Test
    public void fileValidation() throws Exception
    {
    }

    @Test
    public void containsStudyProfile() throws Exception
    {
    }

    @Test
    public void getCurrentVersion() throws Exception
    {
    }

    @Test
    public void createStudyProfile() throws Exception
    {
    }

    @Test
    public void updateStudyProfile() throws Exception
    {
    }

    @Test
    public void getListOfTasks() throws Exception
    {

    }

    @Test
    public void newActivity() throws Exception
    {
    }

}