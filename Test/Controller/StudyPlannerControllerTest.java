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