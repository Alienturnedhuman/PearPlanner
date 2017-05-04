package Controller;

import Model.*;
import View.ConsoleIO;

import java.util.ArrayList;

/**
 * Created by bendickson on 5/4/17.
 */
public class StudyPlannerController
{
    private StudyPlanner planner;
    // public methods

    /**
     * validates whether a file is valid or not
     * @param filedata
     * @return true = valid,  false = not valid
     */
    public boolean fileValidation(String filedata)
    {
        return false;
        // not implemented yet
    }

    /**
     * does the StudyPlanner contain the profile within the hubfile
     * @param hubFile
     * @return
     */
    public boolean containsStudyProfile(HubFile hubFile)
    {
        return false;
        // not implemented yet
    }

    /**
     * if the StudyPlanner contains the hubfile, this returns the version
     * if not, returns -1
     * @param hubFile
     * @return
     */
    public int getCurrentVersion(HubFile hubFile)
    {
        if(containsStudyProfile(hubFile))
        {
            return 0;
            // return version
        }
        else
        {
            return -1;
        }
    }

    /**
     * if valid, this method creates a new StudyProfile and returns true
     * if invalid, it returns false
     *  * fail states include:
     *
     * @param hubFile
     */
    public boolean createStudyProfile(HubFile hubFile)
    {
        return false;

    }

    /**
     * If the study profile exists and the hubfile is newer, this method updates and returns true
     * If not, returns false
     * @param hubFile
     * @return
     */
    public boolean updateStudyProfile(HubFile hubFile)
    {
        return false;

    }

    /**
     * returns a list of tasks from the array list that contain the ModelEntity provided
     * @param model
     * @param taskList
     * @return
     */
    public ArrayList<Task> getListOfTasks(ModelEntity model, ArrayList<Task> taskList)
    {
        return null;
    }

    /**
     * Adds a new activity to the StudyPlanner
     * @return
     */
    public boolean newActivity(ArrayList<Task> taskList)
    {
        return false;
        // not implemented, argument list incomplete
    }



    // constructors
    public StudyPlannerController()
    {
        // checks if there is a existing settings file
        if(DataController.existingSettingsFile())
        {
            // import data
        }
        else
        {
            // create a blank one

            // create an Account
            String fullName ="";
            boolean familyNameLast =false;
            String salutation = "";

            // CONSOLE INPUT  - to be replaced by javaFX
            fullName = View.ConsoleIO.getDataString("Enter Name:");
            salutation = View.ConsoleIO.getDataString("Enter salutation:");
            familyNameLast = View.ConsoleIO.getDataBool("Is the family name last (y/n)");

            Person studentDetails = new Person(salutation,fullName,familyNameLast);
            String studentAccountNumber ="";

            View.ConsoleIO.setConsoleMessage("Hello "+studentDetails.getSalutation()+" "+studentDetails.getFamilyName());
            View.ConsoleIO.setConsoleMessage("Thank you for creating a study profile.");


            Account newAccount = new Account(studentDetails,studentAccountNumber);


            planner = new StudyPlanner(newAccount);
        }
    }
}
