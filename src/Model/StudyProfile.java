package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class StudyProfile
{
    // private data
    private ArrayList<Module> modules;
    private ArrayList<Milestone> milestones;
    private String name;
    private ArrayList<String> details;
    private int year;
    private int semesterNo;
    private int version;

    // public methods
    public int milestonesCompleted()
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
    public double milestonesProgress()
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    // constructors
    StudyProfile()
    {
        // initial set up code below - check if this needs updating

    }
    StudyProfile(HubFile initialHubFile)
    {
        // initial set up code below - check if this needs updating

    }
}
